package controller.teststub;

import java.util.ArrayList;
import java.util.List;

import controller.interfaces.IUserController;
import dataTransferObjects.UserDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class UserStubController implements IUserController {
	UserDTO testUser = new UserDTO(1, "Jeppe", "JTN", "300389-2793", "test", "Admin");
	UserDTO testUser2 = new UserDTO(2, "Mikkel", "MHL", "300389-2793", "test", "Farmaceut");
	UserDTO testUser3 = new UserDTO(3, "Arvid", "AKL", "300389-2793", "test", "Laborant");
	ArrayList<UserDTO> userList = new ArrayList<UserDTO>();
	
	@Override
	public void createUser(UserDTO user) throws InputException, CollisionException, DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserDTO user) throws InputException, DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserDTO getUser(int userID) throws InputException, DALException {
		return testUser;
	}

	@Override
	public List<UserDTO> getUserList() throws DALException {
		userList.add(testUser);
		userList.add(testUser2);
		userList.add(testUser3);
		return userList;
	}
	
}
