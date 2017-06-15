package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.interfaces.IRawMaterialBatchController;
import dataAccessObjects.interfaces.IRawMaterialBatchDAO;
import dataAccessObjects.interfaces.IRawMaterialDAO;
import dataTransferObjects.RawMaterialBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;
import staticClasses.Validator;

public class RawMaterialBatchController implements IRawMaterialBatchController {

	IRawMaterialBatchDAO dao;
	IRawMaterialDAO rmDAO;

	public RawMaterialBatchController(IRawMaterialBatchDAO dao, IRawMaterialDAO rmDAO){
		this.dao = dao;
		this.rmDAO = rmDAO;
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
		ArrayList<RawMaterialBatchDTO> sortedArray = (ArrayList<RawMaterialBatchDTO>) dao.getRawMaterialBatchList();

		Collections.sort(sortedArray);

		return sortedArray;
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
	 * @throws InputException if the RawMaterial ID does not exist
	 */
	@Override
	public void createRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch)
			throws CollisionException, InputException, DALException {

		Validator.idToInteger(rawMaterialBatch.getRbId());//Use overload to check if the id is in the good range
		
		try{
			rmDAO.getRawMaterial(rawMaterialBatch.getRawMaterialId());
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}

		dao.createRawMaterialBatch(rawMaterialBatch);
	}

	/**
	 * Updates a RawMaterialBatchDTO in the saved data
	 * @param RawMaterialBatchDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 * 	 * @throws InputException if the RawMaterial ID does not exist
	 */
	@Override
	public void updateRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException {
		try{
			rmDAO.getRawMaterial(rawMaterialBatch.getRawMaterialId());
		}catch(DALException e){
			throw new InputException(e.getMessage());
		}

		dao.updateRawMaterialBatch(rawMaterialBatch);
	}

}
