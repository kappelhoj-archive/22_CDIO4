package controller.interfaces;

import java.util.List;

import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.*;

public interface IProductBatchCompController {
	ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws InputException, DALException;
	List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException;
	List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException;
	void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws CollisionException, InputException, DALException;
	void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws InputException, DALException;	
}
