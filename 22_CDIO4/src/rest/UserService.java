package rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	static Map<Long, User> users = new HashMap<Long, User>();
	static Long nextId = 10L;
	static {
		nextId++;
		users.put(nextId, new User(nextId, "simon", "Simon Engquist", "SEN", "testCPR", "Operator"));
		nextId++;
		users.put(nextId, new User(nextId, "jeppe", "Jeppe TN", "JTN", "testCPR", "Admin"));
	}
	
	@Path("get-users")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Long, User> getUsers() {
		return users;
	}
	
	@Path("get-user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public User getUser(String userId) {
		return users.get(Long.parseLong(userId));
	}
	
	@Path("create-user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createUser(User u) {
		nextId++;
		users.put(nextId, new User(nextId, u));
		return true;
	}
	
	@Path("edit-user")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean editUser(User u) {
		users.put(u.getId(), u);
		return true;
	}
	
	@Path("delete-user")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteUser(String userId) {
		users.remove(Long.parseLong(userId));
		return true;
	}
}

class User {
	private Long id;
	private String username;
	private String name;
	private String initials;
	private String cpr;
	private String roles;
	
	public User() {
		
	}
	
	public User(Long id, User u) {
		this.id = id;
		username = u.username;
		name = u.name;
		initials = u.initials;
		cpr = u.cpr;
		roles = u.roles;
	}
	
	public User(Long id, String username, String name, String initials, String cpr, String roles) 
	{
		this.id = id;
		this.username = username;
		this.name = name;
		this.initials = initials;
		this.cpr = cpr;
		this.roles = roles;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}
