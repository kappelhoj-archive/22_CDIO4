package controller;

import java.util.List;

import controller.interfaces.IProductBatchCompController;
import dataAccessObjects.ProductBatchCompDAO;
import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class ProductBatchCompController implements IProductBatchCompController {

	
	IProductBatchCompDAO dao = new ProductBatchCompDAO();
	
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
		dao.createProductBatchComp(productBatchComponent);
		
	}

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {
		dao.updateProductBatchComp(productBatchComponent);
		
	}


}
