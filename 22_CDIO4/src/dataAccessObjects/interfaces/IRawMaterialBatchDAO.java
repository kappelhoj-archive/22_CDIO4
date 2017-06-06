package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.RawMaterialBatchDTO;
import exceptions.DALException;

public interface IRawMaterialBatchDAO {
	RawMaterialBatchDTO getRawMaterialBatch(int rbId) throws DALException;
	List<RawMaterialBatchDTO> getRawMaterialBatchList() throws DALException;
	List<RawMaterialBatchDTO> getRawMaterialBatchList(int rawMaterialId) throws DALException;
	void createRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException;
	void updateRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws DALException;
}

