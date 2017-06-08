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


	@Override
	public ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException {
		return dao.getProductBatchComp(pbId, rbId);
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList(int pbId) throws DALException {
		return dao.getProductBatchCompList(pbId);
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
		return dao.getProductBatchCompList();
	}

	@Override
	public void createProductBatchComp(ProductBatchCompDTO productBatchComponent)
			throws CollisionException, DALException {

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

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {
		dao.updateProductBatchComp(productBatchComponent);

	}


}
