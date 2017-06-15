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
import dataTransferObjects.ProductBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

@Path("product_batch")
public class ProductBatchCRUD {

	/**
	 * Returns the product batch with the given product batch id as a JSON-object.
	 * @param id The given product batch id.
	 * @return The ProductBatchDTO as a JSON-object.
	 */
	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ProductBatchDTO getProductBatch(String id) {
		try {
			return Initializer.getProductBatchController().getProductBatch(Validator.idToInteger(id));
		} catch (DALException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Returns a list of all product batches as a JSON-object.
	 * @return The list <ProductBatchDTO> as a JSON-object.
	 */
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductBatchDTO> getProductBatchList() {
		try {
			return Initializer.getProductBatchController().getProductBatchList();
		} catch (DALException e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Receives a JSON-object as a ProductBatchDTO and adds the ProductBatchDTo to the data layer.
	 * @param productbatch The product batch to be added to the data layer.
	 * @return A message which tells whether the creation succeeded or not.
	 */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createProductBatch(ProductBatchDTO productbatch) {
		try {
			Initializer.getProductBatchController().createProductBatch(productbatch);
			return "success: Produkt batchen blev oprettet.";
		} catch (CollisionException e) {
			return "collision-error: Der eksisterer allerede en produkt batch med det indtastede id";
		} catch (InputException e) {
			return "input-error: Det indtastede er ugyldigt.";
		} catch (DALException e) {
			System.out.println(e);
			return "system-error: Der skete en fejl i systemet.";
		}
	}

	//Not in use.
	/**
	 * Receives a JSON-object as a UserDTO and updates the UserDTo in the data layer.
	 * @param productbatch The product batch to be updated in the data layer.
	 * @return A message which tells whether the update succeeded or not.
	 */
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateProductBatch(ProductBatchDTO productbatch) {
		try {
			Initializer.getProductBatchController().updateProductBatch(productbatch);
			return "success: Produkt batchen blev opdateret.";
		} catch (InputException e) {
			return "input-error Det indtastede er ugyldigt.";
		} catch (DALException e) {
			System.out.println(e);
			return "system-error: Der skete en fejl i systemet.";
		}
	}
}
