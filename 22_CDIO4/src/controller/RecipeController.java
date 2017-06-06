package controller;

import java.util.List;

import controller.interfaces.IRecipeController;
import dataAccessObjects.MyReceptDAO;
import dataAccessObjects.interfaces.ReceptDAO;
import dataTransferObjects.ReceptDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class RecipeController implements IRecipeController {
	
	ReceptDAO dao = new MyReceptDAO();

	@Override
	public ReceptDTO getRecipe(int recipeId) throws DALException {
		return dao.getRecept(recipeId);
	}

	@Override
	public List<ReceptDTO> getRecipeList() throws DALException {
		return dao.getReceptList();
	}

	@Override
	public void createRecipe(ReceptDTO recipe) throws CollisionException, DALException {
		dao.createRecept(recipe);

	}

	@Override
	public void updateRecipe(ReceptDTO recipe) throws DALException {
		dao.updateRecept(recipe);

	}

}
