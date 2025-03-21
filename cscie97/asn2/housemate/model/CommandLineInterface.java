package cscie97.asn2.housemate.model;

// CommandLineInterface.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

import java.util.Arrays;

/*
 * Public class that interfaces with the command line syntax that will integrate
 * with the HouseMateModelService
 */
public class CommandLineInterface {

	private HouseMateModelService service;
	private String authToken;

	public CommandLineInterface(HouseMateModelService service, String authToken) {
		this.service = service;
		this.authToken = authToken;
	}

	/*
	 * Public method to parse the command and stores each element
	 * in the parts array.
	 */
	public String[] parseCommand(String command) throws HouseMateException {
		if (command == null || command.trim().isEmpty()) {
			throw new HouseMateException("Blank line detected. Advancing to next command.");
		}
		String[] parts = command.trim().split(" ");
		if (parts.length < 2) {
			throw new HouseMateException("Invalid command format.");
		}
		return parts;
	}


	/*
	 * Execute the command parsed by parseCommand() using a switch case
	 */
	public void executeCommand(String command) {
		try {
			String[] parts = parseCommand(command);
			switch (parts[0]) {
				case "define":
					handleDefineCommand(parts);
					break;
				case "add":
					if (parts[1].equals("occupant")) {
						addOccupantToHouse(parts[2], parts[4]); // Assuming "add occupant <name> to_house <house_name>"
					}
					break;
				case "set":
					handleSetCommand(parts);
					break;
				case "show":
					System.out.println(showConfiguration(parts[2])); // Assuming "show configuration <house_name>"
					break;
				case "get":
					handleGetCommand(parts);
					break;
				case "getstatus":
					String[] deviceParts = parts[1].split(":");
					String deviceStatus = getDeviceStatus(deviceParts[0], deviceParts[1], deviceParts[2], parts[2]); // <status>
					System.out.println(deviceStatus);
					break;
				case "findOccupant":
					String occupantName = parts[1];
					Occupant occupant = service.findOccupantByName(occupantName);
					if (occupant != null) {
						Room room = occupant.getLocation(); // Call getLocation() on the Occupant
						if (room != null) {
							System.out.println("Occupant " + occupantName + " is in room: " + room.getRoomName());
						} else {
							System.out.println("Occupant " + occupantName + " is in an unknown location.");
						}
					} else {
						System.out.println("Occupant not found.");
					}
					break;
				case "record":
					String sensorName = parts[1]; // e.g., "smoke_detector1_1"
					service.recordSensorData(sensorName);
					break;
				case "#":
					System.out.println(Arrays.toString(parts));
					break;
				case " ":
					break;
				default:
					throw new HouseMateException("Unknown command: " + parts[0]);
			}
		} catch (HouseMateException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}

	private void handleSetCommand(String[] parts) {
		if (parts[1].equals("sensor") || parts[1].equals("appliance")) {
			// Extract individual components
			String[] setParts = parts[2].split(":");
			String houseName = setParts[0];  // 'house1'
			String roomName = setParts[1];   // 'kitchen'
			String deviceName = setParts[2]; // 'fridge'
			String statusName = parts[3];  // 'status'
			String value = parts[4];       // 'o
			setDeviceStatus(houseName, roomName, deviceName, statusName, value);
		}
	}

	/*
	 * When a define command is selected it is managed here where it will call
	 * the define methods that will integrate with the HouseMateModelService
	 */
	private void handleDefineCommand(String[] parts) throws HouseMateException {
		switch (parts[1]) {
			case "house":
				defineHouse(parts[2], parts[4]); // Assuming "define house <name> address <address>"
				break;
			case "room":
				defineRoom(parts[8], parts[2], Integer.parseInt(parts[4]), parts[6], Integer.parseInt(parts[10]));
				// Assuming "define room <room_name> floor <floor> type <room_type> house <house_name> windows <window_count>"
				break;
			case "occupant":
				defineOccupant(parts[2], parts[3]); // Assuming "define occupant <name> type <type>"
				break;
			case "sensor":
				String[] roomParts = parts[6].split(":"); // Assuming "define sensor <name> type <type> room <house:room>"
				defineSensor(parts[2], parts[3], roomParts[0], roomParts[1]);
				break;
			case "appliance":
				String[] applianceParts = parts[6].split(":");
				String applianceName = parts[2];  // 'fridge'
				String applianceType = parts[4];  // 'kitchen_appliance'
				String houseName = applianceParts[0];      // 'house1'
				String roomName = applianceParts[1];       // 'kitchen'
				int energyUse = 0;				// zero by default
				// Now call defineAppliance with the correct arguments
				defineAppliance(applianceName, applianceType, houseName, roomName, energyUse);
				break;
			default:
				throw new HouseMateException("Unknown define command: " + parts[1]);
		}
	}

	/*
	 * When a get command is selected it is managed here where it will call
	 * the get methods that will integrate with the HouseMateModelService
	 */
	private void handleGetCommand(String[] parts) throws HouseMateException {
		if (parts[1].equals("energy-use")) {
			if (parts.length == 3) {
				System.out.println(getEnergyUse(parts[2])); // Assuming "get energy-use <house_name>"
			} else if (parts[3].equals("room")) {
				System.out.println(getEnergyUseForRoom(parts[2], parts[4])); // Assuming "get energy-use <house_name> room <room_name>"
			} else if (parts[3].equals("appliance")) {
				System.out.println(getEnergyUseForAppliance(parts[2], parts[4])); // Assuming "get energy-use <house_name> appliance <appliance_name>"
			} else {
				throw new HouseMateException("Unknown get command.");
			}
		} else {
			throw new HouseMateException("Unknown get command.");
		}
	}


	/*
	 * Define a house by interacting with the HouseMateModelService
	 */
	public void defineHouse(String houseName, String address) {
		service.defineHouse(houseName, address);
	}

	/*
	 * Define a room by interacting with the HouseMateModelService
	 */
	public void defineRoom(String houseName, String roomName, int floor, String roomType, int windows) {
		service.defineRoom(houseName, roomName, floor, roomType, windows);
	}

	/*
	 * Define a occupant by interacting with the HouseMateModelService
	 */
	public void defineOccupant(String occupantName, String occupantType) {
		service.defineOccupant(occupantName, occupantType);
	}

	/*
	 * Define a sensor by interacting with the HouseMateModelService
	 */
	public void defineSensor(String sensorName, String sensorType, String houseName, String roomName) {
		service.defineSensor(sensorName, sensorType, houseName, roomName);
	}

	/*
	 * Define an appliance by interacting with the HouseMateModelService
	 */
	public void defineAppliance(String applianceName, String applianceType, String houseName, String roomName, int energyUse) {
		service.defineAppliance(applianceName, applianceType, houseName, roomName, energyUse);
	}

	/*
	 * Add an occupant to a house by interacting with the HouseMateModelService
	 */
	public void addOccupantToHouse(String occupantName, String houseName) {
		service.addOccupantToHouse(occupantName, houseName);
	}


	/*
	 * Set a device status by interacting with the HouseMateModelService
	 */
	public void setDeviceStatus(String houseName, String roomName, String deviceName, String statusName, String value) {
		service.setDeviceStatus(houseName, roomName, deviceName, statusName, value);
	}

	/*
	 * Show the configuration of a house
	 */
	public String showConfiguration(String houseName) {
		return service.showConfiguration(houseName);
	}

	/*
	 * Get the energy usage of a house
	 */
	public double getEnergyUse(String houseName) {
		return service.getEnergyUse(houseName);
	}

	/*
	 * Get the energy usage of a room
	 */
	public double getEnergyUseForRoom(String houseName, String roomName) {
		return service.getEnergyUseForRoom(houseName, roomName);
	}

	/*
	 * Get the energy usage of an appliance
	 */
	public double getEnergyUseForAppliance(String houseName, String applianceName) {
		return service.getEnergyUseForAppliance(houseName, applianceName);
	}

	/*
	 * Get the status of a device
	 */
	public String getDeviceStatus(String houseName, String roomName, String deviceName, String statusName) {
		return service.getDeviceStatus(houseName, roomName, deviceName, statusName);
	}

}
