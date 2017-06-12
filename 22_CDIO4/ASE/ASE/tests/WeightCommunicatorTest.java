package ASE.tests;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ASE.Controllers.WeightCommunicator;
import ASE.Controllers.WeightCommunicator.Protocol;
import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.LogOutException;
import ASE.exceptions.ProtocolErrorException;
import ASE.interfaces.IWeightCommunicator;
public class WeightCommunicatorTest {
	Socket client;
	ServerSocket weight;
	WeightCommunicator weightCommunicator;
	Socket connection;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	@Before
	public void setUp() throws Exception {
		weight = new ServerSocket(8000);
		client= new Socket("localhost", 8000);
		weightCommunicator = new WeightCommunicator(client);
		connection=weight.accept();
		outToServer = new DataOutputStream(connection.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}

	@After
	public void tearDown() throws Exception {
		weight.close();
		client.close();
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

	@Test
	public void testSendProtocol() throws ProtocolErrorException {
		weightCommunicator.sendProtocol(Protocol.P111, "hej");
		assertEquals("P111 \"hej\"",waitForAnswer());
	}

	@Test
	public void testReceiveButtonPush() throws ProtocolErrorException, IOException {
		try{
			outToServer.writeBytes("K R 3"+"\r" + "\n");
			weightCommunicator.receiveButtonPush();	
			fail("if hits this");		
		}
		catch(LogOutException e)
		{
			outToServer.writeBytes("K C 2"+"\r" + "\n");
			try {
				assertEquals(IWeightCommunicator.Buttons.BACK,weightCommunicator.receiveButtonPush());
			} catch (LogOutException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			outToServer.writeBytes("K C 4"+"\r" + "\n");
			try {
				assertEquals(IWeightCommunicator.Buttons.CONFIRM,weightCommunicator.receiveButtonPush());
			} catch (LogOutException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Test
	public void testSendMessage() throws ProtocolErrorException, InvalidReturnMessageException, IOException {
		outToServer.writeBytes("P111 A"+"\r" + "\n");
		weightCommunicator.sendMessage("hej");
	}

	@Test
	public void testAskForInformation() {
		fail("Not yet implemented");
	}

	@Test
	public void testRestartWeightDisplay() throws IOException {
		//		outToServer.writeBytes("P111 A"+"\r" + "\n");
		//		outToServer.writeBytes("P111 A"+"\r" + "\n");
		//		outToServer.writeBytes("P111 A"+"\r" + "\n");
		//		outToServer.writeBytes("P111 A"+"\r" + "\n");
		//		weightCommunicator.restartWeightDisplay();


	}

	@Test
	public void testStopWeight() throws ProtocolErrorException {
		weightCommunicator.stopWeight();
		assertEquals("Q", waitForAnswer());
	}

	@Test
	public void testTaraWeight() throws ProtocolErrorException, IOException {
		outToServer.writeBytes("T S"+"   1.232 kg"+"\r" + "\n");
		weightCommunicator.taraWeight();
	}

	@Test
	public void testGetWeight() throws ProtocolErrorException, IOException {
		outToServer.writeBytes("T S"+"   1.232 kg"+"\r" + "\n");	
		double temp = 1.232;
		if(temp== weightCommunicator.getWeight())
		{
			
		}
		else{fail("ups");}
	}

	@Test
	public void testCleanStream() {
		try {
			outToServer.writeBytes("P111 A"+"\r" + "\n");
			outToServer.writeBytes("P111 A"+"\r" + "\n");
			outToServer.writeBytes("P111 A"+"\r" + "\n");
			outToServer.writeBytes("P111 A"+"\r" + "\n");
			outToServer.writeBytes("K A"+"\r" + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		weightCommunicator.cleanStream();
		weightCommunicator.stopWeight();
		try {
			assertEquals("Q", waitForAnswer());
		} catch (ProtocolErrorException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckAcknowledgement() throws ProtocolErrorException {
		weightCommunicator.checkAcknowledgement(Protocol.P111, "P111 A"+"\r" + "\n");
		weightCommunicator.checkAcknowledgement(Protocol.RM20, "RM20 B"+"\r" + "\n");
		weightCommunicator.checkAcknowledgement(Protocol.DisplayClean, "DW A"+"\r" + "\n");
		weightCommunicator.checkAcknowledgement(Protocol.Tara, "T S"+"\r" + "\n");
		weightCommunicator.checkAcknowledgement(Protocol.Measurement, "S S"+"   1.232 kg"+"\r" + "\n");
		try
		{
			weightCommunicator.checkAcknowledgement(Protocol.P111, "P111 B"+"\r" + "\n");
			fail();
		}
		catch(ProtocolErrorException e){}
		try
		{
			weightCommunicator.checkAcknowledgement(Protocol.RM20, "RM20 A"+"\r" + "\n");
			fail();
		}
		catch(ProtocolErrorException e){}
		try
		{
			weightCommunicator.checkAcknowledgement(Protocol.DisplayClean, "DW H"+"\r" + "\n");
			fail();
		}
		catch(ProtocolErrorException e){}
		try
		{
			weightCommunicator.checkAcknowledgement(Protocol.Tara, "T K"+"\r" + "\n");
			fail();
		}
		catch(ProtocolErrorException e){}
		try
		{
			weightCommunicator.checkAcknowledgement(Protocol.Measurement, "S B"+"   1.232 kg"+"\r" + "\n");
			fail();
		}
		catch(ProtocolErrorException e){}
		
	}

	@Test
	public void testWaitForAnswer() throws IOException, ProtocolErrorException {
		outToServer.writeBytes("hej"+"\r" + "\n");
		assertEquals("hej",weightCommunicator.waitForAnswer());
	}

}
