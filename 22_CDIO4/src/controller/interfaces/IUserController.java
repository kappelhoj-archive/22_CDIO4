package controller.interfaces;

import java.util.List;

import dataTransferObjects.UserDTO;
import exceptions.*;

public interface IUserController {
	public void createUser(UserDTO user) throws InputException, CollisionException, DALException;
	public void updateUser(UserDTO user) throws InputException, DALException;
	public UserDTO getUser(int id) throws InputException, DALException;
	public List<UserDTO> getUserList() throws DALException;

}
