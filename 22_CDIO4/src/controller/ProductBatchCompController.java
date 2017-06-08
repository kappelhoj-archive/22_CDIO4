package controller;

import java.util.List;

import controller.interfaces.IProductBatchCompController;
import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataAccessObjects.interfaces.IProductBatchDAO;
import dataAccessObjects.interfaces.IRawMaterialBatchDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

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
		
		pbdao.getProductBatch(productBatchComponent.getPbId());
		rmbdao.getRawMaterialBatch(productBatchComponent.getRbId());
		
		dao.createProductBatchComp(productBatchComponent);
		
	}

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {
		dao.updateProductBatchComp(productBatchComponent);
		
	}


}
