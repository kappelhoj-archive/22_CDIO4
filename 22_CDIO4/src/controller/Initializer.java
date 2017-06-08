package controller;

import javax.servlet.ServletContextEvent;

import exceptions.DALException;
import controller.interfaces.*;
import dataAccessObjects.*;
import dataTransferObjects.UserDTO;

public class Initializer {

	private static boolean initialized = false;
	
	static ProductBatchCompDAO productBatchCompDAO = new ProductBatchCompDAO();
	static ProductBatchDAO productBatchDAO = new ProductBatchDAO();
	static RawMaterialBatchDAO rawMaterialBatchDAO = new RawMaterialBatchDAO();
	static RawMaterialDAO rawMaterialDAO = new RawMaterialDAO();
	static RecipeCompDAO recipeCompDAO = new RecipeCompDAO();
	static RecipeDAO recipeDAO = new RecipeDAO();
	static UserDAO userDAO = new UserDAO();
	
	public static ProductBatchCompDAO getProductBatchCompDAO() {return productBatchCompDAO;}
	public static ProductBatchDAO getProductBatchDAO() {return productBatchDAO;}
	public static RawMaterialBatchDAO getRawMaterialBatchDAO() {return rawMaterialBatchDAO;}
	public static RawMaterialDAO getRawMaterialDAO() {return rawMaterialDAO;}
	public static RecipeCompDAO getRecipeCompDAO() {return recipeCompDAO;}
	public static RecipeDAO getRecipeDAO() {return recipeDAO;}
	public static UserDAO getUserDAO() {return userDAO;}

	static ILoginController login = new LoginController(userDAO);
	static IProductBatchCompController pbc = new ProductBatchCompController(productBatchCompDAO);
	static IProductBatchController pb = new ProductBatchController(productBatchDAO);
	static IRawMaterialBatchController rmb = new RawMaterialBatchController(rawMaterialBatchDAO);
	static IRawMaterialController rm = new RawMaterialController(rawMaterialDAO);
	static IRecipeCompController rc = new RecipeCompController(recipeCompDAO);
	static IRecipeController r = new RecipeController(recipeDAO);
	static IUserController u = new UserController(userDAO);

	static public ILoginController getLoginController() {return login;}
	static public IProductBatchCompController getProductBatchCompController() {return pbc;}
	static public IProductBatchController getProductBatchController() {return pb;}
	static public IRawMaterialBatchController getRawMaterialBatchController() {return rmb;}
	static public IRawMaterialController getRawMaterialController() {return rm;}
	static public IRecipeCompController getRecipeCompController() {return rc;}
	static public IRecipeController getRecipeController() {return r;}
	static public IUserController getUserController() {return u;}

	public void contextInitialized(ServletContextEvent sce) {

		System.out.println("Listener initialized");

		if (!initialized) {

			try {
				u.createUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "peter", "Admin"));
				u.createUser(new UserDTO(143233, "Simon Engquist", "SE", "1111111118", "simon", "Admin"));
				u.createUser(new UserDTO(144265, "Arvid Langso", "AL", "1111111118", "arvid", "Admin"));
				u.createUser(new UserDTO(165238, "Mikkel Lund", "ML", "1111111118", "mikkel", "Admin"));
				u.createUser(new UserDTO(93905, "Jeppe Nielsen", "ML", "1111111118", "jeppe", "Admin"));
				u.createUser(new UserDTO(16524, "Mads Stege", "MS", "1111111118", "mads", "Admin"));

			} catch (DALException e) {
				System.out.println(e.getMessage());
			}

		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Listener destroyed.\n");
	}
}
