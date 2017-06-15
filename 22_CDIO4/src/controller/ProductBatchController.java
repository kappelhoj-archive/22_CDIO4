package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.interfaces.IProductBatchController;
import dataAccessObjects.interfaces.IProductBatchDAO;
import dataAccessObjects.interfaces.IRecipeDAO;
import dataTransferObjects.ProductBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

public class ProductBatchController implements IProductBatchController {

	IProductBatchDAO dao;
	IRecipeDAO rdao;

	public ProductBatchController(IProductBatchDAO dao, IRecipeDAO rdao){
		this.dao = dao;
		this.rdao = rdao;
	}


	public IProductBatchDAO getDao() {
		return dao;
	}

	/**
	 * Returns a copy of a ProductBatchDTO from the data
	 * @param pbId
	 * @return ProductBatchDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public ProductBatchDTO getProductBatch(int pbId) throws InputException, DALException {
		return dao.getProductBatch(pbId);
	}

	/**
	 * Returns a list of ProductBatchDTOs from the data
	 * @return List<ProductBatchDTO>
	 */
	@Override
	public List<ProductBatchDTO> getProductBatchList() throws DALException {
		ArrayList<ProductBatchDTO> sortedArray = (ArrayList<ProductBatchDTO>) dao.getProductBatchList();

		Collections.sort(sortedArray);

		return sortedArray;
	}

	/**
	 * Adds a ProductBatchDTO to the saved data
	 * @param ProductBatchDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 * @throws InputException : Params not correct
	 */
	@Override
	public void createProductBatch(ProductBatchDTO productBatch) //TODO check -1<status<3
			throws CollisionException, InputException, DALException {
		
		Validator.idToInteger(productBatch.getPbId());//Use overload to check if the id is in the good range
		
		try{
			rdao.getRecipe(productBatch.getRecipeId()); //checks if the recipeID exists
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}

		if(productBatch.getStatus() >= 0 && productBatch.getStatus() <= 2) //checks if the status is between 0 and 2
			dao.createProductBatch(productBatch);
		else
			throw new InputException("Status must be between 0 and 2");
	}

	@Override
	public void updateProductBatch(ProductBatchDTO productBatch) throws InputException, DALException {
		if(productBatch.getStatus() <= 0 && productBatch.getStatus() <= 2)
			dao.updateProductBatch(productBatch);
		else
			throw new InputException("Status must be between 0 and 2");
	}

}
