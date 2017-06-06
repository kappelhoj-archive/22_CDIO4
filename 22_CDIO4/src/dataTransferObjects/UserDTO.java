package dataTransferObjects;

/**
 * Operatoer Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class UserDTO implements IWeightControlDTO
{
	//Instance variables of the object UserDTO
	int id;                     
	String name;                
	String ini;                 
	String cpr;                 
	String password;    
	String role;

	public UserDTO(int oprId, String oprNavn, String ini, String cpr, String password, String rolle)
	{
		this.id = oprId;
		this.name = oprNavn;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.role = rolle;
	}
	
    public UserDTO(UserDTO opr)
    {
    	this.id = opr.getId();
    	this.name = opr.getName();
    	this.ini = opr.getIni();
    	this.cpr = opr.getCpr();
    	this.password = opr.getPassword();
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

	public void setRole(String role) {
		this.role = role;
	}

	public String toString() { return id + "\t" + name + "\t" + ini + "\t" + cpr + "\t" + password; }


	public UserDTO copy() {
		
		return new UserDTO(id, name, ini, cpr, password, role);
		
	}

	@Override
	public String getIdentity() {
		return name;
	}
}
