package tests.staticclasses;

import static org.junit.Assert.*;

import java.math.BigInteger;

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
		System.out.println(Encryption.sha512("hello"));

	}

}
