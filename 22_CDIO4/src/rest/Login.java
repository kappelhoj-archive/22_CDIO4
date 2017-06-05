package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import controller.LoginController;
import controller.UserController;
import controller.interfaces.ILoginController;
import controller.interfaces.IUserController;
import controller.teststub.LoginStubController;
import dataTransferObjects.LoginPOJO;

@Path("login")
public class Login {
//	ILoginController loginController = new LoginController();
	ILoginController loginController = new LoginStubController();

	@Path("login-user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginUser(LoginPOJO login) {
		System.out.println(login);
		if(login.isSuperAdmin())
		{
			return "super_admin";
		}
		else if(loginController.checkLogin(Integer.parseInt(login.getId()), login.getPassword()))
		{
			return "logged_in";
		}
		else {
			return "not_logged_in";
		}

	}
}
