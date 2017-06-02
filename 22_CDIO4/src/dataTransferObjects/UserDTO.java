package dataTransferObjects;

import java.io.Serializable;

/**
 * The objects of the UserDTO is used as data transfer object. The class
 * contains methods to add or remove information from the data transfer object.
 * In addition the class also contains a few utility methods.
 * 
 * @author Group 22
 *
 */
public class UserDTO implements Serializable {

	// The serial id making us able to identify the object when saved and
	// loaded.
	private static final long serialVersionUID = 4545864587995944260L;

	// Instance variables of the object UserDTO
	private int id;
	private String name;
	private String ini;
	private String cpr;
	private String password;
	private String role;

	
	public UserDTO(UserDTO user){
		this.id = user.id;
		this.name = user.name;
		this.ini = user.ini;
		this.cpr = user.cpr;
		this.password = user.password;
		this.role = user.role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIni() {
		return ini;
	}

	public void setIni(String ini) {
		this.ini = ini;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}
	
	public void setRole(String role){
		this.role = role;
	}

	/**
	 * Checks if the userDTO has the given role.
	 * 
	 * @param role
	 *            The role to be checked.
	 * @return True if the userDTO has the role, false otherwise.
	 */
	public boolean hasRole(String role) {
		return this.role.equals(role);
	}

	/**
	 * Creates a string representation of the object without showing the
	 * password and CPR of the userDTO.
	 */
	@Override
	public String toString() {
		String newString = "ID: " + id + ", name: " + name + ", Initials: " + ini + ", Role: " + role;

		return newString;
	}
	
	/**
	 * Checks if the given user is equal to this user.
	 * 
	 * @param user
	 *            The user to be checked.
	 * @return True if the given user is equal to this user, false otherwise.
	 */
	public boolean equals(UserDTO user) {

		if (this.getId() != user.getId()) {
			return false;
		} else if (!this.getName().equals(user.getName())) {
			return false;
		} else if (!this.getIni().equals(user.getIni())) {
			return false;
		} else if (!this.getCpr().equals(user.getCpr())) {
			return false;
		} else if (!this.getPassword().equals(user.getPassword())) {
			return false;
		} else if (!this.getRole().equals(user.getRole())) {
			return false;
		}
		return true;
	}
}
