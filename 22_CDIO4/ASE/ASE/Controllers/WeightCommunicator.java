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
		RM20, P111, Tara, Measurement, Brutto, DisplayClean, Quit, StartUp
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
	 *            The protocal that is sent to the weight.
	 * @param message
	 *            The message sent alongside the protocol.
	 */
	public void sendProtocol(Protocol protocol, String message) {
		try {
			switch (protocol) {
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
		try {
			checkAcknowledgement(Protocol.P111, answerReceived);
		} catch (ProtocolErrorException e) {
			throw new InvalidReturnMessageException(e.getMessage());
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
		
		try {
			if(checkAcknowledgement(Protocol.RM20,answerReceived)){
				return waitForAnswer();
			}
		} catch (ProtocolErrorException e) {
			// TODO Auto-generated catch block
			throw new InvalidReturnMessageException(e.getMessage());
		}
		return null;
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if(checkAcknowledgement(Protocol.DisplayClean, answerReceived))
			{

				// TODO Er dette farligt ????
			}else {restartWeightDisplay();}
		} catch (ProtocolErrorException e) {
			e.printStackTrace();
		}



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
		if(checkAcknowledgement(Protocol.Tara, answerReceived))
		{	
			return;
		}
		else throw new ProtocolErrorException(answerReceived);

	}
	/**
	 * Returns the currently weighted amount from the weight to the user.
	 */
	@Override
	public double getWeight() throws ProtocolErrorException {
		
		sendProtocol(Protocol.Measurement, null);
		answerReceived = waitForAnswer();
		String[] splitAnswer=answerReceived.split(" ");
		if (checkAcknowledgement(Protocol.Measurement, answerReceived))
		{
			for(int i =0; splitAnswer.length>i;i++)
			{
				if(splitAnswer[i].matches("\\d"))
				{
					return Double.parseDouble(splitAnswer[i]);
				}
			}
			
		}
		else{throw new ProtocolErrorException(answerReceived);
		}
		throw new ProtocolErrorException(answerReceived);

	}
	/**
	 * Method to clean the current stream of information from the weight.
	 */
	// TODO Look at exception
	public void cleanStream(){

		try {
			sendProtocol(Protocol.StartUp, null);
			String temp="";
			while (!checkAcknowledgement(Protocol.StartUp, temp)) {
				temp=waitForAnswer();
			}
		} catch (ProtocolErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
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




	public boolean checkAcknowledgement(Protocol prevProtocol, String answer) throws ProtocolErrorException {
		splitAnswer = answer.split(" ");
		splitAnswer[splitAnswer.length]=String.valueOf(splitAnswer[splitAnswer.length].charAt(0));
		switch (prevProtocol) {
		case RM20:
			switch (splitAnswer[1]) {
			case "B":
				return true;
			default:
				previousMessageRecived = answer;
				throw new ProtocolErrorException(answer);
			}
		case P111:

			switch (splitAnswer[1]) {
			case "A":
				return true;
			default:
				previousMessageRecived = answer;
				throw new ProtocolErrorException(answer);
			}
		case DisplayClean:
			switch (splitAnswer[1]) {
			case "A":
				return true;
			default:
				previousMessageRecived = answer;
				throw new ProtocolErrorException(answer);
			}
		case Tara:
			switch (splitAnswer[1]) {
			case "S":
				return true;
			default:
				previousMessageRecived = answer;
				throw new ProtocolErrorException(answer);
			}
		case Measurement:
			switch (splitAnswer[1]) {
			case "S":
				return true;
			default:
				previousMessageRecived = answer;
				throw new ProtocolErrorException(answer);
			}
		case StartUp:
			switch (splitAnswer[1]) {
			case "A":
				return true;
			default:
				previousMessageRecived = answer;
				throw new ProtocolErrorException(answer);
			}
		default:
			break;
		}
		return false;

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
