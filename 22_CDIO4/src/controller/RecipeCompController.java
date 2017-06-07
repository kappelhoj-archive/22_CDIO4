package controller;

import java.util.List;

import controller.interfaces.IRecipeCompController;
import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataTransferObjects.RecipeCompDTO;
import exceptions.DALException;

public class RecipeCompController implements IRecipeCompController {
	
	IRecipeCompDAO dao;
	
	public RecipeCompController(IRecipeCompDAO dao){
		this.dao = dao;
	}	

	public IRecipeCompDAO getDao() {
		return dao;
	}

	@Override
	public RecipeCompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException {
		return dao.getRecipeComp(recipeId, rawMaterialId);
	}

	@Override
	public List<RecipeCompDTO> getRecipeComp(int recipeId) throws DALException {
		return dao.getRecipeCompList(recipeId);
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList() throws DALException {
		return dao.getRecipeCompList();
	}

	@Override
	public void createRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		dao.createRecipeComp(recipeComponent);

	}

	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		dao.updateRecipeComp(recipeComponent);

	}

}
