package controller;

import java.util.List;

import controller.interfaces.IRawMaterialController;
import dataTransferObjects.RaavareDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import exceptions.InputException;

public class RawMaterialController implements IRawMaterialController {

	@Override
	public RaavareDTO getRawMaterial(int raavareId) throws InputException, DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RaavareDTO> getRawMaterial() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createRawMaterial(RaavareDTO raavare) throws CollisionException, InputException, DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRawMaterial(RaavareDTO raavare) throws InputException, DALException {
		// TODO Auto-generated method stub

	}

}
