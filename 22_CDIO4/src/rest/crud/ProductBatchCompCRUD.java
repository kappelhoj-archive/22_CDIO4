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

	//Not in use.
	@Path("read")
	@POST
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