package controller;

import java.util.Hashtable;

import controller.interfaces.ILoginController;
import dataAccessObjects.MyOperatoerDAO;
import dataAccessObjects.interfaces.OperatoerDAO;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

public class LoginController implements ILoginController {

	static Hashtable<Integer, Integer> adminKeyTable = new Hashtable<Integer, Integer>();
	OperatoerDAO dao = new MyOperatoerDAO();

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
				if(dao.getOperatoer(id).getPassword().equals(password))
					return true;
				else
					return false;

			}catch(DALException e2){
				System.out.println(e2);
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
	
	public void resetPassword(int id){
		try{
			
		}
	}
	
	
	/**
	 * Generates a password for the userDTO accepting the rules of DTU
	 * passwords.
	 * 
	 * @return The generated password.
	 */
	public String generatePassword() {
		String password = "";
		int passLength = 8;
		boolean passwordValid = false;
		while (!passwordValid) {
			password = "";
			for (int i = 0; i < passLength; i++) {
				char newCharacther;
				int randGroup = (int) (Math.random() * 100);
				// Add a special characther
				if (randGroup < 5) {
					String specialCharacthers = ".-_+!?=";
					int rand = (int) (Math.random() * specialCharacthers.length());
					newCharacther = specialCharacthers.charAt(rand);
				}
				// Add a small letter.
				else if (randGroup < 30) {
					int rand = (int) (Math.random() * (122 - 97 + 1) + 97);
					newCharacther = (char) rand;
				}
				// Add a large letter.
				else if (randGroup < 55) {
					int rand = (int) (Math.random() * (90 - 65 + 1) + 65);
					newCharacther = (char) rand;
				}
				// Add a number.
				else {
					int rand = (int) (Math.random() * (57 - 48 + 1) + 48);
					newCharacther = (char) rand;
				}
				password += newCharacther + "";
			}
			try {
				Validator.validatePassword(password);
				passwordValid = true;
			} catch (InputException e) {
				// Catches invalid passwords and creates a new one.
			}
		}
		return password;
	}

}
