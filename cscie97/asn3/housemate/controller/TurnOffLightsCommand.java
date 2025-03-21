package cscie97.asn3.housemate.controller;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn2.housemate.model.Event;

public class TurnOffLightsCommand implements Command {
    private Event event;
    private KnowledgeGraph knowledgeGraph;

    public TurnOffLightsCommand(Event event) {
        this.event = event;
        this.knowledgeGraph = KnowledgeGraph.getInstance();
    }

    @Override
    public void execute() throws ControllerException {
        // Logic for turning on the lights
        String deviceName = event.getDeviceID();
        System.out.println("Turning off lights at device: " + deviceName);

        // Update the KnowledgeGraph to reflect that the lights are on
        knowledgeGraph.changeLightState(deviceName,"OFF");
    }
}
