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
	String[] IPArray;

	@Before
	public void setUp() throws Exception {
		fileLocation = "ASE/ASE/tests/WeightTableTest.txt";
		connectionReader = new ConnectionReader(fileLocation);
	}

	@After
	public void tearDown() throws Exception {
		fileLocation = null;
	}

	@Test
	public void testIPChecker() {
		try {
			connectionReader.WeightReader();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		String[] expected = { "12.245.231.201", "91.23.110.232"};
		
		ArrayList<String> actual;
		actual = connectionReader.getAllIPAdresses();
		
		System.out.println(actual);
		
		for (int i = 0; i < expected.length; i++){
			assertEquals(expected[i], actual.get(i));

		}
	}
	
	@Test
	public void testPortChecker(){
		
	}

}
