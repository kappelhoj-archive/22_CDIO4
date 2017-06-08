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
	
	private static int numberOfErrors = 0;

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
	static IRecipeCompController rc = new RecipeCompController(recipeCompDAO, recipeDAO, rawMaterialDAO);
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
				System.out.println("Creating default users...");

				u.createUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "1111111118", "Peterpeter1", "Admin"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			
			try{
			u.createUser(new UserDTO(143233, "Simon Engquist", "SE", "1111111118", "Simonsimon1", "Farmaceut"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
			u.createUser(new UserDTO(144265, "Arvid Langso", "AL", "1111111118", "Arvidarvid1", "Laborant"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
			u.createUser(new UserDTO(165238, "Mikkel Lund", "ML", "1111111118", "Mikkelmikkel1", "Farmaceut"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
			u.createUser(new UserDTO(93905, "Jeppe Nielsen", "ML", "1111111118", "Jeppejeppe1", "Værkfører"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
			u.createUser(new UserDTO(16524, "Mads Stege", "MS", "1111111118", "Madsmads1", "Værkfører"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}

			System.out.println("Done.");
			
			try{
			System.out.println(u.getUserList());
			
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}

			System.out.println("Creating misc DTO...");
			
			try{

			pbc.createProductBatchComp(new ProductBatchCompDTO(1, 1, 5.1, 3.2, 165202));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			
			try{
			pb.createProductBatch(new ProductBatchDTO(1, 0, 1));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}

			try{
			r.createRecipe(new RecipeDTO(1, "Salty water"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}

			try{
			rmb.createRawMaterialBatch(new RawMaterialBatchDTO(1, 1, 8.6));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
			rmb.createRawMaterialBatch(new RawMaterialBatchDTO(2, 2, 10.7));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}

			try{
			rm.createRawMaterial(new RawMaterialDTO(1, "Water", "Water-Corp"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
			rm.createRawMaterial(new RawMaterialDTO(2, "Salt", "Salt-Corp"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}

			try{
			rc.createRecipeComp(new RecipeCompDTO(1, 1, 1.1, 0.6));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}
			
			try{
			rc.createRecipeComp(new RecipeCompDTO(1, 2, 3.1, 2.1));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}


			System.out.println("All done with "+numberOfErrors+" error(s) !");


		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Listener destroyed.\n");
	}
}
