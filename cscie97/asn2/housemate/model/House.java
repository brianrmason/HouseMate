package cscie97.asn2.housemate.model;

// House.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

import java.util.ArrayList;
import java.util.List;

/*
 * Public class to manage house name, address, occupants, and rooms
 */
public class House {

	private String houseName;
	private String address;
	private List<String> occupants;
	private List<Room> rooms;
	private double currentEnergyConsumption;

	// Constructor
	public House(String houseName, String address) {
		this.houseName = houseName;
		this.address = address;
		this.occupants = new ArrayList<>();
		this.rooms = new ArrayList<>();
		this.currentEnergyConsumption = 0;
	}

	// Get rooms
	public List<Room> getRooms() {
		return rooms;
	}


	// Add room to the house
	public void addRoom(Room newRoom) {
		this.rooms.add(newRoom);
	}

	// Add occupant to the house
	public String addOccupant(String newOccupant) {
		this.occupants.add(newOccupant);
		return newOccupant;
	}

	// Get room by name
	public Room getRoomByName(String roomName) {
		for (Room room : rooms) {
			if (room.getRoomName().equals(roomName)) {
				return room;
			}
		}
		return null;
	}

	// Calculate energy consumption (placeholder, update with actual logic)
	public double getEnergyConsumption() {
		// Loop through rooms and appliances to calculate energy consumption
		double totalConsumption = 0;
		for (Room room : rooms) {
			totalConsumption += room.getEnergyConsumption();
		}
		this.currentEnergyConsumption = totalConsumption;
		return currentEnergyConsumption;
	}

	// Override toString method
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("House Name: ").append(houseName).append("\n");
		sb.append("Address: ").append(address).append("\n");

		sb.append("Rooms: \n");
		for (Room room : rooms) {
			sb.append("\t").append(room.toString()).append("\n");
		}

		sb.append("Occupants: \n");
		for (String occupant : occupants) {
			sb.append("\t").append(occupant).append("\n");
		}

		return sb.toString();
	}

}
