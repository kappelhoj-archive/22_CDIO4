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
	
	/*
	 * The warning "unchecked" is there because Java can not define if the file we try to convert
	 * to a HashTable is associated to this class.
	 * We decided to ignore this warning in all our DAO.
	 */
	@SuppressWarnings("unchecked")
	public RecipeDAO() {
		try{
			System.out.println("Retrieving Recipe Data...");
			recipeList = (Hashtable<Integer, RecipeDTO>) FileManagement.retrieveData(TypeOfData.RECIPE);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(recipeList, TypeOfData.USER);
			System.out.println("Done.");
		}
	}
	
	/**
	 * Method which returns a copy of a RecipeDTO from the data
	 * @param recipeId
	 * @return RecipeDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public RecipeDTO getRecipe(int recipeId) throws DALException {
		if(recipeList.get(recipeId) != null)
			return recipeList.get(recipeId).copy();
	
		else
			throw new DALException("Unknown Recipe ID: " + recipeId);
	}

	/**
	 * Method which returns a list of RecipeDTOs from the data
	 * @return List<RecipeDTO>
	 */
	@Override
	public List<RecipeDTO> getRecipeList() throws DALException {
		List<RecipeDTO> recipes = new ArrayList<RecipeDTO>();

		Set<Integer> keys = recipeList.keySet();
		
		for(Integer key : keys){
			recipes.add(recipeList.get(key).copy());
		}

		return recipes;
	}

	/**
	 * Method which adds a RecipeDTO to the saved data
	 * @param RecipeDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 */
	@Override
	public void createRecipe(RecipeDTO recipe) throws DALException {
		if (recipeList.putIfAbsent(recipe.getRecipeId(), recipe.copy()) == null){
			FileManagement.writeData(recipeList, TypeOfData.RECIPE);
			return;
		}
		
		else
			throw new CollisionException("Recpipe ID:"+recipe.getRecipeId()+" already exists !");

	}

	/**
	 * Method which updates a RecipeDTO in the saved data
	 * @param RecipeDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public void updateRecipe(RecipeDTO recipe) throws DALException {
		getRecipe(recipe.getRecipeId());
		
		recipeList.replace(recipe.getRecipeId(), recipe.copy());
		FileManagement.writeData(recipeList, TypeOfData.RECIPE);
	}

}
