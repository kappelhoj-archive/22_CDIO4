package controller.interfaces;

import exceptions.DALException;
import exceptions.InputException;

public interface ILoginController {
	
	public enum LoginState{
		TRUE,
		FALSE,
		NEW
	}
	
	LoginState checkLogin(int id, String password);
	int generateAdminKey(int id);
	int resetPassword(int id) throws InputException, DALException;
	void setNewPassword(int id, String password) throws InputException, DALException;
}
