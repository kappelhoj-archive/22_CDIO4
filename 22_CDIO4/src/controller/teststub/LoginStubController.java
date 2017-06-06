package controller.teststub;

import controller.interfaces.ILoginController;
import dataTransferObjects.LoginPOJO;
import exceptions.DALException;
import exceptions.InputException;

public class LoginStubController implements ILoginController {

	LoginPOJO login = new LoginPOJO("1", "test");
	LoginPOJO admin = new LoginPOJO("admin", "root");

	@Override
	public int generateAdminKey(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public LoginPOJO getLogin() {
		return login;
	}

	@Override
	public LoginState checkLogin(int id, String password) {
		if(id == Integer.parseInt(login.getId()) && password.equals(login.getPassword()))
		{
			return LoginState.TRUE;
		}
		else {
			return LoginState.FALSE;
		}
	}

	@Override
	public int resetPassword(int id) throws InputException, DALException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNewPassword(int id, String password) throws InputException, DALException {
		// TODO Auto-generated method stub
		
	}

}
