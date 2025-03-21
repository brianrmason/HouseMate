package cscie97.asn2.housemate.model;

import java.util.List;

public interface Subject {

	/**
	 * Registers an observer to receive notifications.
	 *
	 * @param observer The observer to attach.
	 */
	void attach(Observer observer);

	/**
	 * Removes an observer from receiving notifications.
	 *
	 * @param observer The observer to detach.
	 */
	void detach(Observer observer);

	/**
	 * Notifies all registered observers of a change by sending an Event object.
	 *
	 * @param event An Event object containing details about the change.
	 */
	void notifyObservers(Event event);

}
