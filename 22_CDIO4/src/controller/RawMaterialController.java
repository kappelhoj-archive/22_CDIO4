package controller;

import java.util.List;

import controller.interfaces.IRawMaterialController;
import dataAccessObjects.RawMaterialDAO;
import dataAccessObjects.interfaces.IRawMaterialDAO;
import dataTransferObjects.RawMaterialDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class RawMaterialController implements IRawMaterialController {
	
	IRawMaterialDAO dao = new RawMaterialDAO();

	@Override
	public RawMaterialDTO getRawMaterial(int id) throws InputException, DALException {
		return dao.getRawMaterial(id);
	}

	@Override
	public List<RawMaterialDTO> getRawMaterialList() throws DALException {
		return dao.getRawMaterialList();
	}

	@Override
	public void createRawMaterial(RawMaterialDTO rawMaterial) throws CollisionException, InputException, DALException {
		dao.createRawMaterial(rawMaterial);
	}

	@Override
	public void updateRawMaterial(RawMaterialDTO rawMaterial) throws InputException, DALException {
		dao.updateRawMaterial(rawMaterial);
	}

}
