package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IRecipeDAO;
import dataTransferObjects.RecipeDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class MyReceptDAO implements IRecipeDAO {
	
	static Hashtable<Integer, RecipeDTO> recipeList = new Hashtable<Integer, RecipeDTO>();

	@Override
	public RecipeDTO getRecipe(int receptId) throws DALException {
		if(recipeList.get(receptId) != null)
			return recipeList.get(receptId).copy();
	
		else
			throw new DALException("Unknown Recipe ID: " + receptId);
	}

	@Override
	public List<RecipeDTO> getRecipeList() throws DALException {
		List<RecipeDTO> recipes = new ArrayList<RecipeDTO>();

		Set<Integer> keys = recipeList.keySet();
		
		for(Integer key : keys){
			recipes.add(recipeList.get(key).copy());
		}

		return recipes;
	}

	@Override
	public void createRecipe(RecipeDTO recept) throws DALException {
		if (recipeList.putIfAbsent(recept.getReceptId(), recept.copy()) == null)
			return;
		
		else
			throw new CollisionException("Recpipe ID:"+recept.getReceptId()+" already exists !");

	}

	@Override
	public void updateRecipe(RecipeDTO recept) throws DALException {
		recipeList.replace(recept.getReceptId(), recept.copy());

	}

}
