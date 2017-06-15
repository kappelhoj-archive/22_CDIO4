package tests.staticclasses;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import staticClasses.Encryption;

public class testEncryption extends Encryption {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashing() {
		String expected = "9b71d224bd62f3785d96d46ad3ea3d73319bfbc2890caadae2dff72519673ca72323c3d99ba5c11d7c7acc6e14b8c5da0c4663475c2e5c3adef46f73bcdec043";
		String actual = Encryption.sha512("hello");
		
		
		System.out.println(Encryption.sha512("Peterpeter1"));
		assertEquals(expected, actual);
		
		expected = "128";
		actual = actual.length()+"";
		
		assertEquals(expected, actual);
		
		System.out.println(System.getProperty("user.name"));

	}

}
