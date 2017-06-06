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
	public List<RaavareBatchDTO> getRawMaterialBatchList(int rawMaterialId) throws DALException {
		return dao.getRaavareBatchList(rawMaterialId);
	}

	@Override
	public void createRawMaterialBatch(RaavareBatchDTO rawMaterialBatch)
			throws CollisionException, DALException {
		dao.createRaavareBatch(rawMaterialBatch);
	}

	@Override
	public void updateRawMaterialBatch(RaavareBatchDTO rawMaterialBatch) throws DALException {
		dao.updateRaavareBatch(rawMaterialBatch);
	}

}
