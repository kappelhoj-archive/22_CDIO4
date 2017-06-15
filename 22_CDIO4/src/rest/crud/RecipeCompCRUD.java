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
import dataTransferObjects.RecipeCompDTO;
import dataTransferObjects.RecipeCompPOJO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

@Path("recipe_component")
public class RecipeCompCRUD {

	/**
	 * Receives a JSON-object as a RecipeCompPOJO and returns the recipe component with the received information.
	 * @param i The given RecipeCompPojo containing the information required to return the correct recipe component.
	 * @return The RecipeCompDTO as a JSON-object.
	 */
	@Path("read")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RecipeCompDTO readRecipeComp(RecipeCompPOJO i) {
		try {
			return Initializer.getRecipeCompController().getRecipeComp(Validator.idToInteger(i.getRecipeId()), Validator.idToInteger(i.getRawMaterialId()));
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Returns a list of all the recipe components in the recipe with the given id.
	 * @param recipeId The given recipe id.
	 * @return The List<RecipeCompDTO> as a JSON-object.
	 */
	@Path("read_list_specific")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<RecipeCompDTO> readRecipeCompList(String recipeId) {
		try {
			return Initializer.getRecipeCompController().getRecipeCompList(Validator.idToInteger(recipeId));
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println();
			return null;
		}
	}
	
	//Not in use, but is needed if a selection on the website is needed.
	/**
	 * Returns a list of all recipe components as a JSON-object.
	 * @return The List<RecipeCompDTO> as a JSON-object.
	 */
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RecipeCompDTO> readRecipeCompList() {
		try {
			return Initializer.getRecipeCompController().getRecipeCompList();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Receives a JSON-object as a RecipeCompDTO and adds the RecipeCompDTO to the data layer.
	 * @param recipeComp the recipe component to be added to the data layer.
	 * @return A message which tells whether the creation succeeded or not.
	 */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRecipeComp(RecipeCompDTO recipeComp) {
		
		try {
			Initializer.getRecipeCompController().createRecipeComp(recipeComp);
		} catch (InputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			return "input-error: Det indtastede er ugyldigt.";
		} catch (CollisionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			return "collision-error: Der findes allerede en recept med denne recept komponent.";
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			return "system-error: Der skete en fejl i systemet.";
		}
		return "success: Recept komponenten blev oprettet og tilføjet til recepten.";
	}

	/**
	 * Receives a JSON-object as a RecipeCompDTO and updates the RecipeCompDTO in the data layer.
	 * @param recipeComp The recipe component to be updated in the data layer.
	 * @return A message which tells whether the update succeeded or not.
	 */
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRecipeComp(RecipeCompDTO recipeComp) {
		try {
			Initializer.getRecipeCompController().updateRecipeComp(recipeComp);
			return "success: Recept komponenten blev opdateret.";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}

}
