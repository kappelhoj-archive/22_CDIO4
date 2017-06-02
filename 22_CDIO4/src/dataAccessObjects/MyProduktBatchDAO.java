package dataAccessObjects;

import java.util.List;

import dataAccessObjects.interfaces.ProduktBatchDAO;
import dataTransferObjects.ProduktBatchDTO;
import exceptions.DALException;

public class MyProduktBatchDAO implements ProduktBatchDAO {
	

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		// TODO Auto-generated method stub

	}

}
