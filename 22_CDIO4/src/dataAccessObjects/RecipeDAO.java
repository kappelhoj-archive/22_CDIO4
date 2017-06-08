package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IRecipeDAO;
import dataTransferObjects.RecipeDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import staticClasses.FileManagement;
import staticClasses.FileManagement.TypeOfData;

public class RecipeDAO implements IRecipeDAO {
	
	static Hashtable<Integer, RecipeDTO> recipeList = new Hashtable<Integer, RecipeDTO>();
	
	@SuppressWarnings("unchecked")
	public RecipeDAO() {
		try{
			System.out.println("Retrieving Recipe Data...");
			recipeList = (Hashtable<Integer, RecipeDTO>) FileManagement.retrieveData(TypeOfData.RECIPE);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println(e);
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(recipeList, TypeOfData.USER);
			System.out.println("Done.");
		}
	}

	@Override
	public RecipeDTO getRecipe(int recipeId) throws DALException {
		if(recipeList.get(recipeId) != null)
			return recipeList.get(recipeId).copy();
	
		else
			throw new DALException("Unknown Recipe ID: " + recipeId);
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
	public void createRecipe(RecipeDTO recipe) throws DALException {
		if (recipeList.putIfAbsent(recipe.getRecipeId(), recipe.copy()) == null){
			FileManagement.writeData(recipeList, TypeOfData.RECIPE);
			return;
		}
		
		else
			throw new CollisionException("Recpipe ID:"+recipe.getRecipeId()+" already exists !");

	}

	@Override
	public void updateRecipe(RecipeDTO recipe) throws DALException {
		recipeList.replace(recipe.getRecipeId(), recipe.copy());
		FileManagement.writeData(recipeList, TypeOfData.RECIPE);
	}

}
