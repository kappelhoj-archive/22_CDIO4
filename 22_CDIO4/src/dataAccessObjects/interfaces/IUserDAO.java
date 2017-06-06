package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.UserDTO;
import exceptions.DALException;

public interface IUserDAO {
	UserDTO getUser(int id) throws DALException;
	List<UserDTO> getUserList() throws DALException;
	void createOperatoer(UserDTO user) throws DALException;
	void updateOperatoer(UserDTO user) throws DALException;
}
