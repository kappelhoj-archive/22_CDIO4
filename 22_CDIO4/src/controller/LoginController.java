package controller;

import java.util.Hashtable;

import controller.interfaces.ILoginController;
import dataAccessObjects.MySQLOperatoerDAO;
import dataAccessObjects.interfaces.OperatoerDAO;
import exceptions.DALException;

public class LoginController implements ILoginController {

	Hashtable<Integer, Integer> adminKeyTable = new Hashtable<Integer, Integer>();
	OperatoerDAO dao = new MySQLOperatoerDAO();

	public LoginController(){

	}

	@Override
	public boolean checkLogin(int id, String password) {
		try{
			if (adminKeyTable.remove(id) == Integer.parseInt(password))
				return true;
			else
				return false;

		}catch(Exception e){
			try{
				if(dao.getOperatoer(id).getPassword() == password)
					return true;
				else
					return false;

			}catch(DALException e2){
				return false;
			}
		}
	}


	@Override
	public int generateAdminKey(int id) {
		Integer key = new Integer((int) Math.floor(Math.random()*10000));
		adminKeyTable.put(id, key);
		return key;
	}

}
