//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn2.housemate.model.Event;

public class TimeToCookZeroCommand implements Command {
    private Event event;
    private KnowledgeGraph knowledgeGraph;

    public TimeToCookZeroCommand(Event event) {
        this.event = event;
        this.knowledgeGraph = KnowledgeGraph.getInstance();
    }

    @Override
    public void execute() throws ControllerException {
        // Extract appliance details from the event
        String applianceName = event.getDeviceID();  // Assuming the appliance name is in the DeviceID field
        System.out.println("Time to cook has reached zero for appliance: " + applianceName);

        // Update or check the cooking status in the KnowledgeGraph
        knowledgeGraph.updateApplianceStatus(applianceName, "cooking", "complete");

        // Trigger a notification or alert
        System.out.println("Cooking time complete. Alert sent for appliance: " + applianceName);
    }
}
