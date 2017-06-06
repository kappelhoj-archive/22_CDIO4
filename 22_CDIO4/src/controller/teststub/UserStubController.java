package controller.teststub;

import java.util.ArrayList;
import java.util.List;

import controller.interfaces.IUserController;
import dataTransferObjects.OperatoerDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class UserStubController implements IUserController {
	OperatoerDTO testUser = new OperatoerDTO(1, "Jeppe", "JTN", "300389-2793", "test", "Admin");
	ArrayList<OperatoerDTO> userList = new ArrayList<OperatoerDTO>();
	
	@Override
	public void createUser(OperatoerDTO user) throws InputException, CollisionException, DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(OperatoerDTO user) throws InputException, DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OperatoerDTO getUser(int userID) throws InputException, DALException {
		return testUser;
	}

	@Override
	public List<OperatoerDTO> getUserList() throws DALException {
		userList.add(testUser);
		return userList;
	}
	
}
