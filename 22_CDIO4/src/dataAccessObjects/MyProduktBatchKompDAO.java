package dataAccessObjects;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class MyProduktBatchKompDAO implements IProductBatchCompDAO {

	static Hashtable<Integer, ProductBatchCompDTO> productBatchCompList = new Hashtable<Integer, ProductBatchCompDTO>();

	@Override
	public ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException {

		if(productBatchCompList.get(pbId) != null && productBatchCompList.get(pbId).getRbId() == rbId)
			return productBatchCompList.get(new DoubleInteger(pbId, rbId)).copy();

		else
			throw new DALException("Unknown Product Batch Comp ID: " + new DoubleInteger(pbId, rbId));
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList(int pbId) throws DALException {
		//List<ProduktBatchKompDTO> productbsc = new ArrayList<ProduktBatchKompDTO>();

		//Set<DoubleInteger> keys = productBatchCompList.keySet();

		//for(DoubleInteger key : keys){
			//if(productBatchCompList.get(key).getPbId() == pbId)
				//productbsc.add(productBatchCompList.get(key).copy());
		//}

		//return productbsc;
		
		return null;
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
	//	List<ProduktBatchKompDTO> productbsc = new ArrayList<ProduktBatchKompDTO>();
//
	//	Set<DoubleInteger> keys = productBatchCompList.keySet();

		//for(DoubleInteger key : keys){
			//productbsc.add(productBatchCompList.get(key).copy());
		//}

		//return productbsc;
	return null;
	
	}

	@Override
	public void createProductBatchComp(ProductBatchCompDTO produktbatchkomponent) throws DALException {

		if (productBatchCompList.putIfAbsent(produktbatchkomponent.getPbId(), produktbatchkomponent.copy()) == null)
			return;

		else
			throw new CollisionException("Product Batch ID:"+produktbatchkomponent.getPbId()+" already exists !");


	}

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO produktbatchkomponent) throws DALException {
		//productBatchCompList.replace(produktbatchkomponent.getPbId(), produktbatchkomponent.getRbId()), produktbatchkomponent.copy());

	}

}
