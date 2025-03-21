//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn2.housemate.model.Event;

public class LocateOccupantCommand implements Command {
    private Event event;
    private KnowledgeGraph knowledgeGraph;

    public LocateOccupantCommand(Event event) {
        this.event = event;
        this.knowledgeGraph = KnowledgeGraph.getInstance();
    }

    @Override
    public void execute() throws ControllerException {
        // Logic for locating the occupant
        String occupantName = event.getDeviceID();  // Assuming the occupant's name or ID is in the DeviceID field
        System.out.println("Locating occupant: " + occupantName);

        // Query the KnowledgeGraph for the occupant’s location
        String location = knowledgeGraph.queryOccupantLocation(occupantName);

        if (location != null) {
            System.out.println("Occupant " + occupantName + " is located in: " + location);
        } else {
            System.out.println("Location for occupant " + occupantName + " is unknown.");
        }
    }
}
