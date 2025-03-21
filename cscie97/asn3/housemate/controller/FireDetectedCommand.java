//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn2.housemate.model.Event;



public class FireDetectedCommand implements Command {
    private Event event;
    private KnowledgeGraph knowledgeGraph;

    public FireDetectedCommand(Event event) {
        this.event = event;
        this.knowledgeGraph = KnowledgeGraph.getInstance();
    }

    @Override
    public void execute() throws ControllerException {
        // Fire detection logic, e.g., updating the KnowledgeGraph
        String deviceName = event.getDeviceID();
        System.out.println("Fire detected at device: " + deviceName);

        knowledgeGraph.updateFireStatus(deviceName,true);

    }
}


