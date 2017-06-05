package ASE.Controllers;

import java.io.IOException;
import java.net.Socket;

import ASE.exceptions.InvalidReturnMessage;
import ASE.exceptions.ProtocolErrorException;
import ASE.interfaces.IWeightCommunicator;
import ASE.interfaces.IWeightCommunicator.Buttons;

public class WeightController implements Runnable {
	MeasurementController measurementAdder;
	IWeightCommunicator weightCommunication;

	WeightController(MeasurementController measurementAdder, Socket weightConnection) throws IOException {
		this.measurementAdder = measurementAdder;
		weightCommunication = new WeightCommunicator(weightConnection);
	}

	@Override
	public void run() {

		while (true) {
			try {
				Buttons buttonConfirmation;
				
				do{
					buttonConfirmation = login();
					switch(buttonConfirmation){
					case BACK:
						continue;
					case LOGOUT:
					default:
						break;
					}
				}while(buttonConfirmation!=Buttons.CONFIRM);
				
				
				
			} catch (ProtocolErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Buttons login() throws ProtocolErrorException {
		try {
			String id = weightCommunication.askForInformation("Enter your id and press ok.");
			// TODO: get operator from system
			String oprName = "";

			weightCommunication.sendMessage("Please confirm your name" + oprName + " ->]");
			return weightCommunication.receiveButtonPush();

		} catch (InvalidReturnMessage e) {
			return weightCommunication.receiveButtonPush();
		}

	}

}
