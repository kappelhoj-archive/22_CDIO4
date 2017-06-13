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
	public UserDTO readUser(String userId) {
		int id = Integer.parseInt(userId);
		try {
			return Initializer.getUserController().getUser(id);
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

	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> readUsers() {
		try {
			return Initializer.getUserController().getUserList();
		} catch (DALException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(UserDTO user) {
		try {
			int key = Initializer.getLoginController().generateAdminKey(user.getId());
			Initializer.getUserController().createUser(user);
			return "success: Brugeren blev oprettet. \n Brugeren har id: " + user.getId() + "og login key: " + key + ".";
		} catch (InputException e) {
			e.printStackTrace();
			return "input-error: Det indtastede er ugyldigt.";
		} catch (CollisionException e) {
			e.printStackTrace();
			return "collision-error: Der eksisterede allerede en bruger i systemet med det indtastede id.";
		} catch (DALException e) {
			e.printStackTrace();
			return "system-error: Der skete en fejl i systemet.";
		}
	}

	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateUser(UserDTO user) {
		try {
			Initializer.getUserController().updateUser(user);
			return "success: Brugeren blev opdateret.";
		} catch (InputException e) {
			e.printStackTrace();
			return "input-error: Det indtastede er ugyldigt.";
		} catch (DALException e) {
			e.printStackTrace();
			return "system-error: Der skete en fejl i systemet.";
		}
	}
}
