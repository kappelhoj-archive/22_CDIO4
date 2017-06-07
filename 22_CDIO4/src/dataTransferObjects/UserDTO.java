package dataTransferObjects;

/**
 * Operatoer Data Access Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class UserDTO extends DTO implements IWeightControlDTO
{
	//Instance variables of the object UserDTO
	int id;                     
	String name;                
	String ini;                 
	String cpr;                 
	String password;    
	String role;

	public UserDTO() {
		
	}
	
	public UserDTO(int id, String name, String ini, String cpr, String password, String role)
	{
		this.id = id;
		this.name = name;
		this.ini = ini;
		this.cpr = cpr;
		this.password = password;
		this.role = role;
	}
	
    public UserDTO(UserDTO user)
    {
    	id = user.getId();
    	name = user.getName();
    	ini = user.getIni();
    	cpr = user.getCpr();
    	password = user.getPassword();
    	role = user.getRole();
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

	public String toString() { return id + "\t" + name + "\t" + ini + "\t" + cpr + "\t" + password + role; }


	public UserDTO copy() {
		
		return new UserDTO(id, name, ini, cpr, password, role);
		
	}

	@Override
	public String getIdentity() {
		return name;
	}

	@Override
	public void copy(IWeightControlDTO dto) throws RuntimeException {
		if(dto instanceof UserDTO){
			UserDTO castDTO=(UserDTO) dto;
			this.cpr=castDTO.getCpr();
			this.id=castDTO.getId();
			this.ini=castDTO.getIni();
			this.name=castDTO.getName();
			this.password=castDTO.getPassword();
			this.role=castDTO.getRole();
		}
		else{
			throw new RuntimeException("Invalid DTO");
		}
		
	}
}
