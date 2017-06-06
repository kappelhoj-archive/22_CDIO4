package controller;

import java.util.List;

import controller.interfaces.IRawMaterialBatchController;
import dataAccessObjects.MyRaavareBatchDAO;
import dataAccessObjects.interfaces.RaavareBatchDAO;
import dataTransferObjects.RaavareBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class RawMaterialBatchController implements IRawMaterialBatchController {

	RaavareBatchDAO dao = new MyRaavareBatchDAO();

	@Override
	public RaavareBatchDTO getRawMaterialBatch(int rbId) throws DALException {
		return dao.getRaavareBatch(rbId);
	}

	@Override
	public List<RaavareBatchDTO> getRawMaterialBatchList() throws DALException {
		return dao.getRaavareBatchList();
	}

	@Override
	public List<RaavareBatchDTO> getRawMaterialBatchList(int raavareId) throws DALException {
		return dao.getRaavareBatchList(raavareId);
	}

	@Override
	public void createRawMaterialBatch(RaavareBatchDTO raavarebatch)
			throws CollisionException, DALException {
		dao.createRaavareBatch(raavarebatch);
	}

	@Override
	public void updateRawMaterialBatch(RaavareBatchDTO raavarebatch) throws DALException {
		dao.updateRaavareBatch(raavarebatch);
	}

}
