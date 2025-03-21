//ControllerException.java
// Brian Mason brm3153@g.harvard.edu
//10/28/2024
package cscie97.asn3.housemate.controller;

/**
 * ControllerException is an exception class for the Housemate controller system.
 * It extends Exception and provides a way to encapsulate detailed error information.
 */
public class ControllerException extends Exception {
	private final String action;  // The action that caused the exception
	private final String reason;  // The reason for the exception

	/**
	 * Constructs a new ControllerException with the specified detail message, action, and reason.
	 *
	 * @param message The detailed error message
	 * @param action  The action being attempted when the exception occurred
	 * @param reason  The reason for the exception
	 */
	public ControllerException(String message, String action, String reason) {
		super(message);
		this.action = action;
		this.reason = reason;
	}

	/**
	 * Returns a string representation of the exception with the action and reason.
	 *
	 * @return Detailed error information
	 */
	@Override
	public String toString() {
		return super.toString() + " [Action: " + action + ", Reason: " + reason + "]";
	}
}
