//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn2.housemate.model.Event;

public class OccupantLeavingCommand implements Command {
    private Event event;
    private KnowledgeGraph knowledgeGraph;

    public OccupantLeavingCommand(Event event) {
        this.event = event;
        this.knowledgeGraph = KnowledgeGraph.getInstance();
    }

    @Override
    public void execute() throws ControllerException {
        // Extract occupant name from the event
        String occupantName = event.getDeviceID();  // Assuming occupant name is in the DeviceID field
        System.out.println("Occupant leaving: " + occupantName);

        // Update KnowledgeGraph to clear occupant's presence
        knowledgeGraph.clearOccupantPresence(occupantName);

        System.out.println("Presence cleared for occupant: " + occupantName);
    }
}