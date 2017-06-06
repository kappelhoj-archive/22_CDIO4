package controller;

import java.util.List;

import controller.interfaces.IRecipeController;
import dataTransferObjects.ReceptDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class RecipeController implements IRecipeController {

	@Override
	public ReceptDTO getRecipe(int recipeId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReceptDTO> getRecipeList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRecipe(ReceptDTO recipe) throws CollisionException, DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRecipe(ReceptDTO recipe) throws DALException {
		// TODO Auto-generated method stub

	}

}
