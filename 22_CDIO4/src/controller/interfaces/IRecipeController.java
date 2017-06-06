package controller.interfaces;

import java.util.List;

import dataTransferObjects.ReceptDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public interface IRecipeController {
	ReceptDTO getRecipe(int recipeId) throws DALException;
	List<ReceptDTO> getRecipeList() throws DALException;
	void createRecipe(ReceptDTO recipe) throws CollisionException,  DALException;
	void updateRecipe(ReceptDTO recipe) throws DALException;
}
