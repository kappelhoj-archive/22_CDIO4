package controller.interfaces;

import java.util.List;

import dataTransferObjects.RaavareBatchDTO;
import exceptions.*;

public interface IRawMaterialBatchController {
	RaavareBatchDTO getRawMaterialBatch(int rbId) throws InputException, DALException;
	List<RaavareBatchDTO> getRawMaterialBatchList() throws DALException;
	List<RaavareBatchDTO> getRawMaterialBatchList(int raavareId) throws InputException, DALException;
	void createRawMaterialBatch(RaavareBatchDTO raavarebatch) throws CollisionException, InputException, DALException;
	void updateRawMaterialBatch(RaavareBatchDTO raavarebatch) throws InputException, DALException;
}
