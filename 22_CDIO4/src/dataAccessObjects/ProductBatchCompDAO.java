package dataAccessObjects;


import java.util.ArrayList;
import java.util.List;

import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import staticClasses.FileManagement;
import staticClasses.FileManagement.TypeOfData;

public class ProductBatchCompDAO implements IProductBatchCompDAO {

	static List<ProductBatchCompDTO> productBatchCompList = new ArrayList<ProductBatchCompDTO>();
	
	@SuppressWarnings("unchecked")
	public ProductBatchCompDAO(){
		try{
			System.out.println("Retrieving ProductBatchComp Data...");
			productBatchCompList = (ArrayList<ProductBatchCompDTO>) FileManagement.retrieveData(TypeOfData.PRODUCTBATCHCOMP);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println(e);
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(productBatchCompList, TypeOfData.PRODUCTBATCHCOMP);
			System.out.println("Done.");
		}
	}

	/**
	 * Method which returns a copy of a ProductBatchCompDTO from the data
	 * @param productbatchID, rawmaterialbatchId
	 * @return ProductBatchCompDTO
	 */
	@Override
	public ProductBatchCompDTO getProductBatchComp(int pbId, int rbId) throws DALException {

		for(ProductBatchCompDTO productbatchcomp : productBatchCompList){

			if(productbatchcomp.getPbId() == pbId && productbatchcomp.getRbId() == rbId)
				return productbatchcomp.copy();
		}
		throw new DALException("Unknown Product Batch Comp ID: " + pbId + " " + rbId);
	}
	
	/**
	 * Method which returns a list of ProductBatchCompDTOs from the data
	 * @param productbatchID
	 * @return List<ProductBatchCompDTO>
	 */
	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList(int pbId) throws DALException {
		List<ProductBatchCompDTO> productBatchCompListget = new ArrayList<ProductBatchCompDTO>();

		for(ProductBatchCompDTO productbatchcomp : productBatchCompList){

			if(productbatchcomp.getPbId() == pbId)
				productBatchCompListget.add(productbatchcomp);

		}

		return productBatchCompListget;
	}

	/**
	 * Method which returns a list of ProductBatchCompDTOs from the data
	 * @return List<ProductBatchCompDTO>
	 */
	@Override
	public List<ProductBatchCompDTO> getProductBatchCompList() throws DALException {
		return productBatchCompList;

	}

	/**
	 * Method which adds a ProductBatchCompDTO to the saved data
	 * @param ProductBatchCompDTO
	 * @return void
	 */
	@Override
	public void createProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {

		try{
			getProductBatchComp(productBatchComponent.getPbId(), productBatchComponent.getRbId());

		}catch (DALException e){ //if it can not find it, so it can create it
			productBatchCompList.add(productBatchComponent.copy());
			FileManagement.writeData(productBatchCompList, TypeOfData.PRODUCTBATCHCOMP);
			return;
		}
		//if it can find it, so it already exists
		throw new CollisionException(productBatchComponent + " already exists !");


	}

	/**
	 * Method which updates a ProductBatchCompDTO in the saved data
	 * @param ProductBatchCompDTO
	 * @return void
	 */
	@Override
	public void updateProductBatchComp(ProductBatchCompDTO productBatchComponent) throws DALException {

		getProductBatchComp(productBatchComponent.getPbId(), productBatchComponent.getRbId());

		for(ProductBatchCompDTO productbatchcomp : productBatchCompList){
			if(productbatchcomp.getPbId() == productBatchComponent.getPbId() 
					&& productbatchcomp.getRbId() == productBatchComponent.getRbId()){

				productBatchCompList.remove(productbatchcomp);
				productBatchCompList.add(productBatchComponent.copy());
				FileManagement.writeData(productBatchCompList, TypeOfData.PRODUCTBATCHCOMP);

			}
		}


	}

}
