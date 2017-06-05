package ASE.Controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import ASE.exceptions.ProtocolErrorException;

public class WeightController implements Runnable {
	MeasurementController measurementAdder;
	Socket weightConnection;
	DataOutputStream outToWeight;
	BufferedReader inFromWeight;

	public WeightController(MeasurementController measurementAdder, Socket weightConnection)
			throws UnknownHostException, IOException {
		this.measurementAdder = measurementAdder;
		this.weightConnection = weightConnection;
		outToWeight = new DataOutputStream(weightConnection.getOutputStream());
		inFromWeight = new BufferedReader(new InputStreamReader(weightConnection.getInputStream()));
	}

	@Override
	public void run() {
		while (true) {
			startMeasureMent();
		}
	}

	public void startMeasureMent() {

	}

	public void login() {

	}

	public String RM20(String message) throws IOException, ProtocolErrorException {
		String out = "";
		outToWeight.writeBytes("RM20 8 \"" + message + "\" \"\" \"&3\"" + "\n");
		out = waitForAnswer();
		checkReturnMessage(message,out);
		return out;
	}

	public void P111(String message) throws IOException, ProtocolErrorException {
		String out = "";
		outToWeight.writeBytes("P111 \"" + message + "\"" + "\n");
		out = waitForAnswer();
		checkReturnMessage(message,out);
	}

	/**
	 * Check if the answer received has a special function or if it is and
	 * error.
	 * 
	 * @param answerReceived
	 */
	public String checkReturnMessage(String previousMessageSent,String answerReceived) throws ProtocolErrorException {
		if(previousMessageSent!=null){
			
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

}
