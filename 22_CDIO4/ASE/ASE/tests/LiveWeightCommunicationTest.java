package ASE.tests;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ASE.Controllers.WeightCommunicator;
import ASE.exceptions.ProtocolErrorException;

public class LiveWeightCommunicationTest {
	public static Socket client;
	static WeightCommunicator weightCommunicator;

	public static void main(String[] args) throws UnknownHostException, IOException {
		client = new Socket("169.254.2.2", 8000);
		weightCommunicator = new WeightCommunicator(client);

		
		weightCommunicator.restartWeightDisplay();
		
		System.out.println("Tryk p� knappe Simon!");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		 try {
//		 System.out.println(weightCommunicator.getWeight());
//		 } catch (ProtocolErrorException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }
		
//		 try {
//		 System.out.println(weightCommunicator.receiveButtonPush());
//		
//		 System.out.println(weightCommunicator.receiveButtonPush());
//		
//		 System.out.println(weightCommunicator.receiveButtonPush());
//		
//		 } catch (ProtocolErrorException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 } catch (LogOutException e) {
//		 // TODO Auto-generated catch block
//		 e.printStackTrace();
//		 }

//		 try {
//		 weightCommunicator.sendMessage("Hej Mads");
//		
//		 } catch (InvalidReturnMessageException e) {
//		 try {
//		 System.out.println(weightCommunicator.receiveButtonPush());
//		 } catch (ProtocolErrorException | LogOutException e1) {
//		 // TODO Auto-generated catch block
//		 e1.printStackTrace();
//		 }
//		 }

//		 try {
//		 System.out.println(weightCommunicator.askForInformation("Skriv id"));
//		 } catch (InvalidReturnMessageException e) {
//			 try {
//				System.out.println(weightCommunicator.receiveButtonPush());
//			} catch (ProtocolErrorException | LogOutException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		 }
		
		 try {
		 weightCommunicator.taraWeight();
		 System.out.println(weightCommunicator.getWeight());
		 } catch (ProtocolErrorException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }

		client.close();
	}

}
