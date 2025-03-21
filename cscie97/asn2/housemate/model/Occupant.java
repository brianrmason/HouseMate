package cscie97.asn2.housemate.model;

// Occupant.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

/*
 * Public class for getting the location of an occupant
 */
public class Occupant {

	private Room location;

	// Constructor
	public Occupant(String name, String type) {
		this.location = null;
	}

	// Get the location of the occupant
	public Room getLocation() {
		return location;
	}


}
