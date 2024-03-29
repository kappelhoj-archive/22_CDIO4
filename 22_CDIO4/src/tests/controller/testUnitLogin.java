package tests.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.LoginController;
import controller.interfaces.ILoginController;
import controller.interfaces.ILoginController.LoginState;
import dataAccessObjects.UserDAO;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.LoginPOJO;
import exceptions.DALException;
import exceptions.InputException;

public class testUnitLogin {

	ILoginController controller;
	IUserDAO dao;

	@Before
	public void setUp() throws Exception {
		dao = new UserDAO();
		controller = new LoginController(dao);
	}

	@After
	public void tearDown() throws Exception {
		controller = null;
		dao = null;
	}

	@Test
	public void testCheckLoginKey() {
		LoginState expected;
		LoginState actual;

		int newKey = 0;
		try {
			newKey = controller.resetPassword(11);
		} catch (InputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Admin key: " + new Integer(newKey).toString());

		expected = LoginState.NEW;
		actual = controller.checkLogin(new LoginPOJO("11", new Integer(newKey).toString()));

		System.out.println(actual);

		System.out.println(newKey);

		assertEquals(expected, actual);

		expected = LoginState.FALSE;
		actual = controller.checkLogin(new LoginPOJO("11", new Integer(newKey).toString()));

		assertEquals(expected, actual);
	}

	@Test
	public void testCheckLoginPassword() {
		LoginState expected;
		LoginState actual;

		expected = LoginState.TRUE;
		actual = controller.checkLogin(new LoginPOJO("165202", "Peterpeter1"));

		assertEquals(expected, actual);

		expected = LoginState.FALSE;
		actual = controller.checkLogin(new LoginPOJO("11", "testpassword2222222"));

		assertEquals(expected, actual);
	}

}
