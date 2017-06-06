package controller;

import java.util.List;

import controller.interfaces.IUserController;
import dataAccessObjects.UserDAO;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.UserDTO;
import exceptions.*;
import staticClasses.Validator;

public class UserController implements IUserController{

	IUserDAO dao = new UserDAO();

	public void validation(UserDTO user) throws InputException{
		Validator.validateUserID(user.getOprId());
		Validator.validateUsername(user.getOprNavn());
		Validator.validateInitials(user.getIni());
		Validator.validateCPR(user.getCpr());
		Validator.validatePassword(user.getPassword());
		Validator.validateRole(user.getRolle());
	}

	@Override
	public void createUser(UserDTO user) throws InputException, CollisionException, DALException{
		try{

			validation(user);

			dao.createOperatoer(user);

			return;

		}catch(InputException e){
			throw new InputException(e.toString());
			
		}catch(CollisionException e){
			throw new CollisionException(e.toString());

		}catch(DALException e){
			System.out.println(e.toString());
			throw new DALException (e.toString());
		}
	}

	@Override
	public void updateUser(UserDTO user) throws InputException, DALException{
		try{

			validation(user);

			dao.updateOperatoer(user);

			return;

		}catch(InputException e){
			throw new InputException(e.toString());

		}catch(DALException e){
			System.out.println(e.toString());
			throw new DALException (e.toString());

		}
	}

	@Override
	public UserDTO getUser(int id) throws InputException, DALException{
		try{
			Validator.validateUserID(id);

			return dao.getUser(id);

		}catch(InputException e){
			throw new InputException(e.toString());

		}catch(DALException e){
			System.out.println(e.toString());
			throw new DALException (e.toString());

		}
	}

	@Override
	public List<UserDTO> getUserList() throws DALException{
		try{

			return dao.getUserList();

		}catch(DALException e){
			System.out.println(e.toString());
			throw new DALException (e.toString());

		}
	}
	
	/**
	 * Generates initials from a given name.
	 * 
	 * @param name
	 *            The name the initials needs to be created from.
	 * @return The generated initials.
	 */
	public String generateInitials(String name) {
		String[] nameParts = name.split(" ");
		String newIni = "";
		if (nameParts.length == 1) {
			newIni = nameParts[0].substring(0, 2);
		} else {
			int length = Math.min(nameParts.length, 4);
			for (int i = 0; i < length; i++) {
				newIni = newIni + nameParts[i].substring(0, 1);
			}
		}
		return newIni;
	}
}