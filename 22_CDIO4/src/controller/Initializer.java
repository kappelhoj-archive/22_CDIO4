package controller;

import javax.servlet.ServletContextEvent;

import exceptions.DALException;
import controller.interfaces.*;
import dataAccessObjects.*;
import dataTransferObjects.UserDTO;

public class Initializer {

	private static boolean initialized = false;
	
	
	ILoginController login = new LoginController(getUserDAO());
	IProductBatchCompController pbc = new ProductBatchCompController(getProductBatchCompDAO());
	IProductBatchController pb = new ProductBatchController(getProductBatchDAO());
	IRawMaterialBatchController rmb = new RawMaterialBatchController( getRawMaterialBatchDAO());
	IRawMaterialController rm = new RawMaterialController(getRawMaterialDAO());
	IRecipeCompController rc = new RecipeCompController(getRecipeCompDAO());
	IRecipeController r = new RecipeController(getRecipeDAO());
	IUserController u = new UserController(getUserDAO());

	public ProductBatchCompDAO getProductBatchCompDAO(){return new ProductBatchCompDAO();}
	public ProductBatchDAO getProductBatchDAO(){return new ProductBatchDAO();}
	public RawMaterialBatchDAO getRawMaterialBatchDAO(){return new RawMaterialBatchDAO();}
	public RawMaterialDAO getRawMaterialDAO(){return new RawMaterialDAO();}
	public RecipeCompDAO getRecipeCompDAO(){return new RecipeCompDAO();}
	public RecipeDAO getRecipeDAO(){return new RecipeDAO();}
	public UserDAO getUserDAO(){return new UserDAO();}

	public ILoginController getLoginController() {return login;}
	public IProductBatchCompController getProductBatchCompController() {return pbc;}
	public IProductBatchController getProductBatchController() {return pb;}
	public IRawMaterialBatchController getRawMaterialBatchController() {return rmb;}
	public IRawMaterialController getRawMaterialController() {return rm;}
	public IRecipeCompController getRecipeCompController() {return rc;}
	public IRecipeController getRecipeController() {return r;}
	public IUserController getUserController() {return u;}

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
