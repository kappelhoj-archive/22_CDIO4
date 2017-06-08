package controller;

import java.util.List;

import controller.interfaces.IUserController;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.UserDTO;
import exceptions.*;
import staticClasses.Validator;

public class UserController implements IUserController{

	IUserDAO dao;

	public UserController(IUserDAO dao){
		this.dao = dao;
	}	

	public IUserDAO getDao() {
		return dao;
	}

	public void validation(UserDTO user) throws InputException{
		Validator.validateUserID(user.getId());
		Validator.validateUsername(user.getName());
		Validator.validateInitials(user.getIni());
		Validator.validateCPR(user.getCpr());
		Validator.validatePassword(user.getPassword());
		Validator.validateRole(user.getRole());
	}

	@Override
	public void createUser(UserDTO user) throws InputException, CollisionException, DALException{


		validation(user);

		dao.createOperatoer(user);

		return;

	}

	@Override
	public void updateUser(UserDTO user) throws InputException, DALException{

		validation(user);

		dao.updateOperatoer(user);

		return;

	}

	@Override
	public UserDTO getUser(int id) throws InputException, DALException{

		Validator.validateUserID(id);

		return dao.getUser(id);

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