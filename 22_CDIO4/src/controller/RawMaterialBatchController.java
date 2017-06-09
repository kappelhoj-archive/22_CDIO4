package controller;

import java.util.List;

import controller.interfaces.IRawMaterialBatchController;
import dataAccessObjects.interfaces.IRawMaterialBatchDAO;
import dataTransferObjects.RawMaterialBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class RawMaterialBatchController implements IRawMaterialBatchController {

	IRawMaterialBatchDAO dao;
	
	public RawMaterialBatchController(IRawMaterialBatchDAO dao){
		this.dao = dao;
	}	


	public IRawMaterialBatchDAO getDao() {
		return dao;
	}

	/**
	 * Returns a copy of a RawMaterialBatchDTO from the data
	 * @param rbId : rawmaterialbatchId
	 * @return RawMaterialBatchDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public RawMaterialBatchDTO getRawMaterialBatch(int rbId) throws DALException {
		return dao.getRawMaterialBatch(rbId);
	}

	/**
	 * Returns a list of RawMaterialBatchDTOs from the data
	 * @return List<RawMaterialBatchDTO>
	 */
	@Override
	public List<RawMaterialBatchDTO> getRawMaterialBatchList() throws DALException {
		return dao.getRawMaterialBatchList();
	}

	 /*
	 * Returns a list of RawMaterialBatchDTOs from the data
	 * @param rawMaterialId
	 * @return List<RawMaterialBatchDTO>
	 */
	@Override
	public List<RawMaterialBatchDTO> getRawMaterialBatchList(int rawMaterialId) throws DALException {
		return dao.getRawMaterialBatchList(rawMaterialId);
	}

	/**
	 * Method which adds a RawMaterialBatchDTO to the saved data
	 * @param RawMaterialBatchDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 */
	@Override
	public void createRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch)
			throws CollisionException, DALException {
		dao.createRawMaterialBatch(rawMaterialBatch);
	}

	/**
	 * Updates a RawMaterialBatchDTO in the saved data
	 * @param RawMaterialBatchDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public void updateRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException {
		dao.updateRawMaterialBatch(rawMaterialBatch);
	}

}
