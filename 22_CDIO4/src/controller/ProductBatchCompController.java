package controller;

import java.util.List;

import controller.interfaces.IProductBatchCompController;
import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class ProductBatchCompController implements IProductBatchCompController {

	@Override
	public ProduktBatchKompDTO getProductBatchComp(int pbId, int rbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduktBatchKompDTO> getProductBatchCompList(int pbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduktBatchKompDTO> getProductBatchCompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProductBatchComp(ProduktBatchKompDTO produktbatchkomponent)
			throws CollisionException, DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProductBatchComp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		// TODO Auto-generated method stub
		
	}


}
