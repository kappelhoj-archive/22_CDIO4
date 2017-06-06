package controller.interfaces;

import java.util.List;

import dataTransferObjects.RecipeDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public interface IRecipeController {
	RecipeDTO getRecipe(int recipeId) throws DALException;
	List<RecipeDTO> getRecipeList() throws DALException;
	void createRecipe(RecipeDTO recipe) throws CollisionException,  DALException;
	void updateRecipe(RecipeDTO recipe) throws DALException;
}
