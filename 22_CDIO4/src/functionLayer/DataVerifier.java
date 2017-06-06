package functionLayer;

import java.util.List;

import dataAccessObjects.IUserDAO;
import dataAccessObjects.IUserDAO.DALException;
import dataTransferObjects.UserDTOSkabelon;
import staticClasses.Validator;
import staticClasses.Validator.InputException;

/**
 * The class DataVerifier implements the interface IDataVerifier.
 * The DataVerifier has the responsibility of making sure that the UserDTO contains valid information.
 * @author Group 22
 *
 */
public class DataVerifier implements IDataVerifier {

	//Instance variable.
	private IUserDAO data;

	/**
	 * Constructor
	 * @param data The Data Access Object to user.
	 */
	public DataVerifier(IUserDAO data) {
		this.data = data;
	}

	/**
	 * Creates a user and adds it to the data.
	 * @param user The user to be created.
	 */
	@Override
	public void createUser(UserDTOSkabelon user) throws WrongDataException {

		try {
			// if it returns an exception then the userID is not used
			data.getUser(user.getId());

			// throws an exception if the userID is taken
			throw new WrongDataException("This userID is already taken: " + user.getId());

		} catch (DALException userID) {

			// sets a newly generated password
			user.setPassword(generatePassword());

			// sets initials according to their username
			user.setIni(generateInitials(user.getName()));

			// validates if all the variables are legal
			validate(user);

			// creates the user.
			try {
				data.createUser(user);
			} catch (DALException e) {
				throw new WrongDataException(e.getMessage());
			}
		}
	}

	/**
	 * Delete user from data.
	 * @param user The user to be deleted.
	 */
	@Override
	public void deleteUser(int userId) throws WrongDataException {
		try {
			// if it returns an exception then the userID doesn't exist
			data.getUser(userId);

			try{
				data.deleteUser(userId);

			}catch(DALException e){
				throw new WrongDataException(e.getMessage());
			}

		} catch (DALException userID) {

			throw new WrongDataException("This userID is unknown: " + userId);
		}
	}

	/**
	 * Returns a user from the data.
	 * 
	 * @param userID
	 *            the user ID of the user that you want to get.
	 * @return The user to be returned.
	 */
	@Override
	public UserDTOSkabelon getUser(int userID) throws DALException {
		return data.getUser(userID);
	}
	
	/**
	 * Returns a list of all users.
	 * 
	 * @return users list.
	 */
	@Override
	public List<UserDTOSkabelon> getUserList() throws DALException {
		return data.getUserList();
	}

	/**
	 * Updates the data file if the UserDTO contains valid data.
	 * 
	 * @param user
	 *            The user to update.
	 * @throws WrongDataException
	 *             The exception to be thrown if the UserDTO contains invalid
	 *             data.
	 */
	@Override
	public void updateUser(UserDTOSkabelon user) throws WrongDataException {
		// validates if all the new data is legal
		validate(user);

		// Updates the user
		try {
			data.updateUser(user);
		} catch (DALException e) {
			throw new WrongDataException(e.getMessage());
		}
	}

	/**
	 * Checks if the UserDTO contains valid data.
	 * 
	 * @param user
	 *            The user to be validated.
	 * @throws WrongDataException
	 *             The exception to be thrown if the UserDTO contains invalid
	 *             data.
	 */
	private void validate(UserDTOSkabelon user) throws WrongDataException {
		// Validates if the username is legal
		try {
			Validator.validateUsername(user.getName());
		} catch (InputException e) {
			throw new WrongDataException(e.getMessage());
		}

		// Validates if the CPR is legal
		try {
			Validator.validateCPR(user.getCpr());
		} catch (InputException e) {
			throw new WrongDataException(e.getMessage());
		}
		try {
			Validator.validateRole(user.getRole());
		} catch (InputException e) {
			throw new WrongDataException(e.getMessage());
		}

		// Validates if the UserID is legal
		try {
			Validator.validateUserID(user.getId());
		} catch (InputException e) {
			throw new WrongDataException(e.getMessage());
		}

		// Validates if the password is legal
		try {
			Validator.validatePassword(user.getPassword());
		} catch (InputException e) {
			throw new WrongDataException(e.getMessage());
		}

		// Validates if the user initials is legal
		try {
			Validator.validateInitials(user.getIni());
		} catch (InputException e) {
			throw new WrongDataException(e.getMessage());
		}

	}

	/**
	 * Generates initials from a given name.
	 * 
	 * @param name
	 *            The name the initials needs to be created from.
	 * @return The generated initials.
	 */
	public String generateInitials(String name) {
		String[] nameParts = name.split(" ");
		String newIni = "";
		if (nameParts.length == 1) {
			newIni = nameParts[0].substring(0, 2);
		} else {
			int length = Math.min(nameParts.length, 4);
			for (int i = 0; i < length; i++) {
				newIni = newIni + nameParts[i].substring(0, 1);
			}
		}
		return newIni;
	}

	/**
	 * Generates a password for the userDTO accepting the rules of DTU
	 * passwords.
	 * 
	 * @return The generated password.
	 */
	public String generatePassword() {
		String password = "";
		int passLength = 8;
		boolean passwordValid = false;
		while (!passwordValid) {
			password = "";
			for (int i = 0; i < passLength; i++) {
				char newCharacther;
				int randGroup = (int) (Math.random() * 100);
				// Add a special characther
				if (randGroup < 5) {
					String specialCharacthers = ".-_+!?=";
					int rand = (int) (Math.random() * specialCharacthers.length());
					newCharacther = specialCharacthers.charAt(rand);
				}
				// Add a small letter.
				else if (randGroup < 30) {
					int rand = (int) (Math.random() * (122 - 97 + 1) + 97);
					newCharacther = (char) rand;
				}
				// Add a large letter.
				else if (randGroup < 55) {
					int rand = (int) (Math.random() * (90 - 65 + 1) + 65);
					newCharacther = (char) rand;
				}
				// Add a number.
				else {
					int rand = (int) (Math.random() * (57 - 48 + 1) + 48);
					newCharacther = (char) rand;
				}
				password += newCharacther + "";
			}
			try {
				Validator.validatePassword(password);
				passwordValid = true;
			} catch (InputException e) {
				// Catches invalid passwords and creates a new one.
			}
		}
		return password;
	}

}
