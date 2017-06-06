package controller.interfaces;

import java.util.List;

import dataTransferObjects.RaavareDTO;
import exceptions.CollisionException;
import exceptions.*;

public interface IRawMaterialController {
	RaavareDTO getRawMaterial(int id) throws InputException, DALException;
	List<RaavareDTO> getRawMaterialList() throws DALException;
	void createRawMaterial(RaavareDTO rawMaterial) throws CollisionException, InputException, DALException;
	void updateRawMaterial(RaavareDTO rawMaterial) throws InputException, DALException;
}
