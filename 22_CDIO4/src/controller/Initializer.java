package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import exceptions.DALException;
import controller.interfaces.*;
import dataAccessObjects.*;
import dataTransferObjects.ProductBatchCompDTO;
import dataTransferObjects.ProductBatchDTO;
import dataTransferObjects.RawMaterialBatchDTO;
import dataTransferObjects.RawMaterialDTO;
import dataTransferObjects.RecipeCompDTO;
import dataTransferObjects.RecipeDTO;
import dataTransferObjects.UserDTO;

@WebListener
public class Initializer implements ServletContextListener {

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

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		System.out.println("Listener initialized");

		if (!initialized) {

			try {
				u.createUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "peterpeter", "Admin"));
				u.createUser(new UserDTO(143233, "Simon Engquist", "SE", "1111111118", "simonsimon", "Admin"));
				u.createUser(new UserDTO(144265, "Arvid Langso", "AL", "1111111118", "arvidarvid", "Admin"));
				u.createUser(new UserDTO(165238, "Mikkel Lund", "ML", "1111111118", "mikkelmikkel", "Admin"));
				u.createUser(new UserDTO(93905, "Jeppe Nielsen", "ML", "1111111118", "jeppejeppe", "Admin"));
				u.createUser(new UserDTO(16524, "Mads Stege", "MS", "1111111118", "madsmads", "Admin"));
				
				pbc.createProductBatchComp(new ProductBatchCompDTO(1, 1, 5.1, 3.2, 165202));
				
				pb.createProductBatch(new ProductBatchDTO(1, 0, 1));
				
				rmb.createRawMaterialBatch(new RawMaterialBatchDTO(1, 1, 8.6));
				rmb.createRawMaterialBatch(new RawMaterialBatchDTO(2, 2, 10.7));
				
				rm.createRawMaterial(new RawMaterialDTO(1, "Water", "Water-Corp"));
				rm.createRawMaterial(new RawMaterialDTO(2, "Salt", "Salt-Corp"));
				
				rc.createRecipeComp(new RecipeCompDTO(1, 1, 1.1, 0.6));
				rc.createRecipeComp(new RecipeCompDTO(1, 2, 3.1, 2.1));
				
				r.createRecipe(new RecipeDTO(1, "Salty water"));
				

			} catch (DALException e) {
				System.out.println(e.getMessage());
			}

		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Listener destroyed.\n");
	}
}
