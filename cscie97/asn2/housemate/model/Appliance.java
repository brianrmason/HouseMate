package cscie97.asn2.housemate.model;

// Appliance.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

/*
 * Public class that facilitates checking the energy consumption
 * and types of appliances.
 */
public class Appliance {

	private String id;
	private double energyConsumption;

	// Constructor to initialize the appliance properties
	public Appliance(String applianceName, String applianceType, double energyUse) {
		this.id = applianceName.toLowerCase();
		this.energyConsumption = energyUse;
	}

	// Get the energy consumption of the appliance
	public double getEnergyConsumption() {
		return this.energyConsumption;
	}

	// Get the name of the appliance
	public String getName() {
		return id;
	}

}
