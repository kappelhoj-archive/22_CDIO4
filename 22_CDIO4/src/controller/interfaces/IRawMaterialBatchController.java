package controller.interfaces;

import java.util.List;

import dataTransferObjects.RawMaterialBatchDTO;
import exceptions.*;

public interface IRawMaterialBatchController {
	RawMaterialBatchDTO getRawMaterialBatch(int rbId) throws InputException, DALException;
	List<RawMaterialBatchDTO> getRawMaterialBatchList() throws DALException;
	List<RawMaterialBatchDTO> getRawMaterialBatchList(int raavareId) throws InputException, DALException;
	void createRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws CollisionException, InputException, DALException;
	void updateRawMaterialBatch(RawMaterialBatchDTO rawMaterialBatch) throws InputException, DALException;
}
