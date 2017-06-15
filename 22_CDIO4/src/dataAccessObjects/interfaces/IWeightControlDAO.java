package dataAccessObjects.interfaces;

import dataTransferObjects.IWeightControlDTO;
import exceptions.DALException;

/*This interface should be used on DAOs that are used by the weight.*/
public interface IWeightControlDAO {
	public IWeightControlDTO getDTOById(int Id) throws DALException;
}
