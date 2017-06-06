package controller.interfaces;

import java.util.List;

import dataTransferObjects.ProduktBatchDTO;
import exceptions.*;

public interface IProductBatchController {
	ProduktBatchDTO getProductBatch(int pbId) throws DALException;
	List<ProduktBatchDTO> getProductBatchList() throws DALException;
	void createProductBatch(ProduktBatchDTO productBatch) throws InputException, CollisionException, DALException;
	void updateProductBatch(ProduktBatchDTO productBatch) throws InputException, DALException;
}
