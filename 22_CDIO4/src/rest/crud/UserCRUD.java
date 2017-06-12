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
import dataTransferObjects.UserDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

@Path("user")
public class UserCRUD {
	
	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO readUser(String userId)
	{
		int id = Integer.parseInt(userId);
		try {
			return Initializer.getUserController().getUser(id);
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
	
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> readUsers()
	{
		try {
			return Initializer.getUserController().getUserList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(UserDTO user)
	{
		try {
			Initializer.getUserController().createUser(user);
			return "success: Successbesked";
		} catch (InputException e) {
			e.printStackTrace();
			return "input-error: Fejlbesked";
		} catch (CollisionException e) {
			e.printStackTrace();
			return "id-error: Fejlbesked";
		} catch (DALException e) {
			e.printStackTrace();
			return "system-error: Fejlbesked";
		}
	}
	
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateUser(UserDTO user) {
		try {
			Initializer.getUserController().updateUser(user);
			return "success: Successbesked";
		} catch (InputException e) {
			e.printStackTrace();
			return "input-error: Fejlbesked";
		} catch (DALException e) {
			e.printStackTrace();
			return "system-error: Fejlbesked";
		}
	}
}
