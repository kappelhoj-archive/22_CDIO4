package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IRawMaterialDAO;
import dataTransferObjects.RawMaterialDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class RawMaterialDAO implements IRawMaterialDAO {
	
	static Hashtable<Integer, RawMaterialDTO> rawMaterialList = new Hashtable<Integer, RawMaterialDTO>();

	@Override
	public RawMaterialDTO getRawMaterial(int rawMaterialId) throws DALException {
		if(rawMaterialList.get(rawMaterialId) != null)
			return rawMaterialList.get(rawMaterialId).copy();
	
		else
			throw new DALException("Unknown Raw Material ID: " + rawMaterialId);
	}

	@Override
	public List<RawMaterialDTO> getRawMaterialList() throws DALException {
		List<RawMaterialDTO> rawmaterials = new ArrayList<RawMaterialDTO>();

		Set<Integer> keys = rawMaterialList.keySet();
		
		for(Integer key : keys){
			rawmaterials.add(rawMaterialList.get(key).copy());
		}

		return rawmaterials;
	}

	@Override
	public void createRawMaterial(RawMaterialDTO raavare) throws DALException {
		if (rawMaterialList.putIfAbsent(raavare.getId(), raavare.copy()) == null)
			return;
		
		else
			throw new CollisionException("Raw Material ID:"+raavare.getId()+" already exists !");

	}

	@Override
	public void updateRawMaterial(RawMaterialDTO raavare) throws DALException {
		rawMaterialList.replace(raavare.getId(), raavare.copy());

	}

}
