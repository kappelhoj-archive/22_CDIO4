package controller;

import java.util.ArrayList;
import java.util.Collections;
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

	/**
	 * Verifies the userDTO Input
	 * @param userId
	 * @return UserDTO
	 * @throws InputException : params are not correct
	 */
	public void validation(UserDTO user) throws InputException{
		Validator.validateUserID(user.getId());
		Validator.validateUsername(user.getName());
		Validator.validateInitials(user.getIni());
		Validator.validateCPR(user.getCpr());
		Validator.validatePassword(user.getPassword());
		Validator.validateRole(user.getRole());
	}

	/**
	 * Adds a UserDTO to the saved data
	 * @param UserDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 * @throws InputException : params are not correct
	 */
	@Override
	public void createUser(UserDTO user) throws InputException, CollisionException, DALException{
		validation(user);

		dao.createOperatoer(user);

		return;
	}

	/**
	 * Updates a UserDTO in the saved data
	 * @param UserDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 * @throws InputException : params are not correct
	 */
	@Override
	public void updateUser(UserDTO user) throws InputException, DALException{
		validation(user);

		dao.updateOperatoer(user);

		return;
	}

	/**
	 * Returns a copy of a UserDTO from the data
	 * @param userId
	 * @return UserDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 * @throws InputException : params are not correct
	 */
	@Override
	public UserDTO getUser(int id) throws InputException, DALException{
		Validator.validateUserID(id);

		return dao.getUser(id);
	}

	/**
	 * Returns a list of UserDTOs from the data
	 * @return List<UserDTO>
	 */
	@Override
	public List<UserDTO> getUserList() throws DALException{
		ArrayList<UserDTO> sortedArray = (ArrayList<UserDTO>) dao.getUserList();

		Collections.sort(sortedArray);

		return sortedArray;
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