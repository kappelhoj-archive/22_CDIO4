package ASE.Controllers;

import java.io.IOException;
import java.net.Socket;

import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.ProtocolErrorException;
import ASE.exceptions.LogOutException;
import ASE.interfaces.IWeightCommunicator;
import ASE.interfaces.IWeightCommunicator.Buttons;

public class WeightController implements Runnable {
	MeasurementController measurementAdder;
	IWeightCommunicator weightCommunication;

	public WeightController(MeasurementController measurementAdder, Socket weightConnection) throws IOException {
		this.measurementAdder = measurementAdder;
		weightCommunication = new WeightCommunicator(weightConnection);
	}

	@Override
	public void run() {

		while (true) {
			try {
				login();
				// TODO Add methods for the other procedures.
			} catch (LogOutException e) {
				// Let the user log out if he presses that button.
			} catch (ProtocolErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void registerProduct() throws ProtocolErrorException,LogOutException{
		
	}
	
	private void login() throws ProtocolErrorException {

		Buttons buttonConfirmation =Buttons.NULL;
		do {
			try {
				buttonConfirmation = getUserIdentity();
				if (buttonConfirmation == Buttons.BACK)
					continue;
			} catch (LogOutException e) {
				// TODO Tell the user he is not logged out and can therefore not
				// logout.
			}
		} while (buttonConfirmation != Buttons.CONFIRM);

	}

	//TODO : Make this general by adding interfaces to relevant DAOS and DTOs.
	private Buttons getUserIdentity() throws ProtocolErrorException, LogOutException {
		try {
			String id = weightCommunication.askForInformation("Enter your id and press ok.");
			// TODO: get operator from system
			String oprName = "";

			weightCommunication.sendMessage("Please confirm your name" + oprName + " ->]");
			return weightCommunication.receiveButtonPush();

		} catch (InvalidReturnMessageException e) {
			return weightCommunication.receiveButtonPush();
		}

	}

}
