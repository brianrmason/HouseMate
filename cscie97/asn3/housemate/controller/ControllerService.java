//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024

package cscie97.asn3.housemate.controller;

import cscie97.asn2.housemate.model.Observer;
import cscie97.asn2.housemate.model.HouseMateModelService;
import cscie97.asn2.housemate.model.Event;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
	Controller service implements the observer interface. Interacts with the
	model service to instantiate the relevant command class associated with
	the event.
 */
public class ControllerService implements Observer {

	private HouseMateModelService modelService;
	private static ControllerService instance;
	private static final Pattern COMMAND_PATTERN = Pattern.compile("([A-Za-z]+)(?::(\\d+))?");


	public ControllerService(HouseMateModelService modelService) {
		this.modelService = modelService;
	}

	/*
		Singleton implementation of the controller service.
	 */
	public static ControllerService getInstance(HouseMateModelService modelService) {
		if (instance == null) {
			instance = new ControllerService(modelService);
		}
		return instance;
	}

	/*
		Process event uses Java reflection and regex to look through the command classes
		to dynamically call a command related to an event.
	 */
	public void processEvent(Event event) {
		String eventInfo = event.getEventInfo();
		Matcher matcher = COMMAND_PATTERN.matcher(eventInfo);

		if (matcher.matches()) {
			String commandType = matcher.group(1);

			try {
				// Dynamically construct the command class name based on the commandType
				String className = "cscie97.asn3.housemate.controller." + commandType;
				Class<?> commandClass = Class.forName(className);

				Command command = (Command) commandClass.getConstructor(Event.class).newInstance(event);

				performCommand(command);

			} catch (ClassNotFoundException e) {
				System.err.println("Command class not found: " + commandType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Invalid eventInfo format: " + eventInfo);
		}
	}

	/*
		Perform command calls the execute method for the specified command.
	 */
	public void performCommand(Command command) throws ControllerException {
		if (command == null) {
			System.out.println("Command is null");
			return;
		}
		command.execute();
	}

	/*
	 	The update method calls the processEvent method above.
	 */
	@Override
	public void update(Event event) {
		System.out.println("Received event: " + event);
		processEvent(event);
	}
}

