package cscie97.asn2.housemate.model;

public interface Observer {

	/**
	 * Called by the subject to notify the observer of a change.
	 *
	 * @param event An Event object containing relevant information about the change.
	 */
	void update(Event event);

}
