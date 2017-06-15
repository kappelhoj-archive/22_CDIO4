package tests.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Initializer;
import dataTransferObjects.UserDTO;
import exceptions.DALException;

/*
 * All tests are negative tests
 */
public class testUserController {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateFail() {
		try {
			Initializer.getUserController().createUser(new UserDTO(1, "Peter Issam EL-HABR", "PE", "1111111118", "11111111111", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().createUser(new UserDTO(1, "Peter Issam EL-HABR", "PE", "1111111118", "11", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().createUser(new UserDTO(1, "Peter Issam EL-HABR", "PE", "1111111118", "Peterpeter1", "Epic Saxo Guy"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().createUser(new UserDTO(1, "Peter Issam EL-HABR", "PE", "1", "Peterpeter1", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().createUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "Peterpeter1", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().createUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "Peterpeter1", null));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().createUser(new UserDTO(165202, "Peter Issam EL-HABR", "", "1111111118", "Peterpeter1", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testUpdateFail(){
		try {
			Initializer.getUserController().updateUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "11111111111", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().updateUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "11", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().updateUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "Peterpeter1", "Epic Saxo Guy"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().updateUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1", "Peterpeter1", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().updateUser(new UserDTO(165202, "Peter Issam EL-HABR", "", "1111111118", "Peterpeter1", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
		
		try {
			Initializer.getUserController().updateUser(new UserDTO(1, "Peter Issam EL-HABR", "PE", "1111111118", "Peterpeter1", "Admin"));
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
	}

}
