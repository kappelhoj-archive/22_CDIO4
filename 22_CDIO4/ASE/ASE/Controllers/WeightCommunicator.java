package ASE.Controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.LogOutException;
import ASE.exceptions.ProtocolErrorException;
import ASE.interfaces.IWeightCommunicator;

/**
 * 
 * @author Simon + Arvid
 *
 */
public class WeightCommunicator implements IWeightCommunicator {

	Socket mySocket;
	DataOutputStream outToWeight;
	BufferedReader inFromWeight;
	String previousMessageSent;
	String answerReceived;
	String previousMessageRecived;
	String splitAnswer[];

	public enum Protocol {
		RM20, P111, Tara, Measurement, Brutto, DisplayClean, Quit, StartUp, CleanRM20
	}
	/**
	 * Creation of mySocket, outToWeight and inFromWeight as a socket,
	 * DataOutputStream and BufferedReader respectively.
	 * 
	 * @param mySocket
	 *            Socket recieved from WeightController.
	 * @throws IOException
	 *             In case any of an error, an IOException is thrown.
	 */
	public WeightCommunicator(Socket mySocket) throws IOException {
		this.mySocket = mySocket;
		outToWeight = new DataOutputStream(mySocket.getOutputStream());
		inFromWeight = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	}
	/**
	 * Method to send a Protocol to the weight on the this socket.
	 * 
	 * @param protocol
	 *            The protocol that is sent to the weight.
	 * @param message
	 *            The message sent alongside the protocol.
	 */
	public void sendProtocol(Protocol protocol, String message) {
		try {
			switch (protocol) {
			
			case CleanRM20:
				outToWeight.writeBytes("RM20 0" + "\r" + "\n");
				break;
			case RM20:
				outToWeight.writeBytes("RM20 8 \"" + message + "\" \"\" \"&3\"" + "\n");
				break;
			case Measurement:
				outToWeight.writeBytes("S" + "\r" + "\n");
				break;
			case Brutto:
				outToWeight.writeBytes("B " + message + "\r" + "\n");
				break;
			case DisplayClean:
				outToWeight.writeBytes("DW" + "\r" + "\n");
				break;
			case P111:
				outToWeight.writeBytes("P111 \"" + message + "\"" + "\n");
				break;
			case Tara:
				outToWeight.writeBytes("T" + "\r" + "\n");
				break;
			case Quit:
				outToWeight.writeBytes("Q" + "\r" + "\n");
				break;
			case StartUp:
				outToWeight.writeBytes("K 3" + "\r" + "\n");
				break;

			default:
				break;

			}
		} catch (Exception e) {

		}
	}
	/**
	 * Method which looks for which buttons have been pressed on the weight, and
	 * responds in kind.
	 */
	@Override
	public Buttons receiveButtonPush() throws ProtocolErrorException, LogOutException {
		if(previousMessageRecived!=null)
		{
			answerReceived=previousMessageRecived;
			previousMessageRecived=null;
		}
		else{answerReceived = waitForAnswer();}

		if (answerReceived.contains("K C 4")) {
			return Buttons.CONFIRM;
		}

		if (answerReceived.contains("K C 2")) {
			return Buttons.BACK;
		}

		if (answerReceived.contains("K R 3")) {
			throw new LogOutException(answerReceived);
		} else{throw new ProtocolErrorException(answerReceived);}


	}
	/**
	 * Method which sends a String message, and then checks to see if it
	 * receives a response.
	 */
	@Override
	public void sendMessage(String message) throws InvalidReturnMessageException {
		sendProtocol(Protocol.P111, message);

		try {
			answerReceived = waitForAnswer();
		} catch (ProtocolErrorException e1) {
			// TODO Auto-generated catch block
			throw new InvalidReturnMessageException(e1.getMessage());
		}
		splitAnswer = answerReceived.split(" ");
		splitAnswer[1]=String.valueOf(splitAnswer[1].charAt(0));
		if(splitAnswer[0].contains("P111")&&splitAnswer[1].contains("A"))
		{
			return;
		}
		else{
			previousMessageRecived=answerReceived;
			throw new InvalidReturnMessageException(answerReceived);
		}

	}
	/**
	 * Method to return the answer given by the weight back to the user.
	 */
	@Override
	// ..............................
	public String askForInformation(String message) throws InvalidReturnMessageException {
		// TODO Auto-generated method stub
		sendProtocol(Protocol.RM20, message);

		try {
			answerReceived = waitForAnswer();
		} catch (ProtocolErrorException e1) {
			// TODO Auto-generated catch block
			throw new InvalidReturnMessageException(e1.getMessage());
		}

		splitAnswer = answerReceived.split(" ");
		splitAnswer[1]=String.valueOf(splitAnswer[1].charAt(0));
		//Checks for acknowledgement 
		try {
			if(splitAnswer[0].contains("RM20")&&splitAnswer[1].contains("B")){
				answerReceived=waitForAnswer();
				splitAnswer = answerReceived.split(" ");
				splitAnswer[1]=String.valueOf(splitAnswer[1].charAt(0));
			}
			else{
				previousMessageRecived=answerReceived;
				throw new ProtocolErrorException(answerReceived);
			}
			//checks for answere
			if(splitAnswer[0].contains("RM20")&&splitAnswer[1].contains("A"))
			{
				splitAnswer=answerReceived.split("\"");
				return splitAnswer[1];
			}
			else{
				previousMessageRecived=answerReceived;
				throw new ProtocolErrorException(answerReceived);
			}
		} catch (ProtocolErrorException e) {
			// TODO Auto-generated catch block
			throw new InvalidReturnMessageException(e.getMessage());
		}
	}
	/**
	 * Simple method to restart the weight display.
	 */
	@Override
	public void restartWeightDisplay() {
		cleanStream();
		sendProtocol(Protocol.DisplayClean, null);
		try {
			answerReceived =waitForAnswer();
		} catch (ProtocolErrorException e1) {
		}
		splitAnswer = answerReceived.split(" ");
		splitAnswer[1]=String.valueOf(splitAnswer[1].charAt(0));

		if(splitAnswer[0].contains("DW")&&splitAnswer[1].contains("A"))
		{	
			return;
		}
		else {restartWeightDisplay();}



	}
	/**
	 * Simple method to stop the weight in it's current operation and quit.
	 */
	@Override
	public void stopWeight() {
		sendProtocol(Protocol.Quit, null);
	}
	/**
	 * Method to tara the weight. It looks to see if the weight responds
	 * positively to its command.
	 */
	@Override
	public void taraWeight() throws ProtocolErrorException {
		sendProtocol(Protocol.Tara, null);
		answerReceived=waitForAnswer();
		splitAnswer = answerReceived.split(" ");
		splitAnswer[1]=String.valueOf(splitAnswer[1].charAt(0));
		if(splitAnswer[0].contains("T")&&splitAnswer[1].contains("S"))
		{	
			return;
		}
		else{
			previousMessageRecived=answerReceived; 
			throw new ProtocolErrorException(answerReceived);}

	}
	/**
	 * Returns the currently weighted amount from the weight to the user.
	 */
	@Override
	public double getWeight() throws ProtocolErrorException {

		sendProtocol(Protocol.Measurement, null);
		answerReceived = waitForAnswer();
		String[] splitAnswer=answerReceived.split(" ");
		if (splitAnswer[0].contains("S")&&splitAnswer[1].contains("S"))
		{
			return Double.parseDouble(splitAnswer[splitAnswer.length-2]);
		}
		else{
			previousMessageRecived=answerReceived;
			throw new ProtocolErrorException(answerReceived);
		}

	}
	/**
	 * Method to clean the current stream of information from the weight.
	 */
	// TODO Look at exception
	public void cleanStream(){
		sendProtocol(Protocol.CleanRM20, null);
		sendProtocol(Protocol.StartUp, null);
		try {
			while(true){
				String answer = waitForAnswer();
				splitAnswer = answer.split(" ");
				splitAnswer[1]=String.valueOf(splitAnswer[1].charAt(0));
				if(splitAnswer[0].contains("K")&&splitAnswer[1].contains("A"))
				{

					return;
				}

			}
		} catch (ProtocolErrorException e) {
			// TODO Auto-generated catch block

		}
	}
	public String waitForAnswer() throws ProtocolErrorException {
		try {
			while (!inFromWeight.ready()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					throw new ProtocolErrorException(e.getMessage());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProtocolErrorException(e.getMessage());
		}
		try {
			answerReceived = inFromWeight.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProtocolErrorException(e.getMessage());
		}
		return answerReceived;
	}
	
	/**
	 * Method to check what kind of acknowledgement is sent back from the
	 * weight.
	 * 
	 * @param prevProtocol
	 *            The previous protocol sent of to the weight.
	 * @param answer
	 *            The answer returned by the weight.
	 * @return Returns False in case the method fails.
	 * @throws ProtocolErrorException
	 *             Throws an exception in case the acknowledgement is not
	 *             recognized.
	 */




//	public boolean checkAcknowledgement(Protocol prevProtocol, String answer) throws ProtocolErrorException {
//		splitAnswer = answer.split(" ");
//		splitAnswer[1]=String.valueOf(splitAnswer[1].charAt(0));
//		switch (prevProtocol) {
//		case RM20:
//			switch (splitAnswer[1]) {
//			case "B":
//				return true;
//			default:
//				previousMessageRecived = answer;
//				throw new ProtocolErrorException(answer);
//			}
//		case P111:
//
//			switch (splitAnswer[1]) {
//			case "A":
//				return true;
//			default:
//				previousMessageRecived = answer;
//				throw new ProtocolErrorException(answer);
//			}
//		case DisplayClean:
//			switch (splitAnswer[1]) {
//			case "A":
//				return true;
//			default:
//				previousMessageRecived = answer;
//				throw new ProtocolErrorException(answer);
//			}
//		case Tara:
//			switch (splitAnswer[1]) {
//			case "S":
//				return true;
//			default:
//				previousMessageRecived = answer;
//				throw new ProtocolErrorException(answer);
//			}
//		case Measurement:
//			switch (splitAnswer[1]) {
//			case "S":
//				return true;
//			default:
//				previousMessageRecived = answer;
//				throw new ProtocolErrorException(answer);
//			}
//		case StartUp:
//			switch (splitAnswer[1]) {
//			case "A":
//				return true;
//			default:
//				previousMessageRecived = answer;
//				throw new ProtocolErrorException(answer);
//			}
//		default:
//			return false;
//		}
//
//	}

	// public String RM20(String message) throws IOException,
	// ProtocolErrorException {
	// String out = "";
	// outToWeight.writeBytes("RM20 8 \"" + message + "\" \"\" \"&3\"" + "\n");
	// out = waitForAnswer();
	// checkReturnMessage(message, out);
	// return out;
	// }
	//
	// public void P111(String message) throws IOException,
	// ProtocolErrorException {
	// String out = "";
	// outToWeight.writeBytes("P111 \"" + message + "\"" + "\n");
	// out = waitForAnswer();
	// checkReturnMessage(message, out);
	// }

	/**
	 * Check if the answer received has a special function or if it is and
	 * error.
	 * 
	 * @param answerReceived
	 */
	//	public String checkReturnMessage(String previousMessageSent, String answerReceived) throws ProtocolErrorException {
	//		if (previousMessageSent != null) {
	//			if (previousMessageSent.equals(answerReceived)) {
	//
	//			}
	//		}
	//
	//		String splitAnswer[] = answerReceived.split(" ");
	//		switch (splitAnswer[0]) {
	//		case "RM20":
	//			switch (splitAnswer[1]) {
	//			case "A":
	//				break;
	//			case "B":
	//				break;
	//			default:
	//				throw new ProtocolErrorException(answerReceived);
	//			}
	//
	//		case "P111":
	//			switch (splitAnswer[1]) {
	//			case "A":
	//				break;
	//			default:
	//				throw new ProtocolErrorException(answerReceived);
	//			}
	//
	//		default:
	//			break;
	//		}
	//
	//		return answerReceived;
	//	}


	// @Override
	// public void flush() {
	// // TODO: Redo this method at some point.
	// try {
	// sendCommand("RM20 0");
	// try {
	// TimeUnit.SECONDS.sleep(2);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// while (inFromWeight.ready()) {
	// inFromWeight.readLine();
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }
	//

}
