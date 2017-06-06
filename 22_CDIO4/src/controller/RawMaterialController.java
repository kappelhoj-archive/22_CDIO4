package controller;

import java.util.List;

import controller.interfaces.IRawMaterialController;
import dataAccessObjects.MyRaavareDAO;
import dataAccessObjects.interfaces.RaavareDAO;
import dataTransferObjects.RaavareDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class RawMaterialController implements IRawMaterialController {
	
	RaavareDAO dao = new MyRaavareDAO();

	@Override
	public RaavareDTO getRawMaterial(int id) throws InputException, DALException {
		return dao.getRaavare(id);
	}

	@Override
	public List<RaavareDTO> getRawMaterialList() throws DALException {
		return dao.getRaavareList();
	}

	@Override
	public void createRawMaterial(RaavareDTO rawMaterial) throws CollisionException, InputException, DALException {
		dao.createRaavare(rawMaterial);
	}

	@Override
	public void updateRawMaterial(RaavareDTO rawMaterial) throws InputException, DALException {
		dao.updateRaavare(rawMaterial);
	}

}
