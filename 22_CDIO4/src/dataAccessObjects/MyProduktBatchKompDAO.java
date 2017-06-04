package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.ProduktBatchKompDAO;
import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.DALException;

public class MyProduktBatchKompDAO implements ProduktBatchKompDAO {
	
	static Hashtable<Integer, ProduktBatchKompDTO> productBatchCompList = new Hashtable<Integer, ProduktBatchKompDTO>();

	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {
		
		if(productBatchCompList.get(rbId) != null)
			return productBatchCompList.get(rbId).copy();
	
		else
			throw new DALException("Unknown Product Batch Comp ID: " + rbId);
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		List<ProduktBatchKompDTO> productbsc = new ArrayList<ProduktBatchKompDTO>();

		Set<Integer> keys = productBatchCompList.keySet();
		
		for(Integer key : keys){
			productbsc.add(productBatchCompList.get(key).copy());
		}

		return productbsc;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		if (productBatchCompList.putIfAbsent(produktbatchkomponent.getPbId(), produktbatch.copy()) == null)
			return;
		
		else
			throw new DALException("Product Batch ID:"+produktbatch.getPbId()+" already exists !");


	}

	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		// TODO Auto-generated method stub

	}

}
