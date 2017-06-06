package controller.interfaces;

import java.util.List;

import dataTransferObjects.ReceptKompDTO;
import exceptions.DALException;

public interface IRecipeCompController {
	ReceptKompDTO getRecipeComp(int receptId, int raavareId) throws DALException;
	List<ReceptKompDTO> getRecipeComp(int receptId) throws DALException;
	List<ReceptKompDTO> getRecipeCompList() throws DALException;
	void createRecipeComp(ReceptKompDTO receptkomponent) throws DALException;
	void updateRecipeComp(ReceptKompDTO receptkomponent) throws DALException;
}
