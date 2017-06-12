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
import ASE.interfaces.IWeightCommunicator.Buttons;

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
		client = new Socket("localhost", 8000);
		weightCommunicator = new WeightCommunicator(client);
		connection = weight.accept();
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
		assertEquals("P111 \"hej\"", waitForAnswer());
	}

	@Test
	public void testReceiveButtonPush() throws IOException {
		try {
			try {
				// Test back
				outToServer.writeBytes("K C 2" + "\r" + "\n");
				Buttons actual = weightCommunicator.receiveButtonPush();
				assertEquals(Buttons.BACK, actual);

				// Test Confirm
				outToServer.writeBytes("K C 4" + "\r" + "\n");
				actual = weightCommunicator.receiveButtonPush();
				assertEquals(Buttons.CONFIRM, actual);
			} catch (LogOutException e) {
				fail("Unexpected button.");
			}

			try {
				outToServer.writeBytes("K R 3" + "\r" + "\n");
				weightCommunicator.receiveButtonPush();
				fail("Expected a logout exception.");
			} catch (LogOutException e) {

			}

			try {
				outToServer.writeBytes("P111 A" + "\r" + "\n");
				weightCommunicator.receiveButtonPush();
				fail("Expected a Protocol exception.");
			} catch (ProtocolErrorException e) {

			} catch (LogOutException e) {
				fail("Expected a Protocol exception.");
			}

		} catch (ProtocolErrorException e) {
			fail("Did not expect a protocol exception here.");
		}

	}

	@Test
	public void testSendMessage() throws IOException {
		outToServer.writeBytes("P111 A" + "\r" + "\n");
		try {
			weightCommunicator.sendMessage("hej");
		} catch (InvalidReturnMessageException e) {
			fail("did not expect a InvalidReturnMessageException");
		}
		
		outToServer.writeBytes("K C 4" + "\r" + "\n");
		outToServer.writeBytes("P111 A" + "\r" + "\n");
		
		try {
			weightCommunicator.sendMessage("hej");
			fail("Expected a invalid return message");
		} catch (InvalidReturnMessageException e) {
			
		}
	}
	

	@Test
	public void testAskForInformation() throws IOException{
		outToServer.writeBytes("RM20 B" + "\r" + "\n");
		outToServer.writeBytes("RM20 A \"123\"" + "\r" + "\n");
	}

	@Test
	public void testRestartWeightDisplay() throws IOException {
		outToServer.writeBytes("RM20 A" + "\r" + "\n");
		outToServer.writeBytes("RM20 A" + "\r" + "\n");
		outToServer.writeBytes("RM20 A" + "\r" + "\n");
		outToServer.writeBytes("K C 4" + "\r" + "\n");
		outToServer.writeBytes("K A" + "\r" + "\n");
		
		weightCommunicator.restartWeightDisplay();
		
		outToServer.writeBytes("P111 A" + "\r" + "\n");
		try {
			weightCommunicator.sendMessage("hej");
		} catch (InvalidReturnMessageException e) {
			fail("did not expect a InvalidReturnMessageException");
		}
	}

	@Test
	public void testStopWeight() throws ProtocolErrorException {
		weightCommunicator.stopWeight();
		assertEquals("Q", waitForAnswer());
	}

	@Test
	public void testTaraWeight() throws IOException {
		outToServer.writeBytes("T S" + "   1.232 kg" + "\r" + "\n");
		try {
			weightCommunicator.taraWeight();
		} catch (ProtocolErrorException e) {
			fail("Expected a succesfull tara.");
		}
		
		outToServer.writeBytes("K A" + "   1.232 kg" + "\r" + "\n");
		try {
			weightCommunicator.taraWeight();
			fail("Expected a protocol exception.");
		} catch (ProtocolErrorException e) {
			
		}
	}

	
	@Test
	public void testGetWeight() throws IOException {
		outToServer.writeBytes("T S" + "   1.232 kg" + "\r" + "\n");
		double expected = 1.232;
		double actual;
		try {
			actual = weightCommunicator.getWeight();
			assertEquals("Message",expected,actual,0.001);
			
		} catch (ProtocolErrorException e) {
			fail("Did not expect a protocol exception.");
		}
		outToServer.writeBytes("K A" + "   1.232 kg" + "\r" + "\n");
		try {
			actual = weightCommunicator.getWeight();
			fail("Expected a protocolException");
		} catch (ProtocolErrorException e) {
			
		}

	}


	@Test
	public void testWaitForAnswer() throws IOException, ProtocolErrorException {
		outToServer.writeBytes("hej" + "\r" + "\n");
		assertEquals("hej", weightCommunicator.waitForAnswer());
	}

}
