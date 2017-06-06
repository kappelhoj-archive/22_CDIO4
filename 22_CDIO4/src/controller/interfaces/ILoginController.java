package controller.interfaces;

import exceptions.DALException;
import exceptions.InputException;

public interface ILoginController {
	boolean checkLogin(int id, String password);
	int generateAdminKey(int id);
	int resetPassword(int id) throws InputException, DALException;
}
