package ASE.interfaces;

import ASE.exceptions.InvalidReturnMessage;
import ASE.exceptions.ProtocolErrorException;


public interface IWeightCommunicator {
	public enum Buttons {
		CONFIRM, /* When the user confirms some information */
		BACK, /* When the user wants to go back to the last "menu" */
		LOGOUT /* When the user dosen't want to do anymore measurement. */
	}

	/**
	 * This method checks if a button was pushed.
	 * 
	 * @return The button that was clicked.
	 * @throws ProtocolErrorException
	 *             - If the message wasnt a button.
	 */
	public Buttons receiveButtonPush() throws ProtocolErrorException;

	/**
	 * This method sends a message to the weight to display on the screen.
	 * 
	 * @param message
	 *            to show on weight display
	 * @throws InvalidReturnMessage
	 *             when an unknown message is received. It is recommended to use
	 *             the receiveButtonPush method if this happen.
	 */
	public void sendMessage(String message) throws InvalidReturnMessage;

	/**
	 * This methods send a message to the weight and waits for a answer from the
	 * user.
	 * 
	 * @param message
	 *            to show on weight display
	 * @return The answer from the user.
	 * @throws InvalidReturnMessage
	 *             when an unknown message is received. It is recommended to use
	 *             the receiveButtonPush method if this happen.
	 */
	public String askForInformation(String message) throws InvalidReturnMessage;

	/**
	 * Clears the display on the weight and all currently ungoing functions.
	 * Then resets the weight to begin state.
	 */
	public void restartWeightDisplay();
	
	/**
	 * Turn of the weight.
	 */
	public void stopWeight();

}
