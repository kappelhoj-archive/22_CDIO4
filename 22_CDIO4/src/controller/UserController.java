package controller;

import java.util.List;

import controller.interfaces.IUserController;
import dataAccessObjects.MyOperatoerDAO;
import dataAccessObjects.interfaces.OperatoerDAO;
import dataTransferObjects.OperatoerDTO;
import exceptions.*;
import staticClasses.Validator;

public class UserController implements IUserController{

	OperatoerDAO dao = new MyOperatoerDAO();

	public void validation(OperatoerDTO user) throws InputException{
		Validator.validateUserID(user.getOprId());
		Validator.validateUsername(user.getOprNavn());
		Validator.validateInitials(user.getIni());
		Validator.validateCPR(user.getCpr());
		Validator.validatePassword(user.getPassword());
		Validator.validateRole(user.getRolle());
	}

	@Override
	public void createUser(OperatoerDTO user) throws InputException, CollisionException, DALException{
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
	public void updateUser(OperatoerDTO user) throws InputException, DALException{
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
	public OperatoerDTO getUser(int userID) throws InputException, DALException{
		try{
			Validator.validateUserID(userID);

			return dao.getOperatoer(userID);

		}catch(InputException e){
			throw new InputException(e.toString());

		}catch(DALException e){
			System.out.println(e.toString());
			throw new DALException (e.toString());

		}
	}

	@Override
	public List<OperatoerDTO> getUserList() throws DALException{
		try{

			return dao.getOperatoerList();

		}catch(DALException e){
			System.out.println(e.toString());
			throw new DALException (e.toString());

		}
	}
}