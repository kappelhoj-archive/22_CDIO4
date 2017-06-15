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
import staticClasses.Validator;

@Path("user")
public class UserCRUD {

	/**
	 * Returns the user with the given user id as a JSON-object.
	 * @param userId The given id of the user.
	 * @return The UserDTO as a JSON-object.
	 */
	@Path("read")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO readUser(String userId) {
		try {
			return Initializer.getUserController().getUser(Validator.idToInteger(userId));
		} catch (InputException e) {
			return null;
		} catch (DALException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Returns a list of all users as a JSON-object.
	 * @return The List<UserDTO> as a JSON-object.
	 */
	@Path("read_list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> readUsers() {
		try {
			return Initializer.getUserController().getUserList();
		} catch (DALException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Receives a JSON-object as a UserDTO and adds the UserDTO to the data layer.
	 * @param user The user to be added to the data layer.
	 * @return A message which tells whether the creation succeeded or not. 
	 * If the response is positive the message includes the id of the user and a first time login key.
	 */
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(UserDTO user) {
		try {
			int key = Initializer.getLoginController().generateAdminKey(user.getId());
			Initializer.getUserController().createUser(user);
			return "success: Brugeren blev oprettet med id " + user.getId() + " og engangsnøglen " + key + ".";
		} catch (InputException e) {
			return "input-error: Det indtastede er ugyldigt.";
		} catch (CollisionException e) {
			return "collision-error: Der eksisterer allerede en bruger med det indtastede id.";
		} catch (DALException e) {
			System.out.println(e);
			return "system-error: Der skete en fejl i systemet.";
		}
	}

	/**
	 * Receives a JSON-object as a UserDTO and updates the UserDTO in the data layer. 
	 * @param user The user to be updated in the data layer.
	 * @return A message which tells whether the update succeeded or not.
	 */
	@Path("update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateUser(UserDTO user) {
		try {
			Initializer.getUserController().updateUser(user);
			return "success: Brugeren blev opdateret.";
		} catch (InputException e) {
			return "input-error: Det indtastede er ugyldigt.";
		} catch (DALException e) {
			System.out.println(e);
			return "system-error: Der skete en fejl i systemet.";
		}
	}
}
