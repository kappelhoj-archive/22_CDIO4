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

	@Override
	public RecipeDTO getRecipe(int recipeId) throws DALException {
		return dao.getRecipe(recipeId);
	}

	@Override
	public List<RecipeDTO> getRecipeList() throws DALException {
		return dao.getRecipeList();
	}

	@Override
	public void createRecipe(RecipeDTO recipe) throws CollisionException, DALException {
		dao.createRecipe(recipe);

	}

	@Override
	public void updateRecipe(RecipeDTO recipe) throws DALException {
		dao.updateRecipe(recipe);

	}

}
