//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;

import cscie97.asn1.knowledge.engine.KnowledgeGraph;
import cscie97.asn2.housemate.model.Event;

public class BeerCountLowCommand implements Command {
    private Event event;
    private KnowledgeGraph knowledgeGraph;

    public BeerCountLowCommand(Event event) {
        this.event = event;
        this.knowledgeGraph = KnowledgeGraph.getInstance();
    }

    @Override
    public void execute() throws ControllerException {
        // Assuming that the current beer count is passed within the event's eventInfo
        int currentBeerCount = Integer.parseInt(event.getEventInfo());
        System.out.println("Beer count low detected: " + currentBeerCount + " beers left.");

        // Update the KnowledgeGraph with the current beer count
        knowledgeGraph.updateBeerCountStatus(currentBeerCount);
    }
}
