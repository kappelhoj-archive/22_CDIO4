package controller.interfaces;

import java.util.List;

import dataTransferObjects.RawMaterialDTO;
import exceptions.CollisionException;
import exceptions.*;

public interface IRawMaterialController {
	RawMaterialDTO getRawMaterial(int id) throws InputException, DALException;
	List<RawMaterialDTO> getRawMaterialList() throws DALException;
	void createRawMaterial(RawMaterialDTO rawMaterial) throws CollisionException, InputException, DALException;
	void updateRawMaterial(RawMaterialDTO rawMaterial) throws InputException, DALException;
}
