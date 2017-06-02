package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.LoginController;
import controller.interfaces.ILoginController;
import dataAccessObjects.MySQLOperatoerDAO;
import dataAccessObjects.interfaces.OperatoerDAO;
import dataTransferObjects.OperatoerDTO;
import exceptions.DALException;

public class testUnitLogin {

	ILoginController controller;
	OperatoerDAO dao;

	@Before
	public void setUp() throws Exception {
		controller = new LoginController();
		dao = new MySQLOperatoerDAO();
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
		dao = null;
	}

	@Test
	public void testCheckLoginKey() {
		boolean expected;
		boolean actual;

		int newKey = controller.generateAdminKey(1111);

		expected = true;
		actual = controller.checkLogin(1111, new Integer(newKey).toString());

		assertEquals(expected, actual);

		expected = false;
		actual = controller.checkLogin(1111, new Integer(newKey).toString());

		assertEquals(expected, actual);
	}

	@Test
	public void testCheckLoginPassword() {
		boolean expected;
		boolean actual;
		try{
			dao.createOperatoer(new OperatoerDTO(1, "Peter", "PE", "cpr", "testpassword"));
		}catch (DALException e){
			fail(e.getMessage());
		}

			expected = true;
			actual = controller.checkLogin(1111, "testpassword");

			assertEquals(expected, actual);

			expected = false;
			actual = controller.checkLogin(1111, "testpassword22222222");

			assertEquals(expected, actual);
		}

	}
