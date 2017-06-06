package dataAccessObjects;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Hashtable;

import exceptions.CollisionException;
import exceptions.DALException;
import dataAccessObjects.interfaces.IWeightControlDAO;
//import staticClasses.FileManagement;
import dataAccessObjects.interfaces.IUserDAO;
import dataTransferObjects.IWeightControlDTO;
import dataTransferObjects.OperatoerDTO;


public class MyOperatoerDAO implements IUserDAO,IWeightControlDAO {

	static Hashtable<Integer, OperatoerDTO> userList = new Hashtable<Integer, OperatoerDTO>();

	public MyOperatoerDAO(){
		//try{
		//	userList = FileManagement.retrieveFrom("operatoer");
		//}catch (DALException e){
		//	try {
		//		FileManagement.saveData(new ArrayList<DTO>(), "operatoer");
		//	} catch (DALException e1) {
		//		System.out.println(e1);
		//	}
		//}

		//userList = new ArrayList<DTO>();
	}

	
	public OperatoerDTO getOperatoer(int oprId) throws DALException {

		if(userList.get(oprId) != null)
			return userList.get(oprId).copy();
	
		else
			throw new DALException("Unknown UserID: " + oprId);

	}


	public void createOperatoer(OperatoerDTO opr) throws DALException {
		if (userList.putIfAbsent(opr.getOprId(), opr.copy()) == null)
			return;
		
		else
			throw new CollisionException("User ID:"+opr.getOprId()+" already exists !");

		//FileManagement.saveData(userList, "operatoer");

	}

	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		userList.replace(opr.getOprId(), opr.copy());
		//FileManagement.saveData(userList, "operatoer");
	}
	

	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> users = new ArrayList<OperatoerDTO>();

		Set<Integer> keys = userList.keySet();
		
		for(Integer key : keys){
			users.add(userList.get(key).copy());
		}

		return users;
	}


	@Override
	public IWeightControlDTO getDTOById(int Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
