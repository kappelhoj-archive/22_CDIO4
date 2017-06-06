package controller.interfaces;

import java.util.List;

import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.*;

public interface IProductBatchCompController {
	ProduktBatchKompDTO getProductBatchComp(int pbId, int rbId) throws DALException;
	List<ProduktBatchKompDTO> getProductBatchCompList(int pbId) throws DALException;
	List<ProduktBatchKompDTO> getProductBatchCompList() throws DALException;
	void createProductBatchComp(ProduktBatchKompDTO produktbatchkomponent) throws CollisionException, DALException;
	void updateProductBatchComp(ProduktBatchKompDTO produktbatchkomponent) throws DALException;	
}
