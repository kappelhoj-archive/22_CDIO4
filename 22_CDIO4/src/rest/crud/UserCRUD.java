package rest.crud;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.interfaces.IUserController;
import controller.teststub.UserStubController;
import dataTransferObjects.OperatoerDTO;
import exceptions.DALException;
import exceptions.InputException;

@Path("user")
public class UserCRUD {
	private static boolean isInitialized = false;
//	IUserController userController = new UserController();
	IUserController userController = new UserStubController();
	
	public static void initialize() {
		if(!isInitialized)
		{
			
		}
		
		isInitialized = true;
	}
	
	@Path("get-user")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public OperatoerDTO readUser(String userId)
	{
		int id = Integer.parseInt(userId);
		try {
			return userController.getUser(id);
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
	
	@Path("get-users")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OperatoerDTO> readUsers()
	{
		try {
			return userController.getUserList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
