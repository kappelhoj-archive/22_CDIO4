package exceptions;

/**
 * The InputException class is an exception inner class used in the
 * Validator class. The exception is thrown if the user inputs something
 * other than told.
 * 
 * @author Group 22
 *
 */
public class CollisionException extends DALException {
	// The serial id making us able to identify the object when saved and
	// loaded.
	private static final long serialVersionUID = 1884427058176762541L;

	/**
	 * Constructor.
	 * 
	 * @param msg
	 *            The error message.
	 */
	public CollisionException(String msg) {
		super(msg);
	}
}