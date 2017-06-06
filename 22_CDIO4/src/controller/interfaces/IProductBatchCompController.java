package controller.interfaces;

import java.util.List;

import dataTransferObjects.ProductBatchCompDTO;
import exceptions.*;

public interface IProductBatchCompController {
	ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException;
	List<ProductBatchCompDTO> getProductBatchCompList(int pbId) throws DALException;
	List<ProductBatchCompDTO> getProductBatchCompList() throws DALException;
	void createProductBatchComp(ProductBatchCompDTO productBatchComponent) throws CollisionException, DALException;
	void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException;	
}
