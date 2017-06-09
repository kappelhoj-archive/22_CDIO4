package ASE.tests;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ASE.Controllers.WeightCommunicator;

public class LiveWeightCommunicationTest {
	public static Socket client;
	static WeightCommunicator weightCommunicator;
	public static void main(String[] args) throws UnknownHostException, IOException {
		client= new Socket("localhost", 8000);
		weightCommunicator = new WeightCommunicator(client);
		// TODO Auto-generated method stub

	}

}
