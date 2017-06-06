package controller;

import java.util.List;

import controller.interfaces.IRecipeCompController;
import dataTransferObjects.ReceptKompDTO;
import exceptions.DALException;

public class RecipeCompController implements IRecipeCompController {

	@Override
	public ReceptKompDTO getRecipeComp(int receptId, int raavareId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReceptKompDTO> getRecipeComp(int receptId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReceptKompDTO> getRecipeCompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRecipeComp(ReceptKompDTO recipecomponent) throws DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRecipeComp(ReceptKompDTO recipecomponent) throws DALException {
		// TODO Auto-generated method stub

	}

}
