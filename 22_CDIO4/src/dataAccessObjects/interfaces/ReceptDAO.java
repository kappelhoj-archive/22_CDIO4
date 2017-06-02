package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.ReceptDTO;
import exceptions.DALException;


public interface ReceptDAO {
	ReceptDTO getRecept(int receptId) throws DALException;
	List<ReceptDTO> getReceptList() throws DALException;
	void createRecept(ReceptDTO recept) throws DALException;
	void updateRecept(ReceptDTO recept) throws DALException;
}
