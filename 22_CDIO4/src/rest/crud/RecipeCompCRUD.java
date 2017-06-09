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
import exceptions.InputException;

@Path("recipe_component")
public class RecipeCompCRUD {

	// TODO: Kig på HTTP ERROR
	@Path("read")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RecipeCompDTO readRecipeComp(RecipeCompPOJO i) {
		try {
			return Initializer.getRecipeCompController().getRecipeComp(Integer.parseInt(i.getRecipeId()), Integer.parseInt(i.getRawMaterialId()));
		} catch (InputException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	// TODO: Kig på HTTP ERROR
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

	// TODO: Kig på HTTP ERROR
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
			return "succes";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet.";
		}
	}

	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRecipeComp(RecipeCompDTO recipeComp) {
		try {
			Initializer.getRecipeCompController().updateRecipeComp(recipeComp);
			return "success";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet.";
		}
	}

}
