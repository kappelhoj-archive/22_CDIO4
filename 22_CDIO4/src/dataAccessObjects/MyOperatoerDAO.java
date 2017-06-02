package dataAccessObjects;

import java.util.List;

import java.util.ArrayList;

import exceptions.DALException;
//import staticClasses.FileManagement;
import dataAccessObjects.interfaces.OperatoerDAO;
import dataTransferObjects.DTO;
import dataTransferObjects.OperatoerDTO;


public class MyOperatoerDAO implements OperatoerDAO {

	static List<DTO> userList;

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

		userList = new ArrayList<DTO>();
	}

	// New method calls - not yet tested
	public OperatoerDTO getOperatoer(int oprId) throws DALException {

		for(DTO user : userList){
			OperatoerDTO opr = (OperatoerDTO) user.copy();

			if(opr.getOprId() == oprId){
				return opr;
			}
		}

		throw new DALException("Can not find " + oprId);

	}

	// New method calls - not yet tested
	public void createOperatoer(OperatoerDTO opr) throws DALException {

			userList.add(opr.copy());


		//FileManagement.saveData(userList, "operatoer");

	}

	public void updateOperatoer(OperatoerDTO opr) throws DALException {
		userList.remove(getOperatoer(opr.getOprId()));
		userList.add(opr.copy());
		//FileManagement.saveData(userList, "operatoer");
	}

	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> users = new ArrayList<OperatoerDTO>();

		for(DTO user : userList)
			users.add((OperatoerDTO) user);

		return users;
	}

}
