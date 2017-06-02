package controller;

import java.util.ArrayList;
import java.util.Hashtable;

import controller.interfaces.ILoginController;
import dataTransferObjects.DTO;
import dataTransferObjects.OperatoerDTO;
import exceptions.DALException;
import staticClasses.FileManagement;

public class LoginController implements ILoginController {

	Hashtable<Integer, Integer> adminKeyTable = new Hashtable<Integer, Integer>();

	public LoginController(){

	}

	@Override
	public boolean checkLogin(int id, String password) {
		try{
			if (adminKeyTable.remove(id) == Integer.parseInt(password))
				return true;
			else
				return false;

		}catch(Exception e){
			try{
				ArrayList<DTO> data = FileManagement.retrieveFrom("operatoer");
				
				for(DTO user : data){
					if(((OperatoerDTO) user).getOprId() == id && ((OperatoerDTO) user).getPassword() == password)
						return true;
				}
				
				return false;
				
			}catch(DALException e2){
				return false;
			}
		}
	}


	@Override
	public int generateAdminKey(int id) {
		Integer key = new Integer((int) Math.floor(Math.random()*10000));
		adminKeyTable.put(id, key);
		return key;
	}

}
