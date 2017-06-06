package rest.crud;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import controller.RecipeController;

import controller.interfaces.IRecipeController;

import dataTransferObjects.ReceptDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("recipe")

public class RecipeCRUD {
	
	IRecipeController controller = new RecipeController();

	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ReceptDTO getRecipe(int recipeId)
	{
		
		try
		{
			return controller.getRecipe(recipeId);
		}
		catch(InputException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
		catch(DALException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	//TODO: Kig på HTTP ERROR
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReceptDTO> getRecipeList()

	{
		try{
			return controller.getRecipeList();
		}
		catch(DALException e){
			System.out.println(e.getMessage());
			return null;
		}	
	}
	
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRecipe(ReceptDTO recipe)
	{
		
		try
		{
			controller.createRecipe(recipe);
			return "success";
		}
		catch(CollisionException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der findes allerede en råvare med det indtastede id.";
		}
		catch(DALException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet."; 
	
		}
	}
	@Path("update")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String updataRecipeBatch(ReceptDTO recipe)
	{
		
		try
		{
			controller.updateRecipe(recipe);
			return "success";
		}
		catch(InputException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Det indtastede er ugyldigt..";		
			
		}
		catch(DALException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet."; 
	
		}
	}
	
	
}
