package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IRawMaterialDAO;
import dataTransferObjects.RawMaterialDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import staticClasses.FileManagement;
import staticClasses.FileManagement.TypeOfData;

public class RawMaterialDAO implements IRawMaterialDAO {
	
	static Hashtable<Integer, RawMaterialDTO> rawMaterialList = new Hashtable<Integer, RawMaterialDTO>();
	
	@SuppressWarnings("unchecked")
	public RawMaterialDAO(){
		try{
			System.out.println("Retrieving RawMaterial Data...");
			rawMaterialList = (Hashtable<Integer, RawMaterialDTO>) FileManagement.retrieveData(TypeOfData.RAWMATERIAL);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println(e);
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(rawMaterialList, TypeOfData.RAWMATERIAL);
			System.out.println("Done.");
		}
	}

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
		if (rawMaterialList.putIfAbsent(raavare.getId(), raavare.copy()) == null){
			FileManagement.writeData(rawMaterialList, TypeOfData.RAWMATERIAL);
			return;
		}
		
		else
			throw new CollisionException("Raw Material ID:"+raavare.getId()+" already exists !");

	}

	@Override
	public void updateRawMaterial(RawMaterialDTO raavare) throws DALException {
		rawMaterialList.replace(raavare.getId(), raavare.copy());
		FileManagement.writeData(rawMaterialList, TypeOfData.RAWMATERIAL);

	}

}
