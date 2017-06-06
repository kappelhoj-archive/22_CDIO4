package ASE.tests.weightController;

import java.util.LinkedList;
import java.util.Queue;

import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.LogOutException;
import ASE.exceptions.ProtocolErrorException;
import ASE.interfaces.IWeightCommunicator;

public class WeightCommunicatorStub implements IWeightCommunicator {

	Queue<Buttons> buttonsPresssedOnWeight;
	Queue<String> textFromWeight;
	Queue<Double> measurementFromWeight;

	Queue<String> messageToWeight;

	WeightCommunicatorStub() {
		buttonsPresssedOnWeight = new LinkedList<Buttons>();
		textFromWeight = new LinkedList<String>();
		measurementFromWeight = new LinkedList<Double>();
		messageToWeight = new LinkedList<String>();
	}

	@Override
	public Buttons receiveButtonPush() throws ProtocolErrorException, LogOutException {
		Buttons buttonPress = buttonsPresssedOnWeight.remove();
		switch (buttonPress) {
		case LOGOUT:
			throw new LogOutException("User logged out");

		case NULL:
			throw new ProtocolErrorException("Exception.");
		default:
			return buttonPress;
		}
		
	}

	@Override
	public void sendMessage(String message) throws InvalidReturnMessageException {
		messageToWeight.add(message);
	}

	@Override
	public String askForInformation(String message) throws InvalidReturnMessageException {
		messageToWeight.add(message);
		
		String text = textFromWeight.remove();
		if(text==null){
			throw new InvalidReturnMessageException("Unexpected return message.");
		}
		return text;
	}

	@Override
	public void taraWeight() throws ProtocolErrorException{
		System.out.println("Tara weight");

	}

	@Override
	public double getWeight() throws ProtocolErrorException{
		System.out.println("Measure weight");
		return measurementFromWeight.remove();
	}

	@Override
	public void restartWeightDisplay() {
		System.out.println("Display reset.");

	}

	@Override
	public void stopWeight() throws ProtocolErrorException{
		System.out.println("You cant stop this weight.");

	}

}
