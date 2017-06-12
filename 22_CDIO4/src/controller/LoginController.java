package controller;

import java.util.Hashtable;

import controller.interfaces.ILoginController;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.LoginPOJO;
import dataTransferObjects.UserDTO;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Encryption;
import staticClasses.Validator;

public class LoginController implements ILoginController {
	
	IUserDAO dao;
	
	public IUserDAO getDao() {
		return dao;
	}

	public LoginController(IUserDAO dao){
		this.dao = dao;
	}
	

	static Hashtable<Integer, Integer> adminKeyTable = new Hashtable<Integer, Integer>();


	/**
	 * Checks if the Login Input is valid as a LoginPOJO
	 * @param LoginPOJO user
	 * @return LoginState  enum(SUPER, NEW, TRUE or FALSE)
	 */
	@Override
	public LoginState checkLogin(LoginPOJO user) {
		try{
			if(user.isSuperAdmin())
				return LoginState.SUPER;
			
		    else if (adminKeyTable.remove(Integer.parseInt(user.getId())).intValue() == Integer.parseInt(user.getPassword()))
				return LoginState.NEW;
			
			else
				return LoginState.FALSE;

		}catch(Exception e){ //If it can not convert the password to an Integer then it is not an Admin Key but a regular password
			try{
				if(dao.getUser(Integer.parseInt(user.getId())).getPassword().equals(Encryption.sha512(user.getPassword()+user.getId())))//checks the ID and password with DAO
					return LoginState.TRUE;
				else
					return LoginState.FALSE;

			}catch(Exception e2){ //If it catch an Exception, the user can not login correctly and needs to retry
				System.out.println(e2);
				return LoginState.FALSE;
			}
		}
	}
	
	/**
	 * Updates the user with the new password
	 * @param userID, userPassword
	 * @return void
	 */
	@Override
	public void setNewPassword(int id, String password) throws InputException, DALException{
		UserDTO user = dao.getUser(id).copy();

		user.setPassword(Encryption.sha512(password+user.getId()));

		dao.updateOperatoer(user);
	}


	/**
	 * Generates an Admin key, saves it in the static HashTable and returns it
	 * @param userID
	 * @return Admin key
	 */
	@Override
	public int generateAdminKey(int id) {
		Integer key = new Integer((int) Math.floor(Math.random()*10000));
		adminKeyTable.putIfAbsent(id, key);
		return key;
	}

	/**
	 * Resets the password of an user and gives his new Admin key to complete the reset.
	 * @param userID
	 * @return Admin key
	 */
	@Override
	public int resetPassword(int id) throws InputException, DALException{
		try{
			Validator.validateUserID(id);

			UserDTO user = dao.getUser(id).copy();

			user.setPassword(generatePassword());

			dao.updateOperatoer(user);

			return generateAdminKey(id);

		}catch(InputException e){
			throw new InputException(e.getMessage());
		}catch(DALException e){
			e.printStackTrace();
			throw new DALException(e.getMessage());
		}
	}


	/**
	 * Generates a default password which needs to be changed by the user with his first login
	 * using the Admin key.
	 * 
	 * @return The generated password.
	 */
	public String generatePassword() {
		String password = "XX";
		int rand = (int) (Math.random() * 1000000000);
		return password+rand;
	}

}
