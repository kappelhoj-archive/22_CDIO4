package ASE.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

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

		fileLocation = "ASE/ASE/test/WeightTableConManagerTest.txt";

		connectionManager.run();

		ArrayList<String> resultsIP = connectionManager.getAllConnectedIPAdresses();
		ArrayList<Integer> resultsPorts = connectionManager.getAllConnectedPortNumbers();

		String[] expectedIP = { "123.64.223.12", "43.210.240.46" };
		ArrayList<String> actualIP = resultsIP;

		for (int i = 0; i < connectionManager.getAllConnectedIPAdresses().size(); i++) {
			assertEquals(expectedIP[i], actualIP.get(i));
		}

		String[] expectedPort = { "32132", "23422" };
		ArrayList<Integer> actualPort = resultsPorts;

		for (int i = 0; i < connectionManager.getAllConnectedIPAdresses().size(); i++) {
			assertEquals(expectedPort[i], actualPort.get(i));
		}
	}

}
