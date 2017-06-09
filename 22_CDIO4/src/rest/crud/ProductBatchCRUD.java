package rest.crud;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.Initializer;
import dataTransferObjects.ProductBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("product_batch")
public class ProductBatchCRUD {

	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ProductBatchDTO getProductBatch(String id) {
		try {
			return Initializer.getProductBatchController().getProductBatch(Integer.parseInt(id));
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
	public List<ProductBatchDTO> getProductBatchList() {
		try {
			return Initializer.getProductBatchController().getProductBatchList();
		} catch (DALException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createProductBatch(ProductBatchDTO productbatch) {
		try {
			Initializer.getProductBatchController().createProductBatch(productbatch);
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
	public String updateProductBatch(ProductBatchDTO productbatch) {
		try {
			Initializer.getProductBatchController().updateProductBatch(productbatch);
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
