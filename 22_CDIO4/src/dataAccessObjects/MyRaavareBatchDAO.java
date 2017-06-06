package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IWeightControlDAO;
import dataAccessObjects.interfaces.RaavareBatchDAO;
import dataTransferObjects.IWeightControlDTO;
import dataTransferObjects.RaavareBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class MyRaavareBatchDAO implements RaavareBatchDAO, IWeightControlDAO {

	static Hashtable<Integer, RaavareBatchDTO> rawmatBatchList = new Hashtable<Integer, RaavareBatchDTO>();

	@Override
	public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
		if(rawmatBatchList.get(rbId) != null)
			return rawmatBatchList.get(rbId).copy();

		else
			throw new DALException("Unknown Raw Material Batch ID: " + rbId);
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		List<RaavareBatchDTO> rawmatbs = new ArrayList<RaavareBatchDTO>();

		Set<Integer> keys = rawmatBatchList.keySet();

		for(Integer key : keys){
			rawmatbs.add(rawmatBatchList.get(key).copy());
		}

		return rawmatbs;
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
		List<RaavareBatchDTO> rawmatbs = new ArrayList<RaavareBatchDTO>();

		Set<Integer> keys = rawmatBatchList.keySet();

		for(Integer key : keys){
			if(rawmatBatchList.get(key).getRaavareId() == raavareId)
				rawmatbs.add(rawmatBatchList.get(key).copy());
		}

		return rawmatbs;
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		if (rawmatBatchList.putIfAbsent(raavarebatch.getRbId(), raavarebatch.copy()) == null)
			return;
		
		else
			throw new CollisionException("Product Batch ID:"+raavarebatch.getRbId()+" already exists !");

	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		rawmatBatchList.replace(raavarebatch.getRbId(), raavarebatch.copy());

	}

	@Override
	public IWeightControlDTO getDTOById(int Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
