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
import exceptions.DALException;

@Path("recipe_component")
public class RecipeCompCRUD {

	@Path("read")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RecipeCompDTO readRecipeComp(RecipeCompPOJO i) {
		try {
			return Initializer.getRecipeCompController().getRecipeComp(Integer.parseInt(i.getRecipeId()), Integer.parseInt(i.getRawMaterialId()));
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Path("read_list_specific")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<RecipeCompDTO> readRecipeCompList(String recipeId) {
		try {
			return Initializer.getRecipeCompController().getRecipeCompList(Integer.parseInt(recipeId));
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println();
			return null;
		}
	}
	
	//Not in use, but is needed if a selection on the website is needed.
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

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRecipeComp(RecipeCompDTO recipeComp) {
		try {
			Initializer.getRecipeCompController().createRecipeComp(recipeComp);
			return "success: recept komponenten blev oprettet og tilføjet til recepten.";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}

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
