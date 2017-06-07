package tests.DAO;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccessObjects.UserDAO;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.UserDTO;
import exceptions.DALException;

public class testUserDAO {

	IUserDAO dao;

	@Before
	public void setUp() throws Exception {
		dao = new UserDAO();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	//Positiv test
	@Test
	public void testCreateUser(){

	}



	//Negative Test
	@Test
	public void testCreateUserFail() {
		boolean expected;
		boolean actual = false;
		try{
			dao.createOperatoer(new UserDTO(12, "Peter", "PE", "cpr", "testpassword","Admin"));
			dao.createOperatoer(new UserDTO(12, "Peter2", "PE2", "cpr2", "testpassword2","Admin"));

			System.out.println(dao.getUserList().toString());
		}catch (DALException e){
			actual = true;
			System.out.println(e);
		}

		try {
			System.out.println(dao.getUserList().toString());
		} catch (DALException e) {
			e.printStackTrace();
		}

		expected = true;

		assertEquals(expected, actual);

	}

	//Negative Test
	//It fails because Update doesn't throw DALException, this is an impossible scenario.
	// The Admin can not update an user who doesn't exist in the User Test.
	@Test
	public void testUpdateUserFail() {
		boolean expected;
		boolean actual = false;
		try{
			dao.updateOperatoer(new UserDTO(14, "Peter", "PE", "cpr", "testpassword","Admin"));

			System.out.println(dao.getUserList().toString());
		}catch (DALException e){
			actual = true;
			System.out.println("Update fail : "+e);
		}

		try {
			System.out.println(dao.getUserList().toString());
		} catch (DALException e) {
			e.printStackTrace();
		}

		expected = true;

		assertEquals(expected, actual);

	}

	//Negative Test
	@Test
	public void testGetUserFail() {
		boolean expected;
		boolean actual = false;
		try{
			dao.getUser(13);

			System.out.println(dao.getUser(13).toString());
		}catch (DALException e){
			actual = true;
			System.out.println(e);
		}

		try {
			System.out.println(dao.getUserList().toString());
		} catch (DALException e) {
			e.printStackTrace();
		}

		expected = true;

		assertEquals(expected, actual);

	}

	//Positiv test
	@Test
	public void testGetAndUpdateUserList(){

		try {
			dao.createOperatoer(new UserDTO(44, "Peter3", "PE3", "cpr3", "testpassword3","Admin"));
			dao.updateOperatoer(new UserDTO(12, "PeterChanged", "PE", "cpr", "testpassword","Admin"));

			System.out.println(dao.getUserList());

		} catch (DALException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
	}

}
