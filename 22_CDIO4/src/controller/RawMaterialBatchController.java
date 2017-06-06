package controller;

import java.util.List;

import controller.interfaces.IRawMaterialBatchController;
import dataTransferObjects.RaavareBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class RawMaterialBatchController implements IRawMaterialBatchController {

	@Override
	public RaavareBatchDTO getRawMaterialBatch(int rbId) throws InputException, DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RaavareBatchDTO> getRawMaterialBatchList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RaavareBatchDTO> getRawMaterialBatchList(int raavareId) throws InputException, DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRawMaterialBatch(RaavareBatchDTO raavarebatch)
			throws CollisionException, InputException, DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRawMaterialBatch(RaavareBatchDTO raavarebatch) throws InputException, DALException {
		// TODO Auto-generated method stub
		
	}

}
