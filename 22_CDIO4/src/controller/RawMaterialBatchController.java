package controller;

import java.util.List;

import controller.interfaces.IRawMaterialBatchController;
import dataAccessObjects.RawMaterialBatchDAO;
import dataAccessObjects.interfaces.IRawMaterialBatchDAO;
import dataTransferObjects.RawMaterialBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class RawMaterialBatchController implements IRawMaterialBatchController {

	IRawMaterialBatchDAO dao = new RawMaterialBatchDAO();

	@Override
	public RawMaterialBatchDTO getRawMaterialBatch(int rbId) throws DALException {
		return dao.getRawMaterialBatch(rbId);
	}

	@Override
	public List<RawMaterialBatchDTO> getRawMaterialBatchList() throws DALException {
		return dao.getRawMaterialBatchList();
	}

	@Override
	public List<RawMaterialBatchDTO> getRawMaterialBatchList(int rawMaterialId) throws DALException {
		return dao.getRawMaterialBatchList(rawMaterialId);
	}

	@Override
	public void createRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch)
			throws CollisionException, DALException {
		dao.createRawMaterialBatch(rawMaterialBatch);
	}

	@Override
	public void updateRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException {
		dao.updateRawMaterialBatch(rawMaterialBatch);
	}

}
