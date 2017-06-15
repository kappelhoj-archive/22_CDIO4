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
import dataTransferObjects.RawMaterialDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

@Path("raw_material")
public class RawMaterialCRUD {

	/**
	 * Returns the raw material with the given raw material id as a JSON-object.
	 * @param id The given raw material id.
	 * @return The RawMaterialDTO as a JSON-object.
	 */
	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RawMaterialDTO readRawMaterial(String id) {
		try {
			return Initializer.getRawMaterialController().getRawMaterial(Validator.idToInteger(id));
		} catch (InputException e) {
			return null;
		} catch (DALException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Returns a list of all raw materials as a JSON-object.
	 * @return The list<RawMaterialDTO> as a JSON-object.
	 */
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RawMaterialDTO> readRawMaterialList() {
		try {
			return Initializer.getRawMaterialController().getRawMaterialList();
		} catch (DALException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Receives a JSON-object as a RawMaterialDTO and adds the RawMaterialDTo to the data layer.
	 * @param rawMaterial The raw material to be added to the data layer.
	 * @return A message which tells whether the creation succeeded or not.
	 */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRawMaterial(RawMaterialDTO rawMaterial) {
		try {
			Initializer.getRawMaterialController().createRawMaterial(rawMaterial);
			return "success: Råvaren blev oprettet";
		} catch (CollisionException e) {
			return "collision-error: Der eksisterer allerede en råvare med det indtastede id.";
		} catch (InputException e) {
			return "input-error: Det indtastede er ugyldigt..";
		} catch (DALException e) {
			System.out.println(e);
			return "system-error: Der skete en fejl i systemet.";
		}
	}

	/**
	 * Receives a JSON-object as a RawMaterialDTO and updates the RawMaterialDTO in the data layer.
	 * @param rawMaterial The raw material to be updated in the data layer.
	 * @return A message which tells whether the update succeeded or not.
	 */
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRawMaterial(RawMaterialDTO rawMaterial) {
		try {
			Initializer.getRawMaterialController().updateRawMaterial(rawMaterial);
			return "success: Råvaren blev opdateret.";
		} catch (InputException e) {
			return "input-error: Det indtastede er ugyldigt.";
		} catch (DALException e) {
			System.out.println(e);
			return "system-error: Der skete en fejl i systemet.";
		}
	}

}