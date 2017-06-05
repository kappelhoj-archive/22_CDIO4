package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.ReceptDAO;
import dataTransferObjects.ReceptDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class MyReceptDAO implements ReceptDAO {
	
	static Hashtable<Integer, ReceptDTO> recipeList = new Hashtable<Integer, ReceptDTO>();

	@Override
	public ReceptDTO getRecept(int receptId) throws DALException {
		if(recipeList.get(receptId) != null)
			return recipeList.get(receptId).copy();
	
		else
			throw new DALException("Unknown Recipe ID: " + receptId);
	}

	@Override
	public List<ReceptDTO> getReceptList() throws DALException {
		List<ReceptDTO> recipes = new ArrayList<ReceptDTO>();

		Set<Integer> keys = recipeList.keySet();
		
		for(Integer key : keys){
			recipes.add(recipeList.get(key).copy());
		}

		return recipes;
	}

	@Override
	public void createRecept(ReceptDTO recept) throws DALException {
		if (recipeList.putIfAbsent(recept.getReceptId(), recept.copy()) == null)
			return;
		
		else
			throw new CollisionException("Recpipe ID:"+recept.getReceptId()+" already exists !");

	}

	@Override
	public void updateRecept(ReceptDTO recept) throws DALException {
		recipeList.replace(recept.getReceptId(), recept.copy());

	}

}
