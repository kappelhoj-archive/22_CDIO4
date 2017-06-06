package controller;

import java.util.List;

import controller.interfaces.IProductBatchController;
import dataAccessObjects.MyProduktBatchDAO;
import dataAccessObjects.interfaces.ProduktBatchDAO;
import dataTransferObjects.ProduktBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class ProductBatchController implements IProductBatchController {

	ProduktBatchDAO dao = new MyProduktBatchDAO();
	
	@Override
	public ProduktBatchDTO getProductBatch(int pbId) throws InputException, DALException {
		return dao.getProduktBatch(pbId);
	}

	@Override
	public List<ProduktBatchDTO> getProductBatchList() throws DALException {
		return dao.getProduktBatchList();
	}

	@Override
	public void createProductBatch(ProduktBatchDTO productBatch) //TODO check -1<status<3
			throws CollisionException, InputException, DALException {
		if(productBatch.getStatus() <= 0 && productBatch.getStatus() <= 2)
			dao.createProduktBatch(productBatch);
		else
			throw new InputException("Status must be between 0 and 2");
	}

	@Override
	public void updateProductBatch(ProduktBatchDTO productBatch) throws InputException, DALException {
		if(productBatch.getStatus() <= 0 && productBatch.getStatus() <= 2)
			dao.updateProduktBatch(productBatch);
		else
			throw new InputException("Status must be between 0 and 2");
	}

}
