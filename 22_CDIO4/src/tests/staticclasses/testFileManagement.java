package tests.staticclasses;

import java.util.ArrayList;

import staticClasses.FileManagement;
import staticClasses.FileManagement.TypeOfData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.Initializer;
import dataTransferObjects.DTO;
import dataTransferObjects.RecipeDTO;
import dataTransferObjects.UserDTO;
import exceptions.DALException;

public class testFileManagement {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testWriteAndReadArrayList() {
		Object testvalues = new ArrayList<DTO>();

		((ArrayList<DTO>) testvalues).add(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "Peterpeter1", "Admin"));

		FileManagement.writeData(testvalues, TypeOfData.USER);

		System.out.println(FileManagement.retrieveData(TypeOfData.USER));


	}

	@Test
	public void testWriteAndReadHashTable() {
		try {

			Initializer.getRecipeController().createRecipe(new RecipeDTO(8, "Salty water"));

			FileManagement.writeData(Initializer.getRecipeController().getRecipeList(), TypeOfData.RECIPE);

			System.out.println(FileManagement.retrieveData(TypeOfData.RECIPE));
		} catch (DALException e) {
			e.printStackTrace();
		}


	}

}
