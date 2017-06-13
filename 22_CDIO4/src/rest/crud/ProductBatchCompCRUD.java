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
import dataTransferObjects.ProductBatchCompPOJO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

@Path("product_batch_comp")
public class ProductBatchCompCRUD {

	/**
	 * Receives a JSON-object as a ProductBatchCompPOJO and returns the product batch component with the received information.
	 * @param i The given ProductBatchCompPOJO containing the information required to return the correct product batch component.
	 * @return The ProductBatchCompDTO as a JSON-object.
	 */
	//Not in use.
	@Path("read")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductBatchCompDTO getProductBatchComp(ProductBatchCompPOJO i) {
		try {
			return Initializer.getProductBatchCompController().getProductBatchComp(Integer.parseInt(i.getPbId()),
					Integer.parseInt(i.getRbId()));
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Returns a list of all the product batch components in the product batch with the given id.
	 * @param pbId The given product batch id.
	 * @return The List<ProductBatchCompDTO> as a JSON.
	 */
	@Path("read_list_specific")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductBatchCompDTO> getProductBatchCompList(String pbId) {
		try {
			return Initializer.getProductBatchCompController().getProductBatchCompList(Integer.parseInt(pbId));
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Returns a list of all the product batch components a JSON-object.
	 * @return The List<ProductBatchCompDTO> as a JSON-object.
	 */
	//Not in use.
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductBatchCompDTO> getProductBatchCompList() {
		try {
			return Initializer.getProductBatchCompController().getProductBatchCompList();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	//Not in use.
	/**
	 * Receives a JSON-object as ProductBatchCompDTO and adds the ProductBatchCompDTO to the data layer.
	 * @param pbc The product batch component to be added to the data layer.
	 * @return A message which tells whether the creation succeeded or not.
	 */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createProductBatchComp(ProductBatchCompDTO pbc) {
		try {
			Initializer.getProductBatchCompController().createProductBatchComp(pbc);
			return "success: Produkt batch komponenten blev oprettet og tilføjet til produkt batchen.";
		} catch (CollisionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "collision-error: Der eksisterede allerede en produkt batch komponent med det indtastede id.";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}

	/**
	 * Receives a JSON-object as a ProductBatchCompDTO and updates the ProductBatchCompDTo in the data layer.
	 * @param pbc The product batch component to be updated in the data layer.
	 * @return A message which tells whether the update succeeded
	 */
	//Not in use.
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateProductBatchComp(ProductBatchCompDTO pbc) {
		try {
			Initializer.getProductBatchCompController().updateProductBatchComp(pbc);
			return "success: Produkt batch komponenten blev opdateret.";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "system-error: Der skete en fejl i systemet.";
		}
	}
}