package dataAccessObjects;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Hashtable;

import exceptions.CollisionException;
import exceptions.DALException;
import dataAccessObjects.interfaces.IWeightControlDAO;
//import staticClasses.FileManagement;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.IWeightControlDTO;
import dataTransferObjects.UserDTO;


public class UserDAO implements IUserDAO,IWeightControlDAO {

	static Hashtable<Integer, UserDTO> userList = new Hashtable<Integer, UserDTO>();

	public UserDAO(){
		//try{
		//	userList = FileManagement.retrieveFrom("operatoer");
		//}catch (DALException e){
		//	try {
		//		FileManagement.saveData(new ArrayList<DTO>(), "operatoer");
		//	} catch (DALException e1) {
		//		System.out.println(e1);
		//	}
		//}

		//userList = new ArrayList<DTO>();
	}

	
	public UserDTO getUser(int id) throws DALException {

		if(userList.get(id) != null)
			return userList.get(id).copy();
	
		else
			throw new DALException("Unknown UserID: " + id);

	}


	public void createOperatoer(UserDTO user) throws DALException {
		if (userList.putIfAbsent(user.getId(), user.copy()) == null)
			return;
		
		else
			throw new CollisionException("User ID:"+user.getId()+" already exists !");

		//FileManagement.saveData(userList, "operatoer");

	}

	public void updateOperatoer(UserDTO user) throws DALException {
		userList.replace(user.getId(), user.copy());
		//FileManagement.saveData(userList, "operatoer");
	}
	

	public List<UserDTO> getUserList() throws DALException {
		List<UserDTO> users = new ArrayList<UserDTO>();

		Set<Integer> keys = userList.keySet();
		
		for(Integer key : keys){
			users.add(userList.get(key).copy());
		}

		return users;
	}


	@Override
	public IWeightControlDTO getDTOById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
