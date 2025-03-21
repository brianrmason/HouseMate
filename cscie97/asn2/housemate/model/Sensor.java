package cscie97.asn2.housemate.model;

// Sensor.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

/*
 * Public class that records the data from a sensor
 */
public class Sensor {

	private String id;

	// Constructor to initialize sensor properties
	public Sensor(String sensorName, String sensorType) {
		this.id = sensorName.toLowerCase();
	}

	// Record data from the sensor
	public void recordData() {
		System.out.println("Recording data from sensor: " + id);
	}

    public String getName() {
		return id;
    }
}

