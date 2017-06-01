package dataTransferObjects;

import java.io.Serializable;

/**
 * The objects of the LoginPOJO is used as data transfer object. The class
 * contains methods to add or remove information from the data transfer object.
 * In addition the class also contains a "isSuperAdmin" method to know if this is a correct fast login.
 * 
 * @author Group 22
 *
 */
public class LoginPOJO implements Serializable {

	// The serial id making us able to identify the object when saved and
	// loaded.
	private static final long serialVersionUID = 4545864587995999260L;

	// Instance variables of the object UserDTO
	private String userName;
	private String password;

	
	public LoginPOJO() {
		
	}

	public LoginPOJO(String username, String password) {
		this.userName=username;
		this.password=password;
	}

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isSuperAdmin(){
		if(userName.equals("root") && password.equals("test"))
			return true;
		
		return false;
	}
	
	public String toString(){
		return "[username: " + userName + ", password: " + password + "]";
	}

}
