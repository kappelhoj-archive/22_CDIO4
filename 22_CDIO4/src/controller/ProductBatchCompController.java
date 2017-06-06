package controller;

import java.util.List;

import controller.interfaces.IProductBatchCompController;
import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class ProductBatchCompController implements IProductBatchCompController {

	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws InputException, DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent)
			throws CollisionException, InputException, DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws InputException, DALException {
		// TODO Auto-generated method stub

	}

}
