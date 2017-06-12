package rest.crud;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.Initializer;
import dataTransferObjects.RecipeDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("recipe")

public class RecipeCRUD {
	

	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RecipeDTO getRecipe(String recipeId)
	{
		try
		{
			return Initializer.getRecipeController().getRecipe(Integer.parseInt(recipeId));
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
	public List<RecipeDTO> getRecipeList()

	{
		try{
			System.out.println(Initializer.getRecipeController().getRecipeList());
			return Initializer.getRecipeController().getRecipeList();
		}
		catch(DALException e){
			System.out.println(e.getMessage());
			return null;
		}	
	}
	
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRecipe(RecipeDTO recipe)
	{	
		try
		{
			Initializer.getRecipeController().createRecipe(recipe);
			return "success: Recepten med id " + recipe.getRecipeId() + " blev oprettet.";
		}
		catch(CollisionException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "collision-error: Der findes allerede en recept med det indtastede id.";
		}
		catch(DALException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet."; 
	
		}
	}
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updataRecipeBatch(RecipeDTO recipe)
	{
		
		try
		{
			Initializer.getRecipeController().updateRecipe(recipe);
			return "success";
		}
		catch(DALException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet."; 
	
		}
	}
	
	
}
