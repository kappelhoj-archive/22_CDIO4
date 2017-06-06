package controller.interfaces;

import java.util.List;

import dataTransferObjects.ProductBatchDTO;
import exceptions.*;

public interface IProductBatchController {
	ProductBatchDTO getProductBatch(int pbId) throws DALException;
	List<ProductBatchDTO> getProductBatchList() throws DALException;
	void createProductBatch(ProductBatchDTO productBatch) throws InputException, CollisionException, DALException;
	void updateProductBatch(ProductBatchDTO productBatch) throws InputException, DALException;
}
