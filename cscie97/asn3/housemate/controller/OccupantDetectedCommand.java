//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn2.housemate.model.Event;

public class OccupantDetectedCommand implements Command {
    private Event event;
    private KnowledgeGraph knowledgeGraph;

    public OccupantDetectedCommand(Event event) {
        this.event = event;
        this.knowledgeGraph = KnowledgeGraph.getInstance();
    }

    @Override
    public void execute() throws ControllerException {
        // Logic for detecting occupant presence
        String occupantName = event.getDeviceID();  // Assuming occupant's ID or name is in DeviceID field
        System.out.println("Detecting occupant presence: " + occupantName);

        // Update the KnowledgeGraph to reflect that the occupant has been detected
        knowledgeGraph.updateOccupantPresence(occupantName, String.valueOf(true));
        System.out.println("Occupant " + occupantName + " detected and presence updated in the system.");
    }
}
