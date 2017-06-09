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

	public WeightCommunicator(Socket mySocket) throws IOException {
		this.mySocket = mySocket;
		outToWeight = new DataOutputStream(mySocket.getOutputStream());
		inFromWeight = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	}

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

	@Override
	public Buttons receiveButtonPush() throws ProtocolErrorException, LogOutException {
		answerReceived = waitForAnswer();
		if (answerReceived.contains("K C 4")) {
			return Buttons.CONFIRM;
		}

		if (answerReceived.contains("K R 3")) {
			return Buttons.BACK;
		}

		if (answerReceived.contains("K C 2")) {
			throw new LogOutException(answerReceived);
		} else
			throw new ProtocolErrorException(answerReceived);

	}

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
		sendProtocol(Protocol.RM20, answerReceived);
		return answerReceived;
	}

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

	@Override
	public void stopWeight() {
		sendProtocol(Protocol.Quit, null);
	}

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

	@Override
	public double getWeight() throws ProtocolErrorException {

		sendProtocol(Protocol.Measurement, null);
		answerReceived = waitForAnswer();
		if (checkAcknowledgement(Protocol.Measurement, answerReceived))
		{
			return Double.parseDouble(splitAnswer[4]);
		}
		else{throw new ProtocolErrorException(answerReceived);
		}

	}

	// TODO Look at exception
	public void cleanStream(){

		try {
			while (inFromWeight.ready()) {
				waitForAnswer();
			}
		} catch (ProtocolErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}






	public boolean checkAcknowledgement(Protocol prevProtocol, String answer) throws ProtocolErrorException {
		splitAnswer = answer.split(" ");
		splitAnswer[1]=String.valueOf(splitAnswer[1].charAt(0));
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
