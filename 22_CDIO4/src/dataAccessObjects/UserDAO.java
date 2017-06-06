package dataAccessObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dataTransferObjects.UserDTO;

/**
 * The class UserDAO has the responsibility to insert, delete, access, update,
 * save and load the data in the system.
 * 
 * @author Group 22
 */
public class UserDAO implements IUserDAO {

	// Instance variables
	private ArrayList<UserDTO> users;

	/**
	 * Constructor. Constructs a user Data Access Object. If there exists no
	 * user data it creates space for some otherwise it loads the user data from
	 * the given file.
	 */
	public UserDAO(String fileName) {
		if (fileName == null) {
			users = new ArrayList<UserDTO>();
		} else {
			try {
				users = loadUsers();
			} catch (DALException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Returns the user with the specified ID if he exists, throws DALException
	 * otherwise.
	 * 
	 * @param userId
	 *            The ID of the user that you want.
	 * @return The user with the given Id
	 */
	public UserDTO getUser(int userID) throws DALException {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == userID) {
				return users.get(i);
			}
		}
		throw new DALException("There exists no user with user id: " + userID);
	}

	/**
	 * Returns a list of all users in the data.
	 * 
	 * @return a list of users.
	 */
	public List<UserDTO> getUserList() throws DALException {
		return users;
	}

	/**
	 * Adds a user to the DAO object and saves it to the data.
	 * 
	 * @param user.
	 *            The user to be added.
	 */
	public void createUser(UserDTO user) throws DALException {
		users.add(user);
		saveUsers(users);
	}

	/**
	 * Deletes a user from the DAO object with the specified userID and updates
	 * the information in the data.
	 * 
	 * @param userID
	 *            The id of the user to be deleted.
	 */
	public void deleteUser(int userID) throws DALException {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == userID) {
				users.remove(i);
				saveUsers(users);
				return;
			}
		}
		throw new DALException("There exists no user with user id: " + userID);
	}

	/**
	 * Updates a user in the data.
	 * 
	 * @param user
	 *            The updated user.
	 */
	public void updateUser(UserDTO user) throws DALException {
		
		for(int i=0;i<users.size();i++){
			if(users.get(i).getId()==user.getId()){
				users.remove(i);
				break;
			}
		}
		users.add(user);
		saveUsers(users);
	}

	/**
	 * Reads and decodes a file with user data and converts the data into
	 * objects.
	 * 
	 * @return An ArrayList of users from the given file.
	 * @throws DALException
	 *             The exception to be thrown something goes wrong under the
	 *             reading and decoding.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<UserDTO> loadUsers() throws DALException {
		ArrayList<UserDTO> users = new ArrayList<UserDTO>();

		ObjectInputStream OIS = null;
		try {
			FileInputStream fIS = new FileInputStream("userData.txt");
			OIS = new ObjectInputStream(fIS);
			Object inObj = OIS.readObject();
			if (inObj instanceof ArrayList<?>) {
				users = (ArrayList<UserDTO>) inObj;
			} else {
				throw new DALException("Wrong object in file");
			}
		} catch (FileNotFoundException e) {
			return users;
		} catch (IOException e) {
			throw new DALException("Error while reading disk!", e);
		} catch (ClassNotFoundException e) {
			throw new DALException("Error while reading file - Class not found!", e);
		} finally {
			if (OIS != null) {
				try {
					OIS.close();
				} catch (IOException e) {
					throw new DALException("Error closing pObjectStream!", e);
				}
			}
		}
		return users;
	}

	/**
	 * Saves the user data to a text file.
	 * 
	 * @param users
	 *            The user data to be saved.
	 * @throws DALException
	 *             The exception to be thrown if something goes wrong under the
	 *             saving.
	 */
	public void saveUsers(ArrayList<UserDTO> users) throws DALException {
		ObjectOutputStream OOS = null;
		try {
			FileOutputStream FOS = new FileOutputStream("userData.txt");
			OOS = new ObjectOutputStream(FOS);
			OOS.writeObject(users);
		} catch (FileNotFoundException e) {
			throw new DALException("Error locating file", e);
		} catch (IOException e) {
			throw new DALException("Error writing to disk", e);
		} finally {
			if (OOS != null) {
				try {
					OOS.close();
				} catch (IOException e) {
					throw new DALException("Unable to close ObjectStream", e);
				}
			}
		}
	}
}
