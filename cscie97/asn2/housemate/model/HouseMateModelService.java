package cscie97.asn2.housemate.model;

// HouseMateModelService.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

import java.util.*;

import cscie97.asn1.knowledge.engine.Triple;
import cscie97.asn1.knowledge.engine.KnowledgeGraph;


/*
 * Public class for defining houses, rooms, appliances, occupants,
 * sensors, and appliances.
 */
public class HouseMateModelService implements Subject {

	// Use a Map to store houses with their names as keys
	private Map<String, House> houses;
	private Map<String, Appliance> appliances;
	private Map<String, Sensor> sensors;
	private List<Observer> observers;
	private KnowledgeGraph knowledgeGraph;

	public HouseMateModelService(KnowledgeGraph knowledgeGraph) {
        this.observers =  new ArrayList<>();
        this.appliances = new HashMap<>();
		this.sensors = new HashMap<>();
        this.houses = new HashMap<>();
		this.knowledgeGraph = knowledgeGraph;
	}

	public KnowledgeGraph getKnowledgeGraph() {
		return knowledgeGraph;
	}

	/*
	 * Define a house with an address and integrates with the Knowledge Graph
	 */
	public void defineHouse(String houseName, String address) {
		House newHouse = new House(houseName, address);
		houses.put(houseName, newHouse); // Add the new house to the map
		knowledgeGraph.importTriple(houseName, "has_address", address);
	}

	/*
	 * Define a room and integrates with the Knowledge Graph
	 */
	public void defineRoom(String houseName, String roomName, int floor, String roomType, int windows) {
		House house = getHouseByName(houseName);
		if (house != null) {
			Room newRoom = new Room(roomName, floor, roomType, windows);
			house.addRoom(newRoom);

			// Check for successful addition before importing triples
			if (house.getRooms().contains(newRoom)) { // Assuming you have a getter for rooms
				knowledgeGraph.importTriple(roomName, "is_in_house", houseName);
				knowledgeGraph.importTriple(roomName, "has_type", roomType);
			} else {
				System.out.println("Room could not be added.");
			}
		} else {
			System.out.println("House not found: " + houseName);
		}
	}

	/*
	 * Define an occupant and integrate it with the Knowledge Graph
	 */
	public void defineOccupant(String occupantName, String occupantType) {
		knowledgeGraph.importTriple(occupantName, "has_type", occupantType);
	}

	/*
	 * Add an occupant to a house and integrate it with the Knowledge Graph
	 */
	public void addOccupantToHouse(String occupantName, String houseName) {
		House house = getHouseByName(houseName);
		if (house != null) {
			house.addOccupant(occupantName);
			knowledgeGraph.importTriple(occupantName, "lives_in", houseName);
		}
	}

	/*
	 * Define a sensor and integrate it with the Knowledge Graph
	 */
	public void defineSensor(String sensorName, String sensorType, String houseName, String roomName) {
		House house = getHouseByName(houseName);
		if (house != null) {
			Room room = house.getRoomByName(roomName);
			if (room != null) {
				Sensor newSensor = new Sensor(sensorName, sensorType);
				room.addSensor(newSensor);
				knowledgeGraph.importTriple(sensorName, "is_in_room", roomName);
			}
		}
	}

	/*
	 * Define an appliance and integrate it with the Knowledge Graph
	 */
	public void defineAppliance(String applianceName, String applianceType, String houseName, String roomName, int energyUse) {
		House house = getHouseByName(houseName);
		if (house != null) {
			Room room = house.getRoomByName(roomName);
			if (room != null) {
				Appliance newAppliance = new Appliance(applianceName, applianceType, energyUse);
				room.addAppliance(newAppliance);
				knowledgeGraph.importTriple(applianceName, "is_in_room", roomName);
				knowledgeGraph.importTriple(applianceName, "has_type", applianceType);
			}
		}
	}

	/*
	 * Set the device status and integrate it with the Knowledge Graph
	 */
	public void setDeviceStatus(String houseName, String roomName, String deviceName, String statusName, String value) {
		System.out.println("Setting " + deviceName + " status to " + value);

		if (deviceName.contains("smoke_detector") && value.equals("fire")) {
			Event event = new Event(houseName, roomName, deviceName,"FireDetectedCommand");
			notifyObservers(event);
		} else if(deviceName.contains("ava") && value.equals("brighten_lights")) {
			Event event = new Event(houseName, roomName, deviceName, "TurnOnLightsCommand");
			notifyObservers(event);
		} else if(deviceName.contains("ava") && value.equals("dim_lights")) {
			Event event = new Event(houseName, roomName, deviceName, "TurnOffLightsCommand");
			notifyObservers(event);
		} else if(deviceName.contains("door") && value.equals("open")) {
			Event event = new Event(houseName, roomName, deviceName, "OpenDoorCommand");
			notifyObservers(event);
		}  else if(deviceName.contains("door") && value.equals("close")) {
			Event event = new Event(houseName, roomName, deviceName, "CloseDoorCommand");
			notifyObservers(event);
		}
	}

	/*
	 * Get the device status and integrate it with the Knowledge Graph
	 */
	public String getDeviceStatus(String houseName, String roomName, String deviceName, String value) {
		// Subject is the deviceName only
		String subject = deviceName;

		// Predicate is the statusName
		String predicate = "has_status";

		// Object is the expected statusName, e.g., "status=active"
		String object = "?"; // We'll look for any value for this status

		// Execute the query
		Set<Triple> result = knowledgeGraph.executeQuery(subject, predicate, object);

		// Check if we have any result
		if (result != null && !result.isEmpty()) {
			for (Triple triple : result) {
				return triple.toString();  // Return the first matching triple
			}
		}

		return "Status not found";  // Default response if no status found
	}


	/*
	 * Shows the configuration of a house by using the House class toString method
	 */
	public String showConfiguration(String houseName) {
		House house = getHouseByName(houseName);
		if (house != null) {
			return house.toString();
		}
		return "House not found.";
	}

	/*
	 * Gets the energy use of a house
	 */
	public double getEnergyUse(String houseName) {
		House house = getHouseByName(houseName);
		if (house != null) {
			return house.getEnergyConsumption();
		}
		return 0;
	}

	/*
	 * Gets the energy usage of a room
	 */
	public double getEnergyUseForRoom(String houseName, String roomName) {
		House house = getHouseByName(houseName);
		if (house != null) {
			Room room = house.getRoomByName(roomName);
			if (room != null) {
				return room.getEnergyConsumption();
			}
		}
		return 0;
	}

	/*
	 * Gets the energy usage of an appliance
	 */
	public double getEnergyUseForAppliance(String houseName, String applianceName) {
		House house = getHouseByName(houseName);
		if (house != null) {
			// Iterate over all rooms in the house
			for (Room room : house.getRooms()) {
				Appliance appliance = room.getApplianceByName(applianceName);
				if (appliance != null) {
					return appliance.getEnergyConsumption();  // Return the energy consumption if the appliance is found
				}
			}
		}

		return 0;
	}

	/*
	 * Private helper method to look in the houses HashMap to get the house name
	 */
	private House getHouseByName(String houseName) {
		return houses.get(houseName.trim().toLowerCase()); // Retrieve house directly from the map
	}

	/*
	 * Public method to look in the KnowledgeGraph for an occupant by name
	 */
	public Occupant findOccupantByName(String occupantName) {
		// Subject is the occupant's name
		String subject = occupantName;

		// Predicate is assumed to be "is_occupant" or a relevant predicate in your KG
		String predicate = "is_occupant";  // Adjust as necessary for your KG

		// Object is set to "?" to find any occupant details
		String object = "?";

		// Execute the query against the knowledge graph
		Set<Triple> result = knowledgeGraph.executeQuery(subject, predicate, object);


		return (Occupant) result;
	}

	/*
	 * Public method to record sensor data
	 */
	public void recordSensorData(String sensorName) {
		Sensor sensor = findSensorByName(sensorName); // Implement this method to find the sensor
		if (sensor != null) {
			sensor.recordData(); // Call the recordData method
		} else {
			System.out.println("Sensor not found: " + sensorName);
		}
	}

	/*
	 * Private helper method to assist in finding a sensor
	 */
	private Sensor findSensorByName(String sensorName) {
		// Prepare the query to find the sensor in the KG
		String subject = sensorName.toLowerCase(); // Normalize to lower case
		String predicate = "is_a"; // Assuming there's a predicate for type checking
		String object = "sensor"; // Assuming sensors are stored as type "sensor"

		// Query the KG
		Set<Triple> result = knowledgeGraph.executeQuery(subject, predicate, object);


		return (Sensor) result;
	}

	/*
		Attach observer
	 */
	@Override
	public void attach(Observer observer) {
		observers.add(observer);
	}

	/*
		Detach observer
	 */
	@Override
	public void detach(Observer observer) {
		observers.remove(observer);
	}

	/*
		Notify observers
	 */
	@Override
	public void notifyObservers(Event event) {
		for (Observer observer : observers) {
			observer.update(event);
		}
	}
}
