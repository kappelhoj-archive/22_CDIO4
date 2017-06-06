package ASE.exceptions;

public abstract class ASEException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ASEException(String message){
		super(message);
	}

}
