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

	/*
	 * The warning "unchecked" is there because Java can not define if the file we try to convert
	 * to a HashTable is associated to this class.
	 * We decided to ignore this warning in all our DAO.
	 */
	@SuppressWarnings("unchecked")
	public UserDAO(){
		try{
			System.out.println("Retrieving User Data...");
			userList = (Hashtable<Integer, UserDTO>) FileManagement.retrieveData(TypeOfData.USER);
			System.out.println("Done.");

		}catch(Exception e){ //if we can not read the file then it is missing
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(userList, TypeOfData.USER);
			System.out.println("Done.");
		}
	}

	/**
	 * Method which returns a copy of a UserDTO from the data
	 * @param userId
	 * @return UserDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
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
	 * @throws CollisionException if the DTO it shall insert already exists
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
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	public void updateOperatoer(UserDTO user) throws DALException {
		getUser(user.getId()); //Try to get the DTO before so it can throw a DALException quicker
		
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
	public IWeightControlDTO getDTOById(int id)throws DALException {
		
		return getUser(id);
	}

}
