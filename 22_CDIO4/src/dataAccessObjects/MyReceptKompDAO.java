package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.ReceptKompDAO;
import dataTransferObjects.ReceptKompDTO;
import exceptions.DALException;

public class MyReceptKompDAO implements ReceptKompDAO {
	
	static Hashtable<DoubleInteger, ReceptKompDTO> recipeCompList = new Hashtable<DoubleInteger, ReceptKompDTO>();

	@Override
	public ReceptKompDTO getReceptKomp(int receptId, int raavareId) throws DALException {
		if(recipeCompList.get(new DoubleInteger(receptId, raavareId)) != null)
			return recipeCompList.get(new DoubleInteger(receptId, raavareId)).copy();

		else
			throw new DALException("Unknown Recipe Comp ID: " +new DoubleInteger(receptId, raavareId));
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList(int receptId) throws DALException {
		List<ReceptKompDTO> recipeComps = new ArrayList<ReceptKompDTO>();

		Set<DoubleInteger> keys = recipeCompList.keySet();

		for(DoubleInteger key : keys){
			if(recipeCompList.get(key).getReceptId() == receptId)
				recipeComps.add(recipeCompList.get(key).copy());
		}

		return recipeComps;
	}

	@Override
	public List<ReceptKompDTO> getReceptKompList() throws DALException {
		List<ReceptKompDTO> recipeComps = new ArrayList<ReceptKompDTO>();

		Set<DoubleInteger> keys = recipeCompList.keySet();

		for(DoubleInteger key : keys){
			recipeComps.add(recipeCompList.get(key).copy());
		}

		return recipeComps;
	}

	@Override
	public void createReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
		if (recipeCompList.putIfAbsent(new DoubleInteger(receptkomponent.getReceptId(), receptkomponent.getRaavareId()), receptkomponent.copy()) == null)
			return;

		else
			throw new DALException("Recipe Comp ID:"+new DoubleInteger(receptkomponent.getReceptId(), receptkomponent.getRaavareId())+" already exists !");

	}

	@Override
	public void updateReceptKomp(ReceptKompDTO receptkomponent) throws DALException {
		recipeCompList.replace(new DoubleInteger(receptkomponent.getReceptId(), receptkomponent.getRaavareId()), receptkomponent.copy());

	}

}
