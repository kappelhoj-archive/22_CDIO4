package dataAccessObjects;

import java.util.List;

import dataTransferObjects.ProduktBatchDTO;
import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.DALException;
public class TestSimonProduktBatchKompDAO implements dataAccessObjects.interfaces.ProduktBatchKompDAO {

	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO produktbatch) throws DALException {
		System.out.println(produktbatch.getPbId()+produktbatch.toString());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		// TODO Auto-generated method stub
		
	}


}
