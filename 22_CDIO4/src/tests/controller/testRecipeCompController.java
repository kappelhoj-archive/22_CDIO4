package tests.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Initializer;
import dataTransferObjects.RecipeCompDTO;
import exceptions.DALException;

/*
 * All tests are negative tests
 */
public class testRecipeCompController {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testCreateFail() {
		try {
			Initializer.getRecipeCompController().createRecipeComp(new RecipeCompDTO(1, 5, 3.1, 2.1));
			
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testUpdateFail(){
		try {
			Initializer.getRecipeCompController().updateRecipeComp(new RecipeCompDTO(1, 5, 3.1, 2.1));
			
			fail("");
		} catch (DALException e) {
			System.out.println(e);
		}
	}
}
