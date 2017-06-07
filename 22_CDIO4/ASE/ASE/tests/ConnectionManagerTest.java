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
		fileLocation = "ASE/ASE/test/WeightTableConManagerTest.txt";
		connectionManager = new ConnectionManager(fileLocation);
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
		connectionManager.getConnections();

		System.out.println("Number of connected IPs: " + connectionManager.getNumberOfConnectedIPs());
		System.out.println("Number of connected Ports: " + connectionManager.getAllConnectedPortNumbers().size());

		ArrayList<String> resultsIP = connectionManager.getAllConnectedIPAddresses();
		ArrayList<Integer> resultsPorts = connectionManager.getAllConnectedPortNumbers();

		String[] expectedIP = { "43.210.240.45", "43.210.240.46", "43.210.240.47", "43.210.240.48", "43.210.240.49" };
		ArrayList<String> actualIP = resultsIP;

		for (int i = 0; i < connectionManager.getAllConnectedIPAddresses().size(); i++) {
			assertEquals(expectedIP[i], actualIP.get(i));
		}

		String[] expectedPort = { "23421", "23422", "23423", "23424", "23425" };
		ArrayList<Integer> actualPort = resultsPorts;

		for (int i = 0; i < connectionManager.getAllConnectedIPAddresses().size(); i++) {
			assertEquals(expectedPort[i], actualPort.get(i));
		}
	}

	/**
	 * Test to see if the amount of connections made is correct.
	 */
	@Test
	public void testCorrectNumberOfWeightsConnected() {

		connectionManager.getConnections();

		int expectedConnections = 5;

		int actualConnections = connectionManager.getNumberOfConnectedIPs();

		assertEquals(expectedConnections, actualConnections);
	}

}
