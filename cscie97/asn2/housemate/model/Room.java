package cscie97.asn2.housemate.model;

// Room.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

import java.util.ArrayList;
import java.util.List;

/*
 * Public class to manage room windows, name, floor, appliances, and sensors
 */
public class Room {

	private int floor;      // Which floor the room is on
	private String name;    // Name of the room
	private int numWindows; // Number of windows in the room
	private List<Appliance> appliances; // List of appliances in the room
	private List<Sensor> sensors; // List of sensors in the room
	private List<Occupant> occupants;

	// Constructor to initialize room properties
	public Room(String roomName, int floor, String roomType, int windows) {
		this.name = roomName;
		this.floor = floor;
		this.numWindows = windows;
		this.appliances = new ArrayList<>();
		this.sensors = new ArrayList<>();
		this.occupants = new ArrayList<>();
	}

	// Get the energy consumption of the room
	public double getEnergyConsumption() {
		double totalEnergy = 0.0;
		for (Appliance appliance : appliances) {
			totalEnergy += appliance.getEnergyConsumption();
		}
		return totalEnergy;
	}

	// Add an appliance to the room
	public void addAppliance(Appliance appliance) {
		this.appliances.add(appliance);
	}

	// Add a sensor to the room
	public void addSensor(Sensor newSensor) {
		this.sensors.add(newSensor);
	}

	// Get an appliance by its name
	public Appliance getApplianceByName(String applianceName) {
		for (Appliance appliance : appliances) {
			if (appliance.getName().equalsIgnoreCase(applianceName)) {
				return appliance;
			}
		}
		return null; // Return null if appliance not found
	}

	// Get the name of the room
	public String getRoomName() {
		return name;
	}

	@Override
	public String toString() {
		return "Room Name: " + name + ", Floor: " + floor + ", Windows: " + numWindows;
	}
}