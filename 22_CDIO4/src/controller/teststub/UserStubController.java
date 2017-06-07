package controller.teststub;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import controller.interfaces.IUserController;
import dataAccessObjects.UserDAO;
import dataTransferObjects.ProductBatchCompDTO;
import dataTransferObjects.UserDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class UserStubController implements IUserController {
	static UserDTO testUser;
	static UserDTO testUser2;
	static UserDTO testUser3;
	static ArrayList<UserDTO> userList;
	
	public UserStubController() {
		testUser = new UserDTO(1, "Jeppe", "JTN", "300389-2793", "test", "Admin");
//		testUser2 = new UserDTO(2, "Mikkel", "MHL", "300389-2793", "test", "Farmaceut");
//		testUser3 = new UserDTO(3, "Arvid", "AKL", "300389-2793", "test", "Laborant");
		userList = new ArrayList<UserDTO>();
		userList.add(testUser);
//		userList.add(testUser2);
//		userList.add(testUser3);
	}
	
	@Override
	public void createUser(UserDTO user) throws InputException, CollisionException, DALException {
		
		userList.add(user);
		System.out.println(user);
		System.out.println(userList);
	}

	@Override
	public void updateUser(UserDTO user) throws InputException, DALException {
		if(testUser.getId() == user.getId())
		{
			System.out.println(user.toString());
		}
		else {
			System.out.println("Fejl");
		}
	}

	@Override
	public UserDTO getUser(int userID) throws InputException, DALException {
		return testUser;
	}

	@Override
	public List<UserDTO> getUserList() throws DALException {
		System.out.println(userList);
		return userList;
	}
	
}
