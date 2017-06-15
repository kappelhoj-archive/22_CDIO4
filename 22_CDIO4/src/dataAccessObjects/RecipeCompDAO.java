package dataAccessObjects;

import java.util.ArrayList;
import java.util.List;

import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataTransferObjects.RecipeCompDTO;
import exceptions.CollisionException;
import exceptions.DALException;
import staticClasses.FileManagement;
import staticClasses.FileManagement.TypeOfData;

public class RecipeCompDAO implements IRecipeCompDAO {

	static List<RecipeCompDTO> recipeCompList = new ArrayList<RecipeCompDTO>();

	/*
	 * The warning "unchecked" is there because Java can not define if the file we try to convert
	 * to an ArrayList is associated to this class.
	 * We decided to ignore this warning in all our DAO.
	 */
	@SuppressWarnings("unchecked")
	public RecipeCompDAO(){
		try{
			System.out.println("Retrieving RecipeComp Data...");
			recipeCompList = (ArrayList<RecipeCompDTO>) FileManagement.retrieveData(TypeOfData.RECIPECOMP);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(recipeCompList, TypeOfData.RECIPECOMP);
			System.out.println("Done.");
		}
	}

	/**
	 * Method which returns a copy of a RecipeCompDTO from the data
	 * @param recipeId, rawMaterialId
	 * @return RecipeCompDTO
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public RecipeCompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException {
		for(RecipeCompDTO recipecomp : recipeCompList){

			if(recipecomp.getRecipeId() == recipeId && recipecomp.getRawMaterialId() == rawMaterialId)
				return recipecomp.copy();
		}
		throw new DALException("Unknown Recipe Comp ID: " + recipeId + " " + rawMaterialId);
	}

	/**
	 * Method which returns a list of RecipeCompDTOs from the data
	 * @param recipeId
	 * @return List<RecipeCompDTO>
	 */
	@Override
	public List<RecipeCompDTO> getRecipeCompList(int recipeId) throws DALException {
		List<RecipeCompDTO> recipecompListget = new ArrayList<RecipeCompDTO>();

		for(RecipeCompDTO recipecomp : recipeCompList){

			if(recipecomp.getRecipeId() == recipeId)
				recipecompListget.add(recipecomp);

		}

		return recipecompListget;
	}

	/** Method which returns a list of RecipeCompDTOs from the data
	 * @return List<RecipeCompDTO>
	 */
	@Override
	public List<RecipeCompDTO> getRecipeCompList() throws DALException {
		return recipeCompList;
	}

	/**
	 * Method which adds a RecipeCompDTO to the saved data
	 * @param RecipeCompDTO
	 * @return void
	 * @throws CollisionException if the DTO it shall insert already exists
	 */
	@Override
	public void createRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		try{
			getRecipeComp(recipeComponent.getRecipeId(), recipeComponent.getRawMaterialId());

		}catch (DALException e){ //if it can not find it, so it can create it
			recipeCompList.add(recipeComponent.copy());
			FileManagement.writeData(recipeCompList, TypeOfData.RECIPECOMP);
			return;
		}
		//if it can find it, so it already exists
		throw new CollisionException(recipeComponent + " already exists !");
	}

	/**
	 * Method which updates a RecipeCompDTO in the saved data
	 * @param RecipeCompDTO
	 * @return void
	 * @throws DALException if the DTO with the param ID doesn't exist in the data
	 */
	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		getRecipeComp(recipeComponent.getRecipeId(), recipeComponent.getRawMaterialId());

		int index = -1;
		for(int i = 0; i<recipeCompList.size();++i){
			if(recipeCompList.get(i).getRecipeId() == recipeComponent.getRecipeId() 
					&& recipeCompList.get(i).getRawMaterialId() == recipeComponent.getRawMaterialId()){

				index=i;
				break;
			}
		}

		if(index > -1){
			recipeCompList.remove(index);
			recipeCompList.add(recipeComponent.copy());
			FileManagement.writeData(recipeCompList, TypeOfData.RECIPECOMP);
			return;

		}
		throw new DALException("Fatal Error on RecipeComponent. Both IDs exist but can not be loaded.");
	}

}
