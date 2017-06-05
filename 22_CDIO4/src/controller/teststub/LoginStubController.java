package controller.teststub;

import controller.interfaces.ILoginController;
import dataTransferObjects.LoginPOJO;

public class LoginStubController implements ILoginController {

	LoginPOJO login = new LoginPOJO("1", "test");
	LoginPOJO admin = new LoginPOJO("admin", "root");
	
	@Override
	public boolean checkLogin(int id, String password) {
		if(id == Integer.parseInt(login.getId()) && password.equals(login.getPassword()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int generateAdminKey(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public LoginPOJO getLogin() {
		return login;
	}

}
