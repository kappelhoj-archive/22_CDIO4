package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.LoginController;
import controller.interfaces.ILoginController;
import dataAccessObjects.MyOperatoerDAO;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.OperatoerDTO;
import exceptions.DALException;

public class testUnitLogin {

	ILoginController controller;
	IUserDAO dao;

	@Before
	public void setUp() throws Exception {
		controller = new LoginController();
		dao = new MyOperatoerDAO();
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
		
		System.out.println("Admin key: "+new Integer(newKey).toString());

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
			dao.createOperatoer(new OperatoerDTO(1111, "Peter", "PE", "cpr", "testpassword", "Admin"));
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

	@Test
	public void testCreateUserFail() {
		boolean expected;
		boolean actual = false;
		try{
			dao.createOperatoer(new OperatoerDTO(1111, "Peter", "PE", "cpr", "testpassword","Admin"));
			dao.createOperatoer(new OperatoerDTO(1111, "Peter2", "PE2", "cpr2", "testpassword2","Admin"));
			
			System.out.println(dao.getOperatoerList().toString());
		}catch (DALException e){
			actual = true;
			System.out.println(e);
		}
		
		try {
			System.out.println(dao.getOperatoerList().toString());
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			expected = true;

			assertEquals(expected, actual);

		}

	
	}
