package controller.interfaces;

import dataTransferObjects.LoginPOJO;
import exceptions.DALException;
import exceptions.InputException;

public interface ILoginController {
	
	public enum LoginState{
		TRUE,
		FALSE,
		SUPER,
		NEW
	}
	
	LoginState checkLogin(LoginPOJO user);
	int generateAdminKey(int id);
	int resetPassword(int id) throws InputException, DALException;
	void setNewPassword(int id, String password) throws InputException, DALException;
	boolean checkPassword(LoginPOJO login) throws InputException, DALException;
}
