package ASE.tests.weightController;

import ASE.interfaces.IMeasurementController;
import dataTransferObjects.ProductBatchCompDTO;

public class MeasurementControllerStub implements IMeasurementController {
	ProductBatchCompDTO measurement;
	@Override
	public void enqueue(ProductBatchCompDTO measurement) {
		this.measurement=measurement;
	}
	
	public ProductBatchCompDTO getMeasurement(){
		return measurement;
	}

}
