package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.interfaces.IRecipeCompController;
import dataAccessObjects.interfaces.IRawMaterialDAO;
import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataAccessObjects.interfaces.IRecipeDAO;
import dataTransferObjects.RecipeCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

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

	/**
	 * Returns a copy of a RecipeCompDTO from the data
	 * @param recipeId, rawMaterialId
	 * @return RecipeCompDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public RecipeCompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException {
		return dao.getRecipeComp(recipeId, rawMaterialId);
	}

	/**
	 * Returns a list of RecipeCompDTOs from the data
	 * @param recipeId
	 * @return List<RecipeCompDTO>
	 */
	@Override
	public List<RecipeCompDTO> getRecipeCompList(int recipeId) throws DALException {
		return dao.getRecipeCompList(recipeId);
	}

	/** 
	 * Returns a list of RecipeCompDTOs from the data
	 * @return List<RecipeCompDTO>
	 */
	@Override
	public List<RecipeCompDTO> getRecipeCompList() throws DALException {
		ArrayList<RecipeCompDTO> sortedArray = (ArrayList<RecipeCompDTO>) dao.getRecipeCompList();

		Collections.sort(sortedArray);

		return sortedArray;
	}

	/**
	 * Adds a RecipeCompDTO to the saved data
	 * @param RecipeCompDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 * @throws InputException : Params not correct
	 */
	@Override
	public void createRecipeComp(RecipeCompDTO recipeComponent) throws InputException, CollisionException, DALException {
		Validator.idToInteger(recipeComponent.getRawMaterialId());//Use overload to check if the id is in the good range
		Validator.idToInteger(recipeComponent.getRecipeId());
		
		try{
			rDAO.getRecipe(recipeComponent.getRecipeId()); //Verify if the recipeId exists
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}
		try{
			rmDAO.getRawMaterial(recipeComponent.getRawMaterialId()); //Verify if the rawMaterialId exists
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}
		dao.createRecipeComp(recipeComponent);

	}

	/**
	 * Updates a RecipeCompDTO in the saved data
	 * @param RecipeCompDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		dao.updateRecipeComp(recipeComponent);

	}

}
