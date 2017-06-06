package controller.interfaces;

import java.util.List;

import dataTransferObjects.OperatoerDTO;
import exceptions.*;

public interface IUserController {
	public void createUser(OperatoerDTO user) throws InputException, CollisionException, DALException;
	public void updateUser(OperatoerDTO user) throws InputException, DALException;
	public OperatoerDTO getUser(int userID) throws InputException, DALException;
	public List<OperatoerDTO> getUserList() throws DALException;

}
