package ASE.tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import ASE.Controllers.WeightCommunicator;
import ASE.exceptions.ProtocolErrorException;

public class WeightSocketTest {
	static Socket client;
	static ServerSocket weight;
	static WeightCommunicator weightCommunicator;
	static Socket connection;
	static DataOutputStream outToServer;
	static BufferedReader inFromServer;

	/**
	 * 
	 * @author Simon
	 * This class is just to have a quick weight emulator.
	 */
	public static void main(String[] args) throws IOException {
		weight = new ServerSocket(8000);
		connection = weight.accept();
		System.out.println(connection);
		outToServer = new DataOutputStream(connection.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		Input thread = new Input(inFromServer);
		new Thread(thread).start();
		while(true)
		{
			@SuppressWarnings("resource")
			Scanner keyboard = new Scanner(System.in);
			outToServer.writeBytes(keyboard.nextLine()+ "\r" + "\n");
		}

	}


	
}
class Input implements Runnable {
	BufferedReader inFromServer;
	Input(BufferedReader inFromServer){
		this.inFromServer=inFromServer;
	}
	public void run() {
		while (true) {
			try {
				System.out.println(waitForAnswer());
			} catch (ProtocolErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	public String waitForAnswer() throws ProtocolErrorException {
		String answerReceived;
		try {
			while (!inFromServer.ready()) {
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
			answerReceived = inFromServer.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ProtocolErrorException(e.getMessage());
		}
		return answerReceived;
	}
}
