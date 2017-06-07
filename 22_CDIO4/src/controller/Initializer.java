package controller;

import javax.servlet.ServletContextEvent;

import exceptions.DALException;
import controller.interfaces.*;
import dataTransferObjects.UserDTO;
import controller.*;

public class Initializer {

	private static boolean initialized = false;
	ILoginController login = new LoginController();
	IProductBatchCompController pbc = new ProductBatchCompController();
	IProductBatchController pb = new ProductBatchController();
	IRawMaterialBatchController rmb = new RawMaterialBatchController();
	IRawMaterialController rm = new RawMaterialController();
	IRecipeCompController rc = new RecipeCompController();
	IRecipeController r = new RecipeController();
	IUserController u = new UserController();

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
