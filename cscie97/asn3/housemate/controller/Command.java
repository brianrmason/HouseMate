//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;
import cscie97.asn2.housemate.model.Event;

/*
	Command interface.
 */
public interface Command {

	public Event event = null;

	public void execute() throws ControllerException;
}
