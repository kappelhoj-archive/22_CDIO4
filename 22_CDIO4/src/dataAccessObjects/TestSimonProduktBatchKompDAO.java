package dataAccessObjects;

import java.util.List;

import dataTransferObjects.ProductBatchDTO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.DALException;
public class TestSimonProduktBatchKompDAO implements dataAccessObjects.interfaces.IProductBatchCompDAO {

	@Override
	public ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException {
		// TODO Auto-generated method stub
		return null;
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
	public void createProductBatchComp(ProductBatchCompDTO produktbatch) throws DALException {
		System.out.println(produktbatch.getPbId()+produktbatch.toString());
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO produktbatchkomponent) throws DALException {
		// TODO Auto-generated method stub
		
	}


}
