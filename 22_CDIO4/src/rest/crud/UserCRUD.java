package rest.crud;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.UserController;
import controller.interfaces.IUserController;
import dataTransferObjects.OperatoerDTO;
import exceptions.DALException;
import exceptions.InputException;

@Path("user")
public class UserCRUD {
	private static boolean isInitialized = false;
	IUserController userController = new UserController();
	
	public static void initialize() {
		if(!isInitialized)
		{
			
		}
		
		isInitialized = true;
	}
	
	@Path("get-user")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public OperatoerDTO readUser(int userId)
	{
		try {
			return userController.getUser(userId);
		} catch (InputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
