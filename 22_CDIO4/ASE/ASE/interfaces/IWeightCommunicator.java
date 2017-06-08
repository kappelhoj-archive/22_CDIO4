package ASE.interfaces;

import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.LogOutException;
import ASE.exceptions.ProtocolErrorException;


public interface IWeightCommunicator {
	public enum Buttons {
		CONFIRM, /* When the user confirms some information  K C 4 */
		BACK, /* When the user wants to go back to the last "menu" K C 2 */
		LOGOUT, /* When the user dosen't want to do anymore measurement. K R 3 */
		NULL /* Standard value.*/
	}

	/**
	 * This method checks if a button was pushed.
	 * 
	 * @return The button that was clicked.
	 * @throws ProtocolErrorException
	 *             - If the message wasnt a button.
	 */
	public Buttons receiveButtonPush() throws ProtocolErrorException, LogOutException;

	/**
	 * This method sends a message to the weight to display on the screen.
	 * 
	 * @param message
	 *            to show on weight display
	 * @throws InvalidReturnMessageException
	 *             when an unknown message is received. It is recommended to use
	 *             the receiveButtonPush method if this happen.
	 */
	public void sendMessage(String message) throws InvalidReturnMessageException;

	/**
	 * This methods send a message to the weight and waits for a answer from the
	 * user.
	 * 
	 * @param message
	 *            to show on weight display
	 * @return The answer from the user.
	 * @throws InvalidReturnMessageException
	 *             when an unknown message is received. It is recommended to use
	 *             the receiveButtonPush method if this happen.
	 */
	public String askForInformation(String message) throws InvalidReturnMessageException;

	/**
	 * Tara the weight.
	 */
	public void taraWeight()throws ProtocolErrorException;
	
	/**
	 * Get the weight measured on the weight.
	 * @return Get the current weight displayed on the weight.
	 */
	public double getWeight()throws ProtocolErrorException;
	
	
	/**
	 * Clears the display on the weight and all currently ungoing functions.
	 * Then resets the weight to begin state.
	 */
	public void restartWeightDisplay();
	
	/**
	 * Turn of the weight.
	 */
	public void stopWeight()throws ProtocolErrorException;

}
