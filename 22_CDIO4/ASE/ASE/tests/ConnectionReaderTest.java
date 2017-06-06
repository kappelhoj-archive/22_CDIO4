package ASE.tests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Views.ConnectionReader;

public class ConnectionReaderTest {

	ConnectionReader connectionReader;
	String fileLocation;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		fileLocation = null;
		connectionReader = null;
	}

	@Test
	public void testIPChecker() {
		fileLocation = "ASE/ASE/tests/WeightTableIPTest.txt";
		connectionReader = new ConnectionReader(fileLocation);

		try {
			connectionReader.WeightReader();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		String[] expected = { "12.245.231.201", "91.23.110.232" };

		ArrayList<String> actual;
		actual = connectionReader.getAllIPAdresses();

		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual.get(i));

		}

	}

	@Test
	public void testPortChecker() {
		fileLocation = "ASE/ASE/tests/WeightTablePortTest.txt";
		connectionReader = new ConnectionReader(fileLocation);

		try {
			connectionReader.WeightReader();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}

		String[] expected = { "12319", "12315" };

		ArrayList<String> actual;
		actual = connectionReader.getAllPortNumbers();

		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], actual.get(i));

		}
	}

}
