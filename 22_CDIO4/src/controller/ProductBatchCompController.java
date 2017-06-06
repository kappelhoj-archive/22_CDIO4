package controller;

import java.util.List;

import controller.interfaces.IProductBatchCompController;
import dataAccessObjects.MyProduktBatchKompDAO;
import dataAccessObjects.interfaces.ProduktBatchKompDAO;
import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class ProductBatchCompController implements IProductBatchCompController {

	
	ProduktBatchKompDAO dao = new MyProduktBatchKompDAO();
	
	@Override
	public ProduktBatchKompDTO getProductBatchComp(int pbId, int rbId) throws DALException {
		return dao.getProduktBatchKomp(pbId, rbId);
	}

	@Override
	public List<ProduktBatchKompDTO> getProductBatchCompList(int pbId) throws DALException {
		return dao.getProduktBatchKompList(pbId);
	}

	@Override
	public List<ProduktBatchKompDTO> getProductBatchCompList() throws DALException {
		return dao.getProduktBatchKompList();
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
