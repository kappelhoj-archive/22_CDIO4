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
import dataTransferObjects.RawMaterialBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

@Path("raw_material_batch")
public class RawMaterialBatchCRUD {

	/**
	 * Returns the raw material batch with the given raw material batch id as a JSON-object.
	 * @param id the given id of the raw material batch.
	 * @return The RawMaterialBatchDTO as a JSON-object.
	 */
	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RawMaterialBatchDTO readRawMaterialBatch(String id) {
		try {
			return Initializer.getRawMaterialBatchController().getRawMaterialBatch(Validator.idToInteger(id));
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

	/**
	 * Returns a list of all the raw material batches as a JSON.
	 * @return The List<RawMaterialBatchDTO> as a JSON-object.
	 */
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RawMaterialBatchDTO> readRawMaterialBatchList() {
		try {
			return Initializer.getRawMaterialBatchController().getRawMaterialBatchList();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	//Not in use.
	/**
	 * Returns a list of all raw material batches which contain a raw material with the given id
	 * @param rawMaterialId The given raw material id.
	 * @return The List<RawMaterialBatchDTO> as a JSON-object.
	 */
	@Path("read_list_specific")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<RawMaterialBatchDTO> readRawMaterialBatchList(String rawMaterialId) {
		try {
			return Initializer.getRawMaterialBatchController().getRawMaterialBatchList(Validator.idToInteger(rawMaterialId));
		} catch (InputException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println();
			return null;
		}
	}

	/**
	 * Receives a JSON-object as a RecipeCompDTO and adds the RecipeCompDTO to the data layer.
	 * @param rawMaterialBatch the raw material batch to be added to the data layer.
	 * @return A message which tells whether the creation succeeded or not.
	 */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) {
		try {
			Initializer.getRawMaterialBatchController().createRawMaterialBatch(rawMaterialBatch);
			return "success: Råvare batchen blev oprettet.";
		} catch (CollisionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "collision-error: Denne råvare batch eksisterer allerede i systemet.";
		} catch (InputException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "input-error: Det indtastede er ugyldigt.";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}

	/**
	 * Receives a JSON-object as a RawMaterialBatchDTo and updates the RawMaterialBatchDTO in the data layer.
	 * @param rawMaterialBatch the raw material batch to be updated in the data layer.
	 * @return A message which tells whether the update succeeded or not.
	 */
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) {
		try {
			Initializer.getRawMaterialBatchController().updateRawMaterialBatch(rawMaterialBatch);
			return "success: Råvare batchen blev opdateret.";
		} catch (InputException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "input-error: Det indtastede er ugyldigt.";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}

}
