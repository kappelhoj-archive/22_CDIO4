package ASE.interfaces;

import dataTransferObjects.ProduktBatchKompDTO;

public interface IMeasurementController {

	public void enqueue(ProduktBatchKompDTO measurement);
	
}
