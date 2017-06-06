package controller;

import java.util.List;

import controller.interfaces.IRecipeCompController;
import dataAccessObjects.MyReceptKompDAO;
import dataAccessObjects.interfaces.ReceptKompDAO;
import dataTransferObjects.ReceptKompDTO;
import exceptions.DALException;

public class RecipeCompController implements IRecipeCompController {
	
	ReceptKompDAO dao = new MyReceptKompDAO();

	@Override
	public ReceptKompDTO getRecipeComp(int receptId, int raavareId) throws DALException {
		return dao.getReceptKomp(receptId, raavareId);
	}

	@Override
	public List<ReceptKompDTO> getRecipeComp(int receptId) throws DALException {
		return dao.getReceptKompList(receptId);
	}

	@Override
	public List<ReceptKompDTO> getRecipeCompList() throws DALException {
		return dao.getReceptKompList();
	}

	@Override
	public void createRecipeComp(ReceptKompDTO receptkomponent) throws DALException {
		dao.createReceptKomp(receptkomponent);

	}

	@Override
	public void updateRecipeComp(ReceptKompDTO receptkomponent) throws DALException {
		dao.updateReceptKomp(receptkomponent);

	}

}
