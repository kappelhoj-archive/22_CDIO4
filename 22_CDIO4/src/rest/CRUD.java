//package rest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//
//import controller.Controller;
//import dataAccessObjects.IUserDAO.DALException;
//import dataTransferObjects.LoginPOJO;
//import dataTransferObjects.UserDTO;
//import functionLayer.IDataVerifier.WrongDataException;
//
//@Path("CRUD")
//public class CRUD {
//
//	@Path("create-user")
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public boolean createUser(UserDTO user) {
//		try {
//
//			while (true) {
//				int id = 0;
//				try {
//					id = (int) (Math.random() * 89 + 11);
//					Controller.func.getUser(id);
//				} catch (DALException e) {
//					user.setId(id);
//					break;
//				}
//			}
//
//			Controller.func.createUser(user);
//		} catch (WrongDataException e) {
//
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//	
//	@Path("delete-user")
//	@DELETE
//	@Consumes(MediaType.APPLICATION_JSON)
//	public boolean deleteUser(String userId) {
//		try {
//			int user_id = Integer.parseInt(userId);
//			Controller.func.deleteUser(user_id);;
//		} catch (WrongDataException e) {
//			e.printStackTrace();
//			return false;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
//	@Path("edit-user")
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	public boolean editUser(UserDTO user) {
//		try {
//			UserDTO sameUser = Controller.func.getUser(user.getId());
//			
//			//If password has not been changed.
//			if (user.getPassword().length() < 1) {
//				
//				user.setPassword(sameUser.getPassword());
//
//			}
//			Controller.func.updateUser(user);
//
//		} catch (WrongDataException | DALException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}
//
//	@Path("get-user")
//	@POST 
//	@Produces("application/json")
//	public UserDTO getUser(String userId) {
//		int id = Integer.parseInt(userId);
//		UserDTO user;
//		UserDTO transfer;
//		try {
//			user = Controller.func.getUser(id);
//			transfer = new UserDTO(user);
//			transfer.setPassword("");
//		} catch (DALException e) {
//			e.printStackTrace();
//			return null;
//		}
//		
//		return transfer;
//		
//	}
//
//	@Path("get-users")
//	@GET
//	@Produces("application/json")
//	public List<UserDTO> getUsers() {
//		List<UserDTO> users;
//		List<UserDTO> transfer = new ArrayList<UserDTO>();
//		System.out.println("Retriving all users");
//
//		try {
//			users = Controller.func.getUserList();
//			for (int i = 0; i < users.size(); i++) {
//				transfer.add(new UserDTO(users.get(i)));
//			}
//
//			for (int i = 0; i < transfer.size(); i++) {
//				transfer.get(i).setPassword("");
//			}
//
//		} catch (DALException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return transfer;
//		
//	}
//	
//	@Path("login-user")
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public boolean loginUser(LoginPOJO login) {
//		System.out.println(login);
//		if(login.isSuperAdmin())
//			
//			return true;
//		
//		else
//			return false;
//
//	}
//}
