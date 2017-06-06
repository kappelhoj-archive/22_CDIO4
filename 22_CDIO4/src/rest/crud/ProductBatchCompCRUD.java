package rest.crud;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import controller.ProductBatchCompController;
import controller.interfaces.IProductBatchCompController;
import dataTransferObjects.ProductBatchCompPOJO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

@Path("product_batch_component")
public class ProductBatchCompCRUD {

	IProductBatchCompController controller = new ProductBatchCompController();

	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public ProductBatchCompDTO getProductBatchComp(ProductBatchCompPOJO ids) {
		try {
			return controller.getProductBatchComp(Integer.parseInt(ids.getPbId()), Integer.parseInt(ids.getRbId()));
		}

		catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	// TODO: Kig på HTTP ERROR
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductBatchCompDTO> getProductBatchCompList(String pbId) {
		try {
			return controller.getProductBatchCompList(Integer.parseInt(pbId));
		} catch (DALException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	// TODO: Kig på HTTP ERROR
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductBatchCompDTO> getProductBatchCompList() {
		try {
			return controller.getProductBatchCompList();
		} catch (DALException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createProductBatchComp(ProductBatchCompDTO productbatchcomp) {
		try {
			controller.createProductBatchComp(productbatchcomp);
			return "success";
		} catch (CollisionException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der findes allerede et produkt batch komponent med det indtastede id.";
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet.";

		}
	}

	@Path("update")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateProductBatchComp(ProductBatchCompDTO productbatchComp) {
		try {
			controller.updateProductBatchComp(productbatchComp);
			return "success";
		}

		catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet.";

		}
	}

}