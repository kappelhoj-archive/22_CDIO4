package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataTransferObjects.RecipeCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class MyReceptKompDAO implements IRecipeCompDAO {
	
	static Hashtable<DoubleInteger, RecipeCompDTO> recipeCompList = new Hashtable<DoubleInteger, RecipeCompDTO>();

	@Override
	public RecipeCompDTO getRecipeComp(int receptId, int raavareId) throws DALException {
		if(recipeCompList.get(new DoubleInteger(receptId, raavareId)) != null)
			return recipeCompList.get(new DoubleInteger(receptId, raavareId)).copy();

		else
			throw new DALException("Unknown Recipe Comp ID: " +new DoubleInteger(receptId, raavareId));
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList(int receptId) throws DALException {
		List<RecipeCompDTO> recipeComps = new ArrayList<RecipeCompDTO>();

		Set<DoubleInteger> keys = recipeCompList.keySet();

		for(DoubleInteger key : keys){
			if(recipeCompList.get(key).getReceptId() == receptId)
				recipeComps.add(recipeCompList.get(key).copy());
		}

		return recipeComps;
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList() throws DALException {
		List<RecipeCompDTO> recipeComps = new ArrayList<RecipeCompDTO>();

		Set<DoubleInteger> keys = recipeCompList.keySet();

		for(DoubleInteger key : keys){
			recipeComps.add(recipeCompList.get(key).copy());
		}

		return recipeComps;
	}

	@Override
	public void createRecipeComp(RecipeCompDTO receptkomponent) throws DALException {
		if (recipeCompList.putIfAbsent(new DoubleInteger(receptkomponent.getReceptId(), receptkomponent.getRaavareId()), receptkomponent.copy()) == null)
			return;

		else
			throw new CollisionException("Recipe Comp ID:"+new DoubleInteger(receptkomponent.getReceptId(), receptkomponent.getRaavareId())+" already exists !");

	}

	@Override
	public void updateRecipeComp(RecipeCompDTO receptkomponent) throws DALException {
		recipeCompList.replace(new DoubleInteger(receptkomponent.getReceptId(), receptkomponent.getRaavareId()), receptkomponent.copy());

	}

}
