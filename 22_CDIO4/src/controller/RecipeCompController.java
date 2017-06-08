package controller;

import java.util.List;

import controller.interfaces.IRecipeCompController;
import dataAccessObjects.interfaces.IRawMaterialDAO;
import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataAccessObjects.interfaces.IRecipeDAO;
import dataTransferObjects.RecipeCompDTO;
import exceptions.DALException;
import exceptions.InputException;

public class RecipeCompController implements IRecipeCompController {

	IRecipeCompDAO dao;
	IRecipeDAO rDAO;
	IRawMaterialDAO rmDAO;

	public RecipeCompController(IRecipeCompDAO dao, IRecipeDAO rDAO, IRawMaterialDAO rmDAO){
		this.dao = dao;
		this.rDAO = rDAO;
		this.rmDAO = rmDAO;
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
		try{
			rDAO.getRecipe(recipeComponent.getRecipeId());
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}
		try{
			rmDAO.getRawMaterial(recipeComponent.getRawMaterialId());
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}
		dao.createRecipeComp(recipeComponent);

	}

	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		dao.updateRecipeComp(recipeComponent);

	}

}
