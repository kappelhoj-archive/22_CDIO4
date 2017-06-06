package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataTransferObjects.RecipeCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class RecipeCompDAO implements IRecipeCompDAO {
	
	static Hashtable<DoubleInteger, RecipeCompDTO> recipeCompList = new Hashtable<DoubleInteger, RecipeCompDTO>();

	@Override
	public RecipeCompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException {
		if(recipeCompList.get(new DoubleInteger(recipeId, rawMaterialId)) != null)
			return recipeCompList.get(new DoubleInteger(recipeId, rawMaterialId)).copy();

		else
			throw new DALException("Unknown Recipe Comp ID: " +new DoubleInteger(recipeId, rawMaterialId));
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList(int recipeId) throws DALException {
		List<RecipeCompDTO> recipeComps = new ArrayList<RecipeCompDTO>();

		Set<DoubleInteger> keys = recipeCompList.keySet();

		for(DoubleInteger key : keys){
			if(recipeCompList.get(key).getRecipeId() == recipeId)
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
	public void createRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		if (recipeCompList.putIfAbsent(new DoubleInteger(recipeComponent.getRecipeId(), recipeComponent.getRawMaterialId()), recipeComponent.copy()) == null)
			return;

		else
			throw new CollisionException("Recipe Comp ID:"+new DoubleInteger(recipeComponent.getRecipeId(), recipeComponent.getRawMaterialId())+" already exists !");

	}

	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		recipeCompList.replace(new DoubleInteger(recipeComponent.getRecipeId(), recipeComponent.getRawMaterialId()), recipeComponent.copy());

	}

}
