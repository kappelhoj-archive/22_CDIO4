package ASE.exceptions;

public class ProtocolErrorException extends Exception {
	String returnedMessage;

	private static final long serialVersionUID = 1L;
	public ProtocolErrorException(String returnedMessage,String reason){
		super(reason);
		this.returnedMessage=returnedMessage;
	}
}
