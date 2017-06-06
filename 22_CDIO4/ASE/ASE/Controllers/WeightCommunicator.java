package ASE.Controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.ProtocolErrorException;
import ASE.interfaces.IWeightCommunicator;
/**
 * 
 * @author arvid
 *
 */
public class WeightCommunicator implements IWeightCommunicator {

	Socket mySocket;
	DataOutputStream outToWeight;
	BufferedReader inFromWeight;
	public WeightCommunicator(Socket mySocket)throws IOException{
		this.mySocket = mySocket;
		outToWeight = new DataOutputStream(mySocket.getOutputStream());
		inFromWeight = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	}
	
	@Override
	public Buttons receiveButtonPush() throws ProtocolErrorException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String message) throws InvalidReturnMessageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String askForInformation(String message) throws InvalidReturnMessageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restartWeightDisplay() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stopWeight() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	public String RM20(String message) throws IOException, ProtocolErrorException {
		String out = "";
		outToWeight.writeBytes("RM20 8 \"" + message + "\" \"\" \"&3\"" + "\n");
		out = waitForAnswer();
		checkReturnMessage(message, out);
		return out;
	}

	public void P111(String message) throws IOException, ProtocolErrorException {
		String out = "";
		outToWeight.writeBytes("P111 \"" + message + "\"" + "\n");
		out = waitForAnswer();
		checkReturnMessage(message, out);
	}

	/**
	 * Check if the answer received has a special function or if it is and
	 * error.
	 * 
	 * @param answerReceived
	 */
	public String checkReturnMessage(String previousMessageSent, String answerReceived) throws ProtocolErrorException {
		if (previousMessageSent != null) {
			if (previousMessageSent.equals(answerReceived)) {
				
			}
		}

		String splitAnswer[] = answerReceived.split(" ");
		switch (splitAnswer[0]) {
		case "RM20":
			switch (splitAnswer[1]) {
			case "A":
				break;
			case "B":
				break;
			default:
				throw new ProtocolErrorException(answerReceived, "Received Unknown");
			}

		case "P111":
			switch (splitAnswer[1]) {
			case "A":
				break;
			default:
				throw new ProtocolErrorException(answerReceived, "Received Unknown");
			}

		default:
			break;
		}

		return answerReceived;
	}

	public String waitForAnswer() throws IOException {
		while (!inFromWeight.ready()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Thread Interupted.");
			}
		}
		return inFromWeight.readLine();
	}

	@Override
	public void flush() {
		// TODO: Redo this method at some point.
		try {
			sendCommand("RM20 0");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (inFromWeight.ready()) {
				inFromWeight.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void taraWeight() throws ProtocolErrorException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getWeight() throws ProtocolErrorException {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
