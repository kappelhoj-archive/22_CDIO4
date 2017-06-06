package controller.interfaces;

import java.util.List;

import dataTransferObjects.ReceptKompDTO;
import exceptions.DALException;

public interface IRecipeCompController {
	ReceptKompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException;
	List<ReceptKompDTO> getRecipeComp(int recipeId) throws DALException;
	List<ReceptKompDTO> getRecipeCompList() throws DALException;
	void createRecipeComp(ReceptKompDTO recipeComponent) throws DALException;
	void updateRecipeComp(ReceptKompDTO recipeComponent) throws DALException;
}
