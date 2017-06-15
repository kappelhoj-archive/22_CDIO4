package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import controller.Initializer;
import dataTransferObjects.LoginPOJO;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

@Path("login")
public class Login {
	
	/**
	 * Log in to the system
	 * @param login information
	 * @return login status.
	 */
	@Path("user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginUser(LoginPOJO login) {
		switch(Initializer.getLoginController().checkLogin(login)) {
		case TRUE:
			return "true_login";
		case SUPER:
			return "super_login";
		case NEW:
			return "new_login";
		default:
			return "not_login: Bruger id eller password er forkert.";
		}
	}
	
	/**
	 * Create a new password.
	 * @param login info - the changed login information.
	 * @return
	 */
	@Path("new_password")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String newPassword(LoginPOJO login) {
		try {
			Initializer.getLoginController().setNewPassword(Validator.idToInteger(login.getId()), login.getPassword());
			return "success: Password opdateret";
			} catch (InputException e) {
			return "input-error: Det indstastede er ugyldigt.";
		} catch (DALException e) {
			return "system-error: Der skete en fejl i systemet.";
		}
	}
	
	/**
	 * Reset the password
	 * @param userId - id of the user to have its password reset.
	 * @return
	 */
	@Path("reset_password")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String resetPassword(String userId) {
		try {
			return "success: Brugerens password blev nulstillet. Engangsnøglen er " + Initializer.getLoginController().resetPassword(Validator.idToInteger(userId));
		} catch (InputException e) {
			return "input-error: Det indtastede er ugyldigt.";
		} catch (DALException e) {
			System.out.println(e);
			return "system-error: Der skete en fejl i systemet.";
		}
	}
	
	/**
	 * Change the password of a user.
	 * @param login - the login changes.
	 * @return Result of the password change.
	 */
	@Path("change_password")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String changePassword(LoginPOJO login) {
		try {
			if(Initializer.getLoginController().checkPassword(login)) {
				return "success: Rigtigt password.";
			}
			else {
				return "password-error: Forkert password.";
			}
		} catch (InputException e) {
			return "input-error: Forkert input.";
		
		} catch (DALException e) {
			System.out.println(e);
			return "system-error: Der skete en system fejl";
		}
	}
}
