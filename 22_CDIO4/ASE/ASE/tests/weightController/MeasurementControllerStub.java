package ASE.tests.weightController;

import ASE.interfaces.IMeasurementController;
import dataTransferObjects.ProduktBatchKompDTO;

public class MeasurementControllerStub implements IMeasurementController {
	ProduktBatchKompDTO measurement;
	@Override
	public void enqueue(ProduktBatchKompDTO measurement) {
		this.measurement=measurement;
	}
	
	public ProduktBatchKompDTO getMeasurement(){
		return measurement;
	}

}
