package ASE.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Views.ConnectionManager;

public class ConnectionManagerTest {

	ConnectionManager connectionManager;

	String fileLocation;

	@Before
	public void setUp() throws Exception {
		fileLocation = "ASE/ASE/tests/WeightTableConManagerTest.txt";
		connectionManager = new ConnectionManager(fileLocation);
		connectionManager.threadStarter();;
	}

	@After
	public void tearDown() throws Exception {
		fileLocation = null;
		connectionManager = null;
	}

	/**
	 * Test to see if the information gathered from the ConnectionReader is read
	 * correctly into the ConnectionManager.
	 */
	@Test
	public void testCorrectInformationRetrievel() {

		ArrayList<String> resultsIP = connectionManager.getAllConnectedIPAddresses();
		ArrayList<Integer> resultsPorts = connectionManager.getAllConnectedPortNumbers();

		String[] expectedIP = { "" };
		ArrayList<String> actualIP = resultsIP;

		for (int i = 0; i < connectionManager.getNumberOfConnectedIPs(); i++) {
			assertEquals(expectedIP[i], actualIP.get(i));
		}

		String[] expectedPort = { "" };
		ArrayList<Integer> actualPort = resultsPorts;

		for (int i = 0; i < connectionManager.getNumberOfConnectedIPs(); i++) {
			assertEquals(expectedPort[i], actualPort.get(i));
		}
	}

	/**
	 * Test to see if the amount of connections made is correct.
	 */
	@Test
	public void testCorrectNumberOfWeightsConnected() {

		int expectedConnections = 0;

		int actualConnections = connectionManager.getNumberOfConnectedIPs();

		assertEquals(expectedConnections, actualConnections);
	}

}
