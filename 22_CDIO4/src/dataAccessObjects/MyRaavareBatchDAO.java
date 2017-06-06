package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IWeightControlDAO;
import dataAccessObjects.interfaces.IRawMaterialBatchDAO;
import dataTransferObjects.IWeightControlDTO;
import dataTransferObjects.RawMaterialBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class MyRaavareBatchDAO implements IRawMaterialBatchDAO, IWeightControlDAO {

	static Hashtable<Integer, RawMaterialBatchDTO> rawmatBatchList = new Hashtable<Integer, RawMaterialBatchDTO>();

	@Override
	public RawMaterialBatchDTO getRawMaterialBatch(int rbId) throws DALException {
		if(rawmatBatchList.get(rbId) != null)
			return rawmatBatchList.get(rbId).copy();

		else
			throw new DALException("Unknown Raw Material Batch ID: " + rbId);
	}

	@Override
	public List<RawMaterialBatchDTO> getRawMaterialBatchList() throws DALException {
		List<RawMaterialBatchDTO> rawmatbs = new ArrayList<RawMaterialBatchDTO>();

		Set<Integer> keys = rawmatBatchList.keySet();

		for(Integer key : keys){
			rawmatbs.add(rawmatBatchList.get(key).copy());
		}

		return rawmatbs;
	}

	@Override
	public List<RawMaterialBatchDTO> getRawMaterialBatchList(int raavareId) throws DALException {
		List<RawMaterialBatchDTO> rawmatbs = new ArrayList<RawMaterialBatchDTO>();

		Set<Integer> keys = rawmatBatchList.keySet();

		for(Integer key : keys){
			if(rawmatBatchList.get(key).getRaavareId() == raavareId)
				rawmatbs.add(rawmatBatchList.get(key).copy());
		}

		return rawmatbs;
	}

	@Override
	public void createRawMaterialBatch(RawMaterialBatchDTO raavarebatch) throws DALException {
		if (rawmatBatchList.putIfAbsent(raavarebatch.getRbId(), raavarebatch.copy()) == null)
			return;
		
		else
			throw new CollisionException("Product Batch ID:"+raavarebatch.getRbId()+" already exists !");

	}

	@Override
	public void updateRawMaterialBatch(RawMaterialBatchDTO raavarebatch) throws DALException {
		rawmatBatchList.replace(raavarebatch.getRbId(), raavarebatch.copy());

	}

	@Override
	public IWeightControlDTO getDTOById(int Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
