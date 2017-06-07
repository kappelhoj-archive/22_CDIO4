package ASE.tests.weightController;

import java.util.List;

import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataTransferObjects.RecipeCompDTO;
import exceptions.DALException;

public class RecipeCompDAOStub implements IRecipeCompDAO {

	RecipeCompDTO dto;
	
	public RecipeCompDAOStub(RecipeCompDTO dto){
		this.dto=dto;
	}
	
	@Override
	public RecipeCompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException {
		// TODO Auto-generated method stub
		return dto;
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList(int receptId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		// TODO Auto-generated method stub

	}

}
