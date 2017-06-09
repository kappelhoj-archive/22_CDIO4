package controller;

import java.util.List;

import controller.interfaces.IRecipeController;
import dataAccessObjects.interfaces.IRecipeDAO;
import dataTransferObjects.RecipeDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class RecipeController implements IRecipeController {
	
	IRecipeDAO dao;
	
	public RecipeController(IRecipeDAO dao){
		this.dao = dao;
	}	

	public IRecipeDAO getDao() {
		return dao;
	}
	
	/**
	 * Returns a copy of a RecipeDTO from the data
	 * @param recipeId
	 * @return RecipeDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public RecipeDTO getRecipe(int recipeId) throws DALException {
		return dao.getRecipe(recipeId);
	}

	/**
	 * Returns a list of RecipeDTOs from the data
	 * @return List<RecipeDTO>
	 */
	@Override
	public List<RecipeDTO> getRecipeList() throws DALException {
		return dao.getRecipeList();
	}

	/**
	 * Adds a RecipeDTO to the saved data
	 * @param RecipeDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 */
	@Override
	public void createRecipe(RecipeDTO recipe) throws CollisionException, DALException {
		dao.createRecipe(recipe);

	}

	/**
	 * Updates a RecipeDTO in the saved data
	 * @param RecipeDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public void updateRecipe(RecipeDTO recipe) throws DALException {
		dao.updateRecipe(recipe);

	}

}
