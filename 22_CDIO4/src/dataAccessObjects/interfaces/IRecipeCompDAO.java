package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.RecipeCompDTO;
import exceptions.DALException;

public interface IRecipeCompDAO {
	RecipeCompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException;
	List<RecipeCompDTO> getRecipeCompList(int receptId) throws DALException;
	List<RecipeCompDTO> getRecipeCompList() throws DALException;
		void createRecipeComp(RecipeCompDTO recipeComponent) throws DALException;
	void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException;
}
