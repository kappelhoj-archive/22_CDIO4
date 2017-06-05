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
	static Map<Long, UserCRUD> users = new HashMap<Long, UserCRUD>();
	static Long nextId = 10L;
	static {
		nextId++;
		users.put(nextId, new UserCRUD(nextId, "simon", "Simon Engquist", "SEN", "testCPR", "Operator"));
		nextId++;
		users.put(nextId, new UserCRUD(nextId, "jeppe", "Jeppe TN", "JTN", "testCPR", "Admin"));
	}
	
	@Path("get-users")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Long, UserCRUD> getUsers() {
		return users;
	}
	
	@Path("get-user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public UserCRUD getUser(String userId) {
		return users.get(Long.parseLong(userId));
	}
	
	@Path("create-user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createUser(UserCRUD u) {
		nextId++;
		users.put(nextId, new UserCRUD(nextId, u));
		return true;
	}
	
	@Path("edit-user")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean editUser(UserCRUD u) {
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
