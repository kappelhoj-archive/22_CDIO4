package controller;

import java.util.List;

import controller.interfaces.IProductBatchController;
import dataAccessObjects.MyProduktBatchDAO;
import dataAccessObjects.interfaces.IProductBatchDAO;
import dataTransferObjects.ProductBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class ProductBatchController implements IProductBatchController {

	IProductBatchDAO dao = new MyProduktBatchDAO();
	
	@Override
	public ProductBatchDTO getProductBatch(int pbId) throws InputException, DALException {
		return dao.getProductBatch(pbId);
	}

	@Override
	public List<ProductBatchDTO> getProductBatchList() throws DALException {
		return dao.getProductBatchList();
	}

	@Override
	public void createProductBatch(ProductBatchDTO productBatch) //TODO check -1<status<3
			throws CollisionException, InputException, DALException {
		if(productBatch.getStatus() <= 0 && productBatch.getStatus() <= 2)
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
