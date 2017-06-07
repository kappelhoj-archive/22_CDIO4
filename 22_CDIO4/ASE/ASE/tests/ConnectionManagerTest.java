package ASE.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Views.ConnectionManager;
import ASE.Views.ConnectionReader;

public class ConnectionManagerTest {

	ConnectionReader connectionReader;
	ConnectionManager connectionManager;
	String fileLocation;

	@Before
	public void setUp() throws Exception {

		connectionManager = new ConnectionManager();
		fileLocation = "ASE/ASE/test/WeightTableConManagerTest.txt";
	}

	@After
	public void tearDown() throws Exception {
		fileLocation = null;
		connectionReader = null;
		connectionManager = null;
	}

	/**
	 * Test to see if the information gathered from the ConnectionReader is read
	 * correctly into the ConnectionManager.
	 */
	@Test
	public void testCorrectInformationRetrievel() {
		connectionManager.getConnections();

		ArrayList<String> resultsIP = connectionManager.getAllConnectedIPAdresses();
		ArrayList<Integer> resultsPorts = connectionManager.getAllConnectedPortNumbers();

		String[] expectedIP = { "43.210.240.45", "43.210.240.46", "43.210.240.47", "43.210.240.48", "43.210.240.49" };
		ArrayList<String> actualIP = resultsIP;

		for (int i = 0; i < connectionManager.getAllConnectedIPAdresses().size(); i++) {
			assertEquals(expectedIP[i], actualIP.get(i));
		}

		String[] expectedPort = { "23421", "23422", "23423", "23424", "23425" };
		ArrayList<Integer> actualPort = resultsPorts;

		for (int i = 0; i < connectionManager.getAllConnectedIPAdresses().size(); i++) {
			assertEquals(expectedPort[i], actualPort.get(i));
		}
	}

	@Test
	public void testCorrectNumberOfWeightsConnected() {
		connectionManager.getConnections();
		
		int expectedConnections = 5;

		int actualConnections = connectionManager.getNumberOfConnectedIPs();

		assertEquals(expectedConnections, actualConnections);
	}

}
