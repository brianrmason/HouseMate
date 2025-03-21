package cscie97.asn2.housemate.model;

// HouseMateException.java
// Brian Mason brm3153@g.harvard.edu
// 10/7/2024

/*
 * Public custom exception class that extends Throwable
 */
public class HouseMateException extends Throwable {

	private String errorCode;
	private String errorMessage;

	// Constructor that initializes the error code and message
	public HouseMateException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public HouseMateException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	// Getters for retrieving error details
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		return "HouseMateException [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

}
