package rest.crud;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.interfaces.IUserController;
import controller.teststub.UserStubController;
import dataTransferObjects.UserDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("user")
public class UserCRUD {
//	IUserController userController = new UserController();
	IUserController userController = new UserStubController();
	
	@Path("read-user")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO readUser(String userId)
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
	
	@Path("read-users")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> readUsers()
	{
		try {
			return userController.getUserList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Path("create-user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(UserDTO user)
	{
		try {
			userController.createUser(user);
			return "success";
		} catch (InputException e) {
			e.printStackTrace();
			return "input-error";
		} catch (CollisionException e) {
			e.printStackTrace();
			return "id-error";
		} catch (DALException e) {
			e.printStackTrace();
			return "system-error";
		}
	}
	
	@Path("update-user")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateUser(UserDTO user) {
		try {
			userController.updateUser(user);
			return "success";
		} catch (InputException e) {
			e.printStackTrace();
			return "input-error";
		} catch (DALException e) {
			e.printStackTrace();
			return "system-error";
		}
	}
}
