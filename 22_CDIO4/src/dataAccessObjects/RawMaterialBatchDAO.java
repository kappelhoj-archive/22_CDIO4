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

public class RawMaterialBatchDAO implements IRawMaterialBatchDAO, IWeightControlDAO {

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
	public List<RawMaterialBatchDTO> getRawMaterialBatchList(int rawMaterialId) throws DALException {
		List<RawMaterialBatchDTO> rawmatbs = new ArrayList<RawMaterialBatchDTO>();

		Set<Integer> keys = rawmatBatchList.keySet();

		for(Integer key : keys){
			if(rawmatBatchList.get(key).getRawMaterialId() == rawMaterialId)
				rawmatbs.add(rawmatBatchList.get(key).copy());
		}

		return rawmatbs;
	}

	@Override
	public void createRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException {
		if (rawmatBatchList.putIfAbsent(rawMaterialBatch.getRbId(), rawMaterialBatch.copy()) == null)
			return;
		
		else
			throw new CollisionException("Product Batch ID:"+rawMaterialBatch.getRbId()+" already exists !");

	}

	@Override
	public void updateRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException {
		rawmatBatchList.replace(rawMaterialBatch.getRbId(), rawMaterialBatch.copy());

	}

	@Override
	public IWeightControlDTO getDTOById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
