package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import controller.interfaces.ILoginController;
import controller.teststub.LoginStubController;
import dataTransferObjects.LoginPOJO;
import exceptions.DALException;
import exceptions.InputException;

@Path("login")
public class Login {
//	ILoginController loginController = new LoginController();
	ILoginController loginController = new LoginStubController();

	@Path("login-user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginUser(LoginPOJO login) {
		System.out.println(login);
		switch(loginController.checkLogin(login)) {
		case TRUE:
			return "logged_in";
		case SUPER:
			return "super_admin";
		case NEW:
			return "new_log_in";
		default:
			return "not_logged_in";
		}
	}
	
	@Path("new-password")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String newPassword(LoginPOJO login) {
		try {
			loginController.setNewPassword(Integer.parseInt(login.getId()), login.getPassword());
			return "success";
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fejl!";
		} catch (InputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Fejl: Password er ikke gyldigt.";
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Der skete en fejl!";
		}
	}
}
