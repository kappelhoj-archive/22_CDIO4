package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.RecipeDTO;
import exceptions.DALException;


public interface IRecipeDAO {
	RecipeDTO getRecipe(int id) throws DALException;
	List<RecipeDTO> getRecipeList() throws DALException;
	void createRecipe(RecipeDTO recipe) throws DALException;
	void updateRecipe(RecipeDTO recipe) throws DALException;
}
