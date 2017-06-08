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
import staticClasses.FileManagement;
import staticClasses.FileManagement.TypeOfData;

public class RawMaterialBatchDAO implements IRawMaterialBatchDAO, IWeightControlDAO {

	static Hashtable<Integer, RawMaterialBatchDTO> rawmatBatchList = new Hashtable<Integer, RawMaterialBatchDTO>();
	
	@SuppressWarnings("unchecked")
	public RawMaterialBatchDAO(){
		try{
			System.out.println("Retrieving RawMaterialBatch Data...");
			rawmatBatchList = (Hashtable<Integer, RawMaterialBatchDTO>) FileManagement.retrieveData(TypeOfData.RAWMATERIALBATCH);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println(e);
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(rawmatBatchList, TypeOfData.RAWMATERIALBATCH);
			System.out.println("Done.");
		}
	}

	/**
	 * Method which returns a copy of a RawMaterialBatchDTO from the data
	 * @param rbId : rawmaterialbatchId
	 * @return RawMaterialBatchDTO
	 */
	@Override
	public RawMaterialBatchDTO getRawMaterialBatch(int rbId) throws DALException {
		if(rawmatBatchList.get(rbId) != null)
			return rawmatBatchList.get(rbId).copy();

		else
			throw new DALException("Unknown Raw Material Batch ID: " + rbId);
	}
	
	/**
	 * Method which returns a list of RawMaterialBatchDTOs from the data
	 * @return List<RawMaterialBatchDTO>
	 */
	@Override
	public List<RawMaterialBatchDTO> getRawMaterialBatchList() throws DALException {
		List<RawMaterialBatchDTO> rawmatbs = new ArrayList<RawMaterialBatchDTO>();

		Set<Integer> keys = rawmatBatchList.keySet();

		for(Integer key : keys){
			rawmatbs.add(rawmatBatchList.get(key).copy());
		}

		return rawmatbs;
	}

	 /* Method which returns a list of RawMaterialBatchDTOs from the data
	 * @param rawMaterialId
	 * @return List<RawMaterialBatchDTO>
	 */
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

	/**
	 * Method which adds a RawMaterialBatchDTO to the saved data
	 * @param RawMaterialBatchDTO
	 * @return void
	 */
	@Override
	public void createRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException {
		if (rawmatBatchList.putIfAbsent(rawMaterialBatch.getRbId(), rawMaterialBatch.copy()) == null){
			FileManagement.writeData(rawmatBatchList, TypeOfData.RAWMATERIALBATCH);
			return;
		}
		
		else
			throw new CollisionException("Product Batch ID:"+rawMaterialBatch.getRbId()+" already exists !");

	}

	/**
	 * Method which updates a RawMaterialBatchDTO in the saved data
	 * @param RawMaterialBatchDTO
	 * @return void
	 */
	@Override
	public void updateRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException {
		rawmatBatchList.replace(rawMaterialBatch.getRbId(), rawMaterialBatch.copy());
		FileManagement.writeData(rawmatBatchList, TypeOfData.RAWMATERIALBATCH);
	}

	@Override
	public IWeightControlDTO getDTOById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
