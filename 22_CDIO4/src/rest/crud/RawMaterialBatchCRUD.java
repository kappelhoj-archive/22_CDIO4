package rest.crud;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.RawMaterialBatchController;
import controller.interfaces.IRawMaterialBatchController;
import dataTransferObjects.RaavareBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("raw_material_batch")
public class RawMaterialBatchCRUD {

	IRawMaterialBatchController controller = new RawMaterialBatchController();

	// TODO: Kig på HTTP ERROR
	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public RaavareBatchDTO readRawMaterialBatch(int id) {
		try {
			return controller.getRawMaterialBatch(id);
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
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RaavareBatchDTO> readRawMaterialBatchList() {
		try {
			return controller.getRawMaterialBatchList();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	// TODO: KIG PÅ HTTP ERROR
	@Path("read_list_specific")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<RaavareBatchDTO> readRawMaterialBatchList(int raavareId) {
		try {
			return controller.getRawMaterialBatchList(raavareId);
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

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRawMaterialBatch(RaavareBatchDTO rawMaterialBatch) {

		try {
			controller.createRawMaterialBatch(rawMaterialBatch);
			return "success";
		} catch (CollisionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der findes allerede en råvare med det indtastede id.";
		} catch (InputException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Det indtastede er ugyldigt..";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet.";
		}
	}

	@Path("update")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateRawMaterialBatch(RaavareBatchDTO rawMaterialBatch) {
		try {
			controller.updateRawMaterialBatch(rawMaterialBatch);
			return "success";
		} catch (InputException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Det indtastede er ugyldigt..";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet.";
		}
	}

}
