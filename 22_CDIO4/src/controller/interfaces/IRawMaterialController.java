package controller.interfaces;

import java.util.List;

import dataTransferObjects.RaavareDTO;
import exceptions.CollisionException;
import exceptions.*;

public interface IRawMaterialController {
	RaavareDTO getRawMaterial(int raavareId) throws InputException, DALException;
	List<RaavareDTO> getRawMaterial() throws DALException;
	void createRawMaterial(RaavareDTO raavare) throws CollisionException, InputException, DALException;
	void updateRawMaterial(RaavareDTO raavare) throws InputException, DALException;
}
