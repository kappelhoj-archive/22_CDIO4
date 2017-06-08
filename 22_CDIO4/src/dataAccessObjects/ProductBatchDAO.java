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
import staticClasses.FileManagement;
import staticClasses.FileManagement.TypeOfData;

public class ProductBatchDAO implements IProductBatchDAO, IWeightControlDAO {

	static Hashtable<Integer, ProductBatchDTO> productBatchList = new Hashtable<Integer, ProductBatchDTO>();
	
	@SuppressWarnings("unchecked")
	public ProductBatchDAO(){
		try{
			System.out.println("Retrieving ProductBatch Data...");
			productBatchList = (Hashtable<Integer, ProductBatchDTO>) FileManagement.retrieveData(TypeOfData.PRODUCTBATCH);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println(e);
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(productBatchList, TypeOfData.PRODUCTBATCH);
			System.out.println("Done.");
		}
	}
	
	
	/**
	 * Method which returns a copy of a ProductBatchDTO from the data
	 * @param pbId
	 * @return ProductBatchDTO
	 */
	@Override
	public ProductBatchDTO getProductBatch(int pbId) throws DALException {
		
		if(productBatchList.get(pbId) != null)
			return productBatchList.get(pbId).copy();
	
		else
			throw new DALException("Unknown Product Batch ID: " + pbId);
		
	}
	
	/**
	 * Method which returns a list of ProductBatchDTOs from the data
	 * @return List<ProductBatchDTO>
	 */
	@Override
	public List<ProductBatchDTO> getProductBatchList() throws DALException {
		List<ProductBatchDTO> productbs = new ArrayList<ProductBatchDTO>();

		Set<Integer> keys = productBatchList.keySet();
		
		for(Integer key : keys){
			productbs.add(productBatchList.get(key).copy());
		}

		return productbs;
	}
	
	/**
	 * Method which adds a ProductBatchDTO to the saved data
	 * @param ProductBatchDTO
	 * @return void
	 */
	@Override
	public void createProductBatch(ProductBatchDTO productBatch) throws DALException {
		if (productBatchList.putIfAbsent(productBatch.getPbId(), productBatch.copy()) == null){
			FileManagement.writeData(productBatchList, TypeOfData.PRODUCTBATCH);
			return;
		}
		
		else
			throw new CollisionException("Product Batch ID:"+productBatch.getPbId()+" already exists !");

	}

	/**
	 * Method which updates a ProductBatchDTO in the saved data
	 * @param ProductBatchDTO
	 * @return void
	 */
	@Override
	public void updateProductBatch(ProductBatchDTO productBatch) throws DALException {
		productBatchList.replace(productBatch.getPbId(), productBatch.copy());
		FileManagement.writeData(productBatchList, TypeOfData.PRODUCTBATCH);
	}


	@Override
	public IWeightControlDTO getDTOById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
