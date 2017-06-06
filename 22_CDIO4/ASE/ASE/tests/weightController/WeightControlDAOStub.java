package ASE.tests.weightController;

import dataAccessObjects.interfaces.IWeightControlDAO;
import dataTransferObjects.IWeightControlDTO;

public class WeightControlDAOStub implements IWeightControlDAO {
	
	IWeightControlDTO weightControlDTO;
	WeightControlDAOStub(IWeightControlDTO weightControlDTO) {
		this.weightControlDTO=weightControlDTO;
	}
	
	@Override
	public IWeightControlDTO getDTOById(int Id) {
		return weightControlDTO;
	}

}
