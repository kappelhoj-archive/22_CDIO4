package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IRawMaterialDAO;
import dataTransferObjects.RawMaterialDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class MyRaavareDAO implements IRawMaterialDAO {
	
	static Hashtable<Integer, RawMaterialDTO> rawmatList = new Hashtable<Integer, RawMaterialDTO>();

	@Override
	public RawMaterialDTO getRawMaterial(int raavareId) throws DALException {
		if(rawmatList.get(raavareId) != null)
			return rawmatList.get(raavareId).copy();
	
		else
			throw new DALException("Unknown Raw Material ID: " + raavareId);
	}

	@Override
	public List<RawMaterialDTO> getRawMaterialList() throws DALException {
		List<RawMaterialDTO> rawmats = new ArrayList<RawMaterialDTO>();

		Set<Integer> keys = rawmatList.keySet();
		
		for(Integer key : keys){
			rawmats.add(rawmatList.get(key).copy());
		}

		return rawmats;
	}

	@Override
	public void createRawMaterial(RawMaterialDTO raavare) throws DALException {
		if (rawmatList.putIfAbsent(raavare.getId(), raavare.copy()) == null)
			return;
		
		else
			throw new CollisionException("Raw Material ID:"+raavare.getId()+" already exists !");

	}

	@Override
	public void updateRawMaterial(RawMaterialDTO raavare) throws DALException {
		rawmatList.replace(raavare.getId(), raavare.copy());

	}

}
