package controller;

import java.util.List;

import controller.interfaces.IRawMaterialController;
import dataAccessObjects.interfaces.IRawMaterialDAO;
import dataTransferObjects.RawMaterialDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class RawMaterialController implements IRawMaterialController {
	
	IRawMaterialDAO dao;
	
	public RawMaterialController(IRawMaterialDAO dao){
		this.dao = dao;
	}	

	public IRawMaterialDAO getDao() {
		return dao;
	}
	
	/**
	 * Returns a copy of a RawMaterialDTO from the data
	 * @param rawmaterialId
	 * @return RawMaterialDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public RawMaterialDTO getRawMaterial(int id) throws InputException, DALException {
		return dao.getRawMaterial(id);
	}

	/**
	 * Returns a list of RawMaterialDTOs from the data
	 * @return List<RawMaterialDTO>
	 */
	@Override
	public List<RawMaterialDTO> getRawMaterialList() throws DALException {
		return dao.getRawMaterialList();
	}

	/**
	 * Adds a RawMaterialDTO to the saved data
	 * @param RawMaterialDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 */
	@Override
	public void createRawMaterial(RawMaterialDTO rawMaterial) throws CollisionException, InputException, DALException {
		dao.createRawMaterial(rawMaterial);
	}

	/**
	 * Updates a RawMaterialDTO in the saved data
	 * @param RawMaterialDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public void updateRawMaterial(RawMaterialDTO rawMaterial) throws InputException, DALException {
		dao.updateRawMaterial(rawMaterial);
	}

}
