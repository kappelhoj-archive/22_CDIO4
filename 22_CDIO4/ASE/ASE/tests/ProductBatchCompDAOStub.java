package ASE.tests;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.DALException;

public class ProductBatchCompDAOStub implements IProductBatchCompDAO{
	Queue<ProductBatchCompDTO> measurements = new LinkedList<ProductBatchCompDTO>();

	@Override
	public ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException {
		// TODO Auto-generated method stub
		return measurements.remove();
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList(int pbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createProductBatchComp(ProductBatchCompDTO produktBatchComponent) throws DALException {
		measurements.add(produktBatchComponent);
		
	}

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO produktBatchComponent) throws DALException {
		// TODO Auto-generated method stub
		
	}

}
