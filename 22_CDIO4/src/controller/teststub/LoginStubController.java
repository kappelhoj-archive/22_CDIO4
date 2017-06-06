package controller.teststub;

import controller.interfaces.ILoginController;
import dataTransferObjects.LoginPOJO;
import exceptions.DALException;
import exceptions.InputException;

public class LoginStubController implements ILoginController {

	LoginPOJO user1 = new LoginPOJO("1", "test");
	LoginPOJO user2 = new LoginPOJO("2", "test2");

	@Override
	public int generateAdminKey(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public LoginPOJO getLogin() {
		return user1;
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
		if(Integer.parseInt(user.getId()) == Integer.parseInt(user1.getId()) && user.getPassword().equals(user1.getPassword()))
		{
			return LoginState.TRUE;
		}
		else if(user.isSuperAdmin()) {
			return LoginState.SUPER;
		}
		else {
			return LoginState.FALSE;
		}
	}

}
