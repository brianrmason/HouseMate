//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn2.housemate.model.Event;

public class OpenDoorCommand implements Command {
    private Event event;
    private KnowledgeGraph knowledgeGraph;

    public OpenDoorCommand(Event event) {
        this.event = event;
        this.knowledgeGraph = KnowledgeGraph.getInstance();
    }

    @Override
    public void execute() throws ControllerException {
        // Logic for opening the door
        String deviceName = event.getDeviceID();
        System.out.println("Opening door at device: " + deviceName);

        // Update the KnowledgeGraph to reflect that the door is open
        knowledgeGraph.updateDoorStatus(deviceName, "door", "OPEN");
    }
}
