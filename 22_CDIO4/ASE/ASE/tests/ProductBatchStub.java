package ASE.tests;

import java.util.List;

import dataAccessObjects.interfaces.IProductBatchDAO;
import dataTransferObjects.ProductBatchDTO;
import exceptions.DALException;

public class ProductBatchStub implements IProductBatchDAO {
	ProductBatchDTO pbDTO= new ProductBatchDTO(1, 1, 4);
	
	@Override
	public ProductBatchDTO getProductBatch(int pbId) throws DALException {
		// TODO Auto-generated method stub
		return pbDTO;
	}

	@Override
	public List<ProductBatchDTO> getProductBatchList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProductBatch(ProductBatchDTO productBatch) throws DALException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProductBatch(ProductBatchDTO productBatch) throws DALException {
		pbDTO=productBatch;

	}

}
