package controller.interfaces;

import java.util.List;

import dataTransferObjects.ProduktBatchDTO;
import exceptions.*;

public interface IProductBatchController {
	ProduktBatchDTO getProductBatch(int pbId) throws DALException;
	List<ProduktBatchDTO> getProductBatchList() throws DALException;
	void createProductBatch(ProduktBatchDTO produktbatch) throws InputException, CollisionException, DALException;
	void updateProductBatch(ProduktBatchDTO produktbatch) throws InputException, DALException;
}
