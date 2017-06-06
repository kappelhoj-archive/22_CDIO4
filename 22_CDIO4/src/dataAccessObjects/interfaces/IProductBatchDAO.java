package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.ProductBatchDTO;
import exceptions.DALException;

public interface IProductBatchDAO {
	ProductBatchDTO getProductBatch(int pbId) throws DALException;
	List<ProductBatchDTO> getProductBatchList() throws DALException;
	void createProductBatch(ProductBatchDTO productBatch) throws DALException;
	void updateProductBatch(ProductBatchDTO productBatch) throws DALException;
}