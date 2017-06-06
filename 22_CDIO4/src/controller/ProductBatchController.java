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
	public void createProductBatch(ProduktBatchDTO produktbatch) //TODO check -1<status<3
			throws CollisionException, InputException, DALException {
		if(produktbatch.getStatus() <= 0 && produktbatch.getStatus() <= 2)
			dao.createProduktBatch(produktbatch);
		else
			throw new InputException("Status must be between 0 and 2");
	}

	@Override
	public void updateProductBatch(ProduktBatchDTO produktbatch) throws InputException, DALException {
		if(produktbatch.getStatus() <= 0 && produktbatch.getStatus() <= 2)
			dao.updateProduktBatch(produktbatch);
		else
			throw new InputException("Status must be between 0 and 2");
	}

}
