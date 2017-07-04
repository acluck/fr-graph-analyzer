package parse;

/**
 * Exception thrown when a document being parsed is invalid.
 * 
 * @author Adam Luck
 */
public class InvalidDocumentException extends Exception {

	/**	Default serial version UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Passes the exception message to Exception's constructor.
	 * @param message Message to be passed to Exception's constructor.
	 */
	public InvalidDocumentException(String message) {
		super(message);
	}
	
	/**
	 * Calls the parameterized constructor with the default message for InvalidDocumentException.
	 */
	public InvalidDocumentException() {
		super("PDF file could not be parsed.");
	}
	
}
