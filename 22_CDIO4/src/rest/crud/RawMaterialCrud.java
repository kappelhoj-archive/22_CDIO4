package rest.crud;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import dataTransferObjects.RaavareDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("raw_material")
public class RawMaterialCrud {
	
	IRawMaterialController controller = new rawMaterialController();
	
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createRawMaterial(RaavareDTO rawMaterial)
	{
		try
		{
		controller.create(RaavareDTO);
		return "success";
		}
		catch (CollisionException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der findes allerede en råvare med det indtastede id.";
		}
		catch (InputException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Det indtastede er ugyldigt..";
		}
		catch (DALException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "Fejl: Der skete en fejl i systemet."; 
		}
	}
}