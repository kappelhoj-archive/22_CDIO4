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
	
	@SuppressWarnings("unchecked")
	public RecipeCompDAO(){
		try{
			System.out.println("Retrieving RecipeComp Data...");
			recipeCompList = (ArrayList<RecipeCompDTO>) FileManagement.retrieveData(TypeOfData.RECIPECOMP);
			System.out.println("Done.");

		}catch(Exception e){
			System.out.println(e);
			System.out.println("Trying to create the saving file...");
			FileManagement.writeData(recipeCompList, TypeOfData.RECIPECOMP);
			System.out.println("Done.");
		}
	}

	@Override
	public RecipeCompDTO getRecipeComp(int recipeId, int rawMaterialId) throws DALException {
		for(RecipeCompDTO recipecomp : recipeCompList){

			if(recipecomp.getRecipeId() == recipeId && recipecomp.getRawMaterialId() == rawMaterialId)
				return recipecomp.copy();
		}
		throw new DALException("Unknown Recipe Comp ID: " + recipeId + " " + rawMaterialId);
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList(int recipeId) throws DALException {
		List<RecipeCompDTO> recipecompListget = new ArrayList<RecipeCompDTO>();

		for(RecipeCompDTO recipecomp : recipeCompList){

			if(recipecomp.getRecipeId() == recipeId)
				recipecompListget.add(recipecomp);

		}

		return recipecompListget;
	}

	@Override
	public List<RecipeCompDTO> getRecipeCompList() throws DALException {
		return recipeCompList;
	}

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

	@Override
	public void updateRecipeComp(RecipeCompDTO recipeComponent) throws DALException {
		getRecipeComp(recipeComponent.getRecipeId(), recipeComponent.getRawMaterialId());

		for(RecipeCompDTO recipecomp : recipeCompList){
			if(recipecomp.getRecipeId() == recipeComponent.getRecipeId() 
					&& recipecomp.getRawMaterialId() == recipeComponent.getRawMaterialId()){

				recipeCompList.remove(recipecomp);
				recipeCompList.add(recipeComponent.copy());
				FileManagement.writeData(recipeCompList, TypeOfData.RECIPECOMP);

			}
		}

	}

}
