package staticClasses;

import java.util.List;

/**
 * The Validator class only contains static methods and is used to check if the
 * user input is valid. The class also contains the inner class InputException
 * which is an exception class. The exception is thrown if the user input
 * something wrong.
 * 
 * @author Group 22
 *
 */
public class Validator {

	// Constant of the class. Contains the valid roles in the system
	public static final String[] validRoles = new String[] { "Admin", "Pharmacist", "Foreman", "Operator" };

	/**
	 * Checks if the given userID has the valid size. It does not check if it is
	 * in the database.
	 * 
	 * @param userID
	 *            The userID to be checked.
	 * @return True if the userID is valid, throws InputException otherwise.
	 * @throws InputException
	 *             The exception to be thrown if the given userID is not valid.
	 */
	public static boolean validateUserID(int userID) throws InputException {
		if (userID < 11 || userID > 99) {
			throw new InputException("This user id is invalid. User ID's has to be between 11 and 99");
		}
		return true;
	}

	/**
	 * Checks if the given username has the valid size.
	 * 
	 * @param username
	 *            The username to be checked.
	 * @return True if the username is valid, throws InputException otherwise.
	 * @throws InputException
	 *             The exception to be thrown if the given username is not
	 *             valid.
	 */
	public static boolean validateUsername(String username) throws InputException {
		if (username == null) {
			throw new InputException("Username can't be null");
		}

		if (username.length() < 2 || username.length() > 20) {
			throw new InputException(
					"This username is invalid. Usernames has to be between 2 and 20 characthers long.");
		}
		return true;
	}

	/**
	 * Checks if the given initials has the valid size.
	 * 
	 * @param ini
	 *            The initials to be checked.
	 * @return True if the initials are valid, throws InputException otherwise.
	 * @throws InputException
	 *             The exception to be thrown if the given initials is not
	 *             valid.
	 */
	public static boolean validateInitials(String ini) throws InputException {
		if (ini == null) {
			throw new InputException("initials can't be null");
		}

		if (ini.length() < 2 || ini.length() > 4) {
			throw new InputException(
					"These initials are invalid. Initials has to be between 2 and 4 characthers long.");
		}
		return true;
	}

	/**
	 * Checks if the given CPR is a valid CPR as we know it the CPR in Denmark.
	 * 
	 * @param cpr
	 *            The CPR to be checked.
	 * @return True if the CPR is valid, throws InputException otherwise.
	 * @throws InputException
	 *             The exception to be thrown if the given CPR is not valid.
	 */
	public static boolean validateCPR(String cpr) throws InputException {
		if (cpr == null) {
			throw new InputException("cpr can't be null");
		}

		int[] validationNumber = new int[] { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
		int[] cprArray = new int[10];

		if (cpr.length() != 10) {
			throw new InputException("The cpr number has to have 10 characthers.");
		}

		// Save the cpr number in an array.
		for (int i = 0; i < 10; i++) {
			try {
				cprArray[i] = Integer.parseInt(cpr.substring(i, i + 1));
			} catch (NumberFormatException e) {
				throw new InputException("Cpr number can only contain numbers.");
			}
		}
		if (cprArray[0] > 3 || (cprArray[0] == 3 && cprArray[1] > 1)) {
			throw new InputException(cprArray[0] + "" + cprArray[1] + " is not a valid date.");
		}
		if ((cprArray[2] > 1 || (cprArray[2] > 0 && cprArray[3] > 2))) {
			throw new InputException(cprArray[2] + "" + cprArray[3] + " is not a valid month.");
		}

		// Calculate the validation number:
		int sum = 0;

		for (int i = 0; i < 9; i++) {
			sum += cprArray[i] * validationNumber[i];
		}

		int remainder = (sum % 11);

		if (remainder == 1) {
			throw new InputException("The cpr number is invalid.");
		}

		int controlCifre = 11 - remainder;

		if (controlCifre != cprArray[9]) {
			throw new InputException("The cpr number is invalid.");
		}
		return true;
	}

	/**
	 * Checks if the given password is valid. A password is valid if it follows
	 * the requirements that DTU has set.
	 * 
	 * @param password
	 *            The password to be checked.
	 * @return True if the password is valid, throws InputException otherwise.
	 * @throws InputException
	 *             The exception to be thrown if the given password is not
	 *             valid.
	 */
	public static boolean validatePassword(String password) throws InputException {
		if (password == null) {
			throw new InputException("Password can't be null");
		}

		boolean smallLetterFlag = false;
		boolean bigLetterFlag = false;
		boolean numberFlag = false;
		boolean specialCharFlag = false;
		int groupCount = 0;
		char currentChar;
		int charValue;

		if (password.length() < 6) {
			throw new InputException("This password is too short.");
		}
		for (int i = 0; i < password.length(); i++) {
			currentChar = password.charAt(i);
			charValue = (int) currentChar;

			// Is the char a small letter?
			if (charValue >= 97 && charValue <= 122) {
				if (smallLetterFlag == false) {
					groupCount++;
					smallLetterFlag = true;
				}
			}
			// Is the char a big letter?
			else if (charValue >= 65 && charValue <= 90) {
				if (bigLetterFlag == false) {
					groupCount++;
					bigLetterFlag = true;
				}
			}
			// is the char a number?
			else if (charValue >= 48 && charValue <= 57) {
				if (numberFlag == false) {
					groupCount++;
					numberFlag = true;
				}
			}
			// Is the char an allowed special character.
			else if (currentChar == '.' || currentChar == '-' || currentChar == '_' || currentChar == '+'
					|| currentChar == '!' || currentChar == '?' || currentChar == '=') {
				if (specialCharFlag == false) {
					groupCount++;
					specialCharFlag = true;
				}
			}
			// If the char is not allowed.
			else {
				throw new InputException("'" + currentChar + "' is not a valid characther.");
			}
		}
		// If the password dosen't contain chars from atleast 3 groups.
		if (groupCount < 3) {
			throw new InputException(
					"This password does not contain characthers from 3 different groups.\n The groups are: 'a-z' , 'A-Z','0-9', {'.', '-', '_', '+', '!', '?', '='}");
		}
		return true;
	}

	/**
	 * Checks if the given role is a valid role.
	 * 
	 * @param role
	 *            The role to be checked.
	 * @return True if the role is valid, throws InputException otherwise.
	 * @throws InputException
	 *             The exception to be thrown if the given role is not valid.
	 */
	public static boolean validateRole(String role) throws InputException {
		if (role == null) {
			throw new InputException("Roles can't be null");
		}

		for (int i = 0; i < validRoles.length; i++) {
			if (role.equals(validRoles[i])) {
				return true;
			}
		}
		throw new InputException("This is not a valid role.");
	}

	/**
	 * Checks if the given role is a valid role.
	 * 
	 * @param role
	 *            The role to be checked.
	 * @return True if the role is valid, throws InputException otherwise.
	 * @throws InputException
	 *             The exception to be thrown if the given role is not valid.
	 */
	public static boolean validateRoles(List<String> roles) throws InputException {
		if (roles == null) {
			throw new InputException("Roles can't be null");
		}
		boolean equalFlag = false;
		for (int i = 0; i < roles.size(); i++) {
			for (int j = 0; j < validRoles.length; j++) {
				if (roles.get(i).equals(validRoles[j])) {
					equalFlag = true;
					break;
				}
			}
			if (equalFlag == false) {
				throw new InputException("Role " + roles.get(i) + " was not valid");
			}
			equalFlag = false;
		}
		return true;
	}

	/**
	 * The InputException class is an exception inner class used in the
	 * Validator class. The exception is thrown if the user inputs something
	 * other than told.
	 * 
	 * @author Group 22
	 *
	 */
	public static class InputException extends Exception {
		// The serial id making us able to identify the object when saved and
		// loaded.
		private static final long serialVersionUID = 7884927058176762594L;

		/**
		 * Constructor.
		 * 
		 * @param msg
		 *            The error message.
		 */
		public InputException(String msg) {
			super(msg);
		}
	}
}
