package controller.interfaces;

import java.util.List;

import dataTransferObjects.RecipeCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public interface IRecipeCompController {
	RecipeCompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException;
	List<RecipeCompDTO> getRecipeCompList(int recipeId) throws DALException;
	List<RecipeCompDTO> getRecipeCompList() throws DALException;
	void createRecipeComp(RecipeCompDTO recipeComponent) throws InputException, CollisionException, DALException;
	void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException;
}
