package dataAccessObjects.interfaces;

import java.util.List;

import dataTransferObjects.ProductBatchCompDTO;
import exceptions.DALException;

public interface IProductBatchCompDAO {
	ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException;
	List<ProductBatchCompDTO> getProductBatchCompList(int pbId) throws DALException;
	List<ProductBatchCompDTO> getProductBatchCompList() throws DALException;
	void createProductBatchComp(ProductBatchCompDTO produktBatchComponent) throws DALException;
	void updateProductBatchComp(ProductBatchCompDTO produktBatchComponent) throws DALException;	
}

