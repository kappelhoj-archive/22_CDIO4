package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.OperatoerDTO;
import exceptions.DALException;

public interface IUserDAO {
	OperatoerDTO getUser(int id) throws DALException;
	List<OperatoerDTO> getUserList() throws DALException;
	void createOperatoer(OperatoerDTO user) throws DALException;
	void updateOperatoer(OperatoerDTO user) throws DALException;
}
