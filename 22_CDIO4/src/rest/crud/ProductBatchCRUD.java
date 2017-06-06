package rest.crud;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.ProductBatchController;
import controller.interfaces.IProductBatchController;
import dataTransferObjects.ProduktBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("product_batch")
public class ProductBatchCRUD {
	IProductBatchController controller = new ProductBatchController();

	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ProduktBatchDTO getProductBatch(int id) {
		try {
			return controller.getProductBatch(id);
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
	public List<ProduktBatchDTO> getProductBatchList() {
		try {
			return controller.getProductBatchList();
		} catch (DALException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createProductBatch(ProduktBatchDTO productbatch) {
		try {
			controller.createProductBatch(productbatch);
			return "success";
		} catch (CollisionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der findes allerede et produkt batch med det indtastede id.";
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
	public String updateProductBatch(ProduktBatchDTO productbatch) {
		try {
			controller.updateProductBatch(productbatch);
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
