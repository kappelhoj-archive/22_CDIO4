package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.LoginController;

public class testUnitLogin {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		boolean expected;
		boolean actual;
		
		LoginController controller = new LoginController();
		
		int newKey = controller.generateAdminKey(1111);
		
		System.out.println(newKey);
		
		expected = true;
		actual = controller.checkLogin(1111, new Integer(newKey).toString());
		
		assertEquals(expected, actual);
		
		expected = false;
		actual = controller.checkLogin(1111, new Integer(newKey).toString());
		
		assertEquals(expected, actual);
	}

}
