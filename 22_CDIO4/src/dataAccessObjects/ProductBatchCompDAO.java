package dataAccessObjects;


import java.util.ArrayList;
import java.util.List;

import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;

public class ProductBatchCompDAO implements IProductBatchCompDAO {

	static List<ProductBatchCompDTO> productBatchCompList = new ArrayList<ProductBatchCompDTO>();

	@Override
	public ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException {

		for(ProductBatchCompDTO productbatchcomp : productBatchCompList){

			if(productbatchcomp.getPbId() == pbId && productbatchcomp.getRbId() == rbId)
				return productbatchcomp.copy();
		}
		throw new DALException("Unknown Product Batch Comp ID: " + pbId + " " + rbId);
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList(int pbId) throws DALException {
		List<ProductBatchCompDTO> productBatchCompListget = new ArrayList<ProductBatchCompDTO>();

		for(ProductBatchCompDTO productbatchcomp : productBatchCompList){

			if(productbatchcomp.getPbId() == pbId)
				productBatchCompListget.add(productbatchcomp);

		}

		return productBatchCompListget;
	}

	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
		return productBatchCompList;

	}

	@Override
	public void createProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {

		try{
			getProductBatchComp(productBatchComponent.getPbId(), productBatchComponent.getRbId());

		}catch (DALException e){ //if it can not find it, so it can create it
			productBatchCompList.add(productBatchComponent.copy());
			return;
		}
		//if it can find it, so it already exists
		throw new CollisionException(productBatchComponent + " already exists !");


	}

	@Override
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {

		getProductBatchComp(productBatchComponent.getPbId(), productBatchComponent.getRbId());

		for(ProductBatchCompDTO productbatchcomp : productBatchCompList){
			if(productbatchcomp.getPbId() == productBatchComponent.getPbId() 
					&& productbatchcomp.getRbId() == productBatchComponent.getRbId()){

				productBatchCompList.remove(productbatchcomp);
				productBatchCompList.add(productBatchComponent.copy());

			}
		}


	}

}
