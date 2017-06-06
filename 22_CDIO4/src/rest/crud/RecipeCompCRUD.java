package rest.crud;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.RecipeCompController;
import controller.interfaces.IRecipeCompController;
import dataTransferObjects.ReceptKompDTO;
import exceptions.DALException;
import exceptions.InputException;

@Path("recipe_component")
public class RecipeCompCRUD {

	IRecipeCompController controller = new RecipeCompController();

	// TODO: Kig på HTTP ERROR
	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ReceptKompDTO readRecipeComp(int recipeId, int rawMaterialId) {
		try {
			return controller.getRecipeComp(recipeId, rawMaterialId);
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
	public List<ReceptKompDTO> readRecipeComp(int recipeId) {
		try {
			return controller.getRecipeComp(recipeId);
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
	public List<ReceptKompDTO> readRecipeCompList() {
		try {
			return controller.getRecipeCompList();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRecipeComp(ReceptKompDTO recipeComp) {
		try {
			controller.createRecipeComp(recipeComp);
			return "succes";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet.";
		}
	}

	@Path("update")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRecipeComp(ReceptKompDTO recipeComp) {
		try {
			controller.updateRecipeComp(recipeComp);
			return "success";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet.";
		}
	}

}
