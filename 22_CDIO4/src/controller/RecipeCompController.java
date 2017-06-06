package controller;

import java.util.List;

import controller.interfaces.IRecipeCompController;
import dataAccessObjects.MyReceptKompDAO;
import dataAccessObjects.interfaces.ReceptKompDAO;
import dataTransferObjects.ReceptKompDTO;
import exceptions.DALException;

public class RecipeCompController implements IRecipeCompController {
	
	ReceptKompDAO dao = new MyReceptKompDAO();

	@Override
	public ReceptKompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException {
		return dao.getReceptKomp(recipeId, rawMaterialId);
	}

	@Override
	public List<ReceptKompDTO> getRecipeComp(int recipeId) throws DALException {
		return dao.getReceptKompList(recipeId);
	}

	@Override
	public List<ReceptKompDTO> getRecipeCompList() throws DALException {
		return dao.getReceptKompList();
	}

	@Override
	public void createRecipeComp(ReceptKompDTO recipeComponent) throws DALException {
		dao.createReceptKomp(recipeComponent);

	}

	@Override
	public void updateRecipeComp(ReceptKompDTO recipeComponent) throws DALException {
		dao.updateReceptKomp(recipeComponent);

	}

}
