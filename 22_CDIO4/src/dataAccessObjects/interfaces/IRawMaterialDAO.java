package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.RawMaterialDTO;
import exceptions.DALException;

public interface IRawMaterialDAO {
	RawMaterialDTO getRawMaterial(int id) throws DALException;
	List<RawMaterialDTO> getRawMaterialList() throws DALException;
	void createRawMaterial(RawMaterialDTO rawMaterial) throws DALException;
	void updateRawMaterial(RawMaterialDTO rawMaterial) throws DALException;
}
