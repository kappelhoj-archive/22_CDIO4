package dataAccessObjects;

import java.util.List;

import java.util.ArrayList;

import exceptions.DALException;
import staticClasses.FileManagement;
import dataAccessObjects.interfaces.OperatoerDAO;
import dataTransferObjects.DTO;
import dataTransferObjects.OperatoerDTO;

public class MySQLOperatoerDAO implements OperatoerDAO {

	// New method calls - not yet tested
	public OperatoerDTO getOperatoer(int oprId) throws DALException {

		ArrayList<DTO> data = FileManagement.retrieveFrom("operatoer");

		for(DTO user : data){
			OperatoerDTO opr = (OperatoerDTO) user;

			if(opr.getOprId() == oprId)
				return opr;
		}

		throw new DALException("Can not find " + oprId);

	}

	// New method calls - not yet tested
	public void createOperatoer(OperatoerDTO opr) throws DALException {

	}

	public void updateOperatoer(OperatoerDTO opr) throws DALException {

	}

	public List<OperatoerDTO> getOperatoerList() throws DALException {
		return null;

	}

}
