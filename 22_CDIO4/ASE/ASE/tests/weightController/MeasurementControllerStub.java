package ASE.tests.weightController;

import ASE.DTOs.MeasurementDTO;
import ASE.interfaces.IMeasurementController;
import dataTransferObjects.ProductBatchCompDTO;

public class MeasurementControllerStub implements IMeasurementController {
	MeasurementDTO measurement;

	
	public MeasurementDTO getMeasurement(){
		return measurement;
	}


	@Override
	public void enqueue(MeasurementDTO measurement) {
		this.measurement=measurement;
	}

}
