package dataAccessObjects.interfaces;

import dataTransferObjects.DTO;

/*This interface should be used on DAOs that are used by the weight.*/
public interface IWeightControlDAO {
	public DTO getDTOById(int Id);
}
