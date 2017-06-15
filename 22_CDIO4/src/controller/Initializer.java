package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;import ASE.Controllers.MeasurementController;import ASE.Views.ConnectionManager;import exceptions.DALException;
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
	static IProductBatchCompController pbc = new ProductBatchCompController(productBatchCompDAO, productBatchDAO, rawMaterialBatchDAO);
	static IProductBatchController pb = new ProductBatchController(productBatchDAO, recipeDAO);
	static IRawMaterialBatchController rmb = new RawMaterialBatchController(rawMaterialBatchDAO, rawMaterialDAO);
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

	/**
	 * Method which is used at the start of the server
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		System.out.println("Initialicering færdig");

		if (!initialized) {

			try {
				System.out.println("Lave standard brugere...");

				u.createUser(new UserDTO(165202, "Peter Issam EL-HABR", "PE", "0101800032", "Peterpeter1", "Admin"));
			} catch (DALException e) {
				System.out.println("ERROR");
				System.out.println(e);
				++numberOfErrors;
			}

			try{
				u.createUser(new UserDTO(143233, "Simon Engquist", "SE", "0101405109", "Simonsimon1", "Farmaceut"));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
				u.createUser(new UserDTO(144265, "Arvid Langso", "AL", "0101600203", "Arvidarvid1", "Laborant"));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
				u.createUser(new UserDTO(165238, "Mikkel Lund", "ML", "0101600203", "Mikkelmikkel1", "Farmaceut"));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
				u.createUser(new UserDTO(93905, "Jeppe Nielsen", "JN", "0101200159", "Jeppejeppe1", "Værkfører"));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
				u.createUser(new UserDTO(16524, "Mads Stege", "MS", "0101002918", "Madsmads1", "Værkfører"));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}

			System.out.println("Done.");

			try{
				System.out.println(u.getUserList());

			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}

			System.out.println("laver forskellige DTO'er...");

			try{
				r.createRecipe(new RecipeDTO(1, "Salt vand"));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}


			try{
				rm.createRawMaterial(new RawMaterialDTO(1, "Vand", "VandCo"));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
				rm.createRawMaterial(new RawMaterialDTO(2, "Salt", "SaltCo"));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}

			try{
				rmb.createRawMaterialBatch(new RawMaterialBatchDTO(1, 1, 8.6));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			try{
				rmb.createRawMaterialBatch(new RawMaterialBatchDTO(2, 2, 10.7));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			
			try{
				pb.createProductBatch(new ProductBatchDTO(1, 0, 1));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			
			try{
				pb.createProductBatch(new ProductBatchDTO(3, 0, 1));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}

			try{

				pbc.createProductBatchComp(new ProductBatchCompDTO(1, 1, 5.1, 3.2, 165202));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}

			try{
				rc.createRecipeComp(new RecipeCompDTO(1, 1, 0.15, 10));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}

			try{
				rc.createRecipeComp(new RecipeCompDTO(1, 2, 0.15, 10));
			} catch (DALException e) {
				System.out.println("FEJL!");
				System.out.println(e);
				++numberOfErrors;
			}
			
			
			System.out.println("Server startet med: "+numberOfErrors+" FEJL!!");			System.out.println("Starting weight controllers.");
			System.out.println("Listener destroyed.\n");
			MeasurementController measureCon =new MeasurementController(Initializer.getProductBatchCompDAO(), Initializer.getProductBatchDAO());
			ConnectionManager conMan=new ConnectionManager(null,measureCon);
			new Thread(conMan).start();
			new Thread(measureCon).start();

		}
	}

	/**
	 * Method which is used at the stop of the server
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}
