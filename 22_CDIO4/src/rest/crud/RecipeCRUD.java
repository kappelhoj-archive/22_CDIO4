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

@Path("recipe")

public class RecipeCRUD {

	/**
	 * Returns the recipe with the given recipe id as a JSON-object.
	 * @param recipeId The given id of the recipe.
	 * @return The RecipeDTO as a JSON-object.
	 */
	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RecipeDTO getRecipe(String recipeId) {
		try {
			return Initializer.getRecipeController().getRecipe(Integer.parseInt(recipeId));
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Returns a list of all recipes as a JSON-object.
	 * @return The List<UserDTO> as a JSON-object.
	 */
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RecipeDTO> getRecipeList() {
		try {
			return Initializer.getRecipeController().getRecipeList();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Receives a JSON-object as a RecipeDTO and adds the RecipeDTO to the data layer.
	 * @param recipe The recipe to be added to the data layer.
	 * @return A message which tells whether the creation succeeded or not.
	 */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRecipe(RecipeDTO recipe) {
		try {
			Initializer.getRecipeController().createRecipe(recipe);
			return "success: Recepten blev oprettet";
		} catch (CollisionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "collision-error: Der eksisterer allerede en recept med det indtastede id";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}

	//Not in use. Is needed if we want to be able to change the name of a recipe.
	/**
	 * Receives a JSON-object as a RecipeDTO and updates the RecipeDTO in the data layer.
	 * @param recipe The recipe to be updated in the data layer.
	 * @return A message which tells whether the update succeeded or not.
	 */
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updataRecipeBatch(RecipeDTO recipe) {
		try {
			Initializer.getRecipeController().updateRecipe(recipe);
			return "success: Recepten blev opdateret.";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}

}
