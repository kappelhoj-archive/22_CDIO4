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
	public int resetPassword(int id) throws InputException, DALException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNewPassword(int id, String password) throws InputException, DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LoginState checkLogin(LoginPOJO user) {
		if(Integer.parseInt(user.getId()) == Integer.parseInt(login.getId()) && user.getPassword().equals(login.getPassword()))
		{
			return LoginState.TRUE;
		}
		else if(Integer.parseInt(user.getId()) == Integer.parseInt(admin.getId()) && user.getPassword().equals(admin.getPassword())) {
			return LoginState.SUPER;
		}
		else {
			return LoginState.FALSE;
		}
	}

}
