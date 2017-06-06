package controller;

import java.util.List;

import controller.interfaces.IProductBatchController;
import dataTransferObjects.ProduktBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class ProductBatchController implements IProductBatchController {

	@Override
	public ProduktBatchDTO getProductBatch(int pbId) throws InputException, DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduktBatchDTO> getProductBatchList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProductBatch(ProduktBatchDTO productbatch) //TODO check -1<status<3
			throws CollisionException, InputException, DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProductBatch(ProduktBatchDTO productbatch) throws InputException, DALException {
		// TODO Auto-generated method stub

	}

}
