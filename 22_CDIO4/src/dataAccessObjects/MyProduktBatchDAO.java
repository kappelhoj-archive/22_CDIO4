package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.ProduktBatchDAO;
import dataTransferObjects.ProduktBatchDTO;
import exceptions.DALException;

public class MyProduktBatchDAO implements ProduktBatchDAO {

	static Hashtable<Integer, ProduktBatchDTO> productBatchList = new Hashtable<Integer, ProduktBatchDTO>();

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		
		if(productBatchList.get(pbId) != null)
			return productBatchList.get(pbId).copy();
	
		else
			throw new DALException("Unknown Product Batch ID: " + pbId);
		
	}
	

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		List<ProduktBatchDTO> productbs = new ArrayList<ProduktBatchDTO>();

		Set<Integer> keys = productBatchList.keySet();
		
		for(Integer key : keys){
			productbs.add(productBatchList.get(key).copy());
		}

		return productbs;
	}
	

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		if (productBatchList.putIfAbsent(produktbatch.getPbId(), produktbatch.copy()) == null)
			return;
		
		else
			throw new DALException("Product Batch ID:"+produktbatch.getPbId()+" already exists !");

	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		productBatchList.replace(produktbatch.getPbId(), produktbatch.copy());

	}

}
