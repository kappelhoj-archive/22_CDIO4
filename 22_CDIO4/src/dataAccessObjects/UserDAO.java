package dataAccessObjects;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Hashtable;

import exceptions.CollisionException;
import exceptions.DALException;
import staticClasses.FileManagement;
import staticClasses.FileManagement.TypeOfData;
import dataAccessObjects.interfaces.IWeightControlDAO;
//import staticClasses.FileManagement;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.IWeightControlDTO;
import dataTransferObjects.UserDTO;


public class UserDAO implements IUserDAO,IWeightControlDAO {

	static Hashtable<Integer, UserDTO> userList = new Hashtable<Integer, UserDTO>();

	@SuppressWarnings("unchecked")
	public UserDAO(){
		try{
			System.out.println("Retrieving User Data...");
			userList = (Hashtable<Integer, UserDTO>) FileManagement.retrieveData(TypeOfData.USER);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println(e);
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(userList, TypeOfData.USER);
			System.out.println("Done.");
		}
	}

	/**
	 * Method which returns a copy of a UserDTO from the data
	 * @param userId
	 * @return UserDTO
	 */
	public UserDTO getUser(int id) throws DALException {

		if(userList.get(id) != null)
			return userList.get(id).copy();

		else
			throw new DALException("Unknown UserID: " + id);

	}

	/**
	 * Method which adds a UserDTO to the saved data
	 * @param UserDTO
	 * @return void
	 */
	public void createOperatoer(UserDTO user) throws DALException {
		if (userList.putIfAbsent(user.getId(), user.copy()) == null){
			FileManagement.writeData(userList, TypeOfData.USER);
			return;
		}

		else
			throw new CollisionException("User ID:"+user.getId()+" already exists !");


	}

	/**
	 * Method which updates a UserDTO in the saved data
	 * @param UserDTO
	 * @return void
	 */
	public void updateOperatoer(UserDTO user) throws DALException {
		userList.replace(user.getId(), user.copy());
		FileManagement.writeData(userList, TypeOfData.USER);
	}

	/**
	 * Method which returns a list of UserDTOs from the data
	 * @return List<UserDTO>
	 */
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
