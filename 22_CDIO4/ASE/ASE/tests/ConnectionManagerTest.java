package ASE.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Views.ConnectionManager;
import ASE.Views.ConnectionReader;

public class ConnectionManagerTest {

	ConnectionManager connectionManager;
	ConnectionReader connectionReader;
	String fileLocation;

	@Before
	public void setUp() throws Exception {
		fileLocation = "ASE/ASE/tests/WeightTableConManagerTest.txt";
		connectionManager = new ConnectionManager(fileLocation, null);
		connectionManager.threadStarter();
		;
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
		try {
			connectionReader.getWeightIPs();
		} catch (FileNotFoundException e) {
			System.out.println("Connecting to IP failed.");
		}
		ArrayList<String> resultsIP = connectionReader.getAllIPAddresses();
		ArrayList<String> resultsPorts = connectionReader.getAllPortNumbers();

		String[] expectedIP = { "43.210.240.45", "43.210.240.46" };
		ArrayList<String> actualIP = resultsIP;

		for (int i = 0; i < connectionManager.getNumberOfConnectedIPs(); i++) {
			assertEquals(expectedIP[i], actualIP.get(i));
		}

		String[] expectedPort = { "23421", "23422" };
		ArrayList<String> actualPort = resultsPorts;

		for (int i = 0; i < connectionManager.getNumberOfConnectedIPs(); i++) {
			assertEquals(expectedPort[i], actualPort.get(i));
		}
	}
}
