package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.RaavareDAO;
import dataTransferObjects.RaavareDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class MyRaavareDAO implements RaavareDAO {
	
	static Hashtable<Integer, RaavareDTO> rawmatList = new Hashtable<Integer, RaavareDTO>();

	@Override
	public RaavareDTO getRaavare(int raavareId) throws DALException {
		if(rawmatList.get(raavareId) != null)
			return rawmatList.get(raavareId).copy();
	
		else
			throw new DALException("Unknown Raw Material ID: " + raavareId);
	}

	@Override
	public List<RaavareDTO> getRaavareList() throws DALException {
		List<RaavareDTO> rawmats = new ArrayList<RaavareDTO>();

		Set<Integer> keys = rawmatList.keySet();
		
		for(Integer key : keys){
			rawmats.add(rawmatList.get(key).copy());
		}

		return rawmats;
	}

	@Override
	public void createRaavare(RaavareDTO raavare) throws DALException {
		if (rawmatList.putIfAbsent(raavare.getRaavareId(), raavare.copy()) == null)
			return;
		
		else
			throw new CollisionException("Raw Material ID:"+raavare.getRaavareId()+" already exists !");

	}

	@Override
	public void updateRaavare(RaavareDTO raavare) throws DALException {
		rawmatList.replace(raavare.getRaavareId(), raavare.copy());

	}

}
