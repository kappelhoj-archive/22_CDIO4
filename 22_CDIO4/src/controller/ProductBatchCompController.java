package controller;

import java.util.List;

import controller.interfaces.IProductBatchCompController;
import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataAccessObjects.interfaces.IProductBatchDAO;
import dataAccessObjects.interfaces.IRawMaterialBatchDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class ProductBatchCompController implements IProductBatchCompController {

	IProductBatchCompDAO dao;
	IProductBatchDAO pbdao;
	IRawMaterialBatchDAO rmbdao;

	public ProductBatchCompController(IProductBatchCompDAO dao, IProductBatchDAO pbdao, IRawMaterialBatchDAO rmbdao){
		this.dao = dao;
		this.pbdao = pbdao;
		this.rmbdao = rmbdao;
	}


	public IProductBatchCompDAO getDao() {
		return dao;
	}

	/**
	 * Returns a copy of a ProductBatchCompDTO from the data
	 * @param productbatchID, rawmaterialbatchId
	 * @return ProductBatchCompDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException {
		return dao.getProductBatchComp(pbId, rbId);
	}

	/**
	 * Returns a list of ProductBatchCompDTOs from the data
	 * @param productbatchID
	 * @return List<ProductBatchCompDTO>
	 */
	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList(int pbId) throws DALException {
		return dao.getProductBatchCompList(pbId);
	}

	/**
	 * Returns a list of ProductBatchCompDTOs from the data
	 * @return List<ProductBatchCompDTO>
	 */
	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
		return dao.getProductBatchCompList();
	}

	/**
	 * Adds a ProductBatchCompDTO to the saved data
	 * @param ProductBatchCompDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 * @throws InputException : Params not correct
	 */
	@Override
	public void createProductBatchComp(ProductBatchCompDTO productBatchComponent)
			throws CollisionException, DALException {

		//Verify if the creation params are correct : The ProductBatch and the RawMaterialBatch shall both exist in the Data
		try{
			pbdao.getProductBatch(productBatchComponent.getPbId());
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}
		try{
			rmbdao.getRawMaterialBatch(productBatchComponent.getRbId());
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}

		dao.createProductBatchComp(productBatchComponent);

	}

	/**
	 * Updates a ProductBatchCompDTO in the saved data
	 * @param ProductBatchCompDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {
		dao.updateProductBatchComp(productBatchComponent);

	}


}
