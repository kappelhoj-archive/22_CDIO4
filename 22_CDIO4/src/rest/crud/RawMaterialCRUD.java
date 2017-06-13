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

@Path("raw_material")
public class RawMaterialCRUD {

	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RawMaterialDTO readRawMaterial(String id) {
		try {
			return Initializer.getRawMaterialController().getRawMaterial(Integer.parseInt(id));
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

	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RawMaterialDTO> readRawMaterialList() {
		try {
			return Initializer.getRawMaterialController().getRawMaterialList();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRawMaterial(RawMaterialDTO rawMaterial) {
		try {
			Initializer.getRawMaterialController().createRawMaterial(rawMaterial);
			return "success: Råvaren blev oprettet";
		} catch (CollisionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "collision-error: Der eksisterer allerede en råvare med det indtastede id.";
		} catch (InputException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "input-error: Det indtastede er ugyldigt..";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}

	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRawMaterial(RawMaterialDTO rawMaterial) {
		try {
			Initializer.getRawMaterialController().updateRawMaterial(rawMaterial);
			return "success: Råvaren blev opdateret";
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