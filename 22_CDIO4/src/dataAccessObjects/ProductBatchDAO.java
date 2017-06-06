package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IWeightControlDAO;
import dataAccessObjects.interfaces.IProductBatchDAO;
import dataTransferObjects.IWeightControlDTO;
import dataTransferObjects.ProductBatchDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class ProductBatchDAO implements IProductBatchDAO, IWeightControlDAO {

	static Hashtable<Integer, ProductBatchDTO> productBatchList = new Hashtable<Integer, ProductBatchDTO>();

	@Override
	public ProductBatchDTO getProductBatch(int pbId) throws DALException {
		
		if(productBatchList.get(pbId) != null)
			return productBatchList.get(pbId).copy();
	
		else
			throw new DALException("Unknown Product Batch ID: " + pbId);
		
	}
	

	@Override
	public List<ProductBatchDTO> getProductBatchList() throws DALException {
		List<ProductBatchDTO> productbs = new ArrayList<ProductBatchDTO>();

		Set<Integer> keys = productBatchList.keySet();
		
		for(Integer key : keys){
			productbs.add(productBatchList.get(key).copy());
		}

		return productbs;
	}
	

	@Override
	public void createProductBatch(ProductBatchDTO productBatch) throws DALException {
		if (productBatchList.putIfAbsent(productBatch.getPbId(), productBatch.copy()) == null)
			return;
		
		else
			throw new CollisionException("Product Batch ID:"+productBatch.getPbId()+" already exists !");

	}

	@Override
	public void updateProductBatch(ProductBatchDTO productBatch) throws DALException {
		productBatchList.replace(productBatch.getPbId(), productBatch.copy());

	}


	@Override
	public IWeightControlDTO getDTOById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
