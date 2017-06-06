package dataAccessObjects.interfaces;

import dataTransferObjects.IWeightControlDTO;

/*This interface should be used on DAOs that are used by the weight.*/
public interface IWeightControlDAO {
	public IWeightControlDTO getDTOById(int Id);
}
