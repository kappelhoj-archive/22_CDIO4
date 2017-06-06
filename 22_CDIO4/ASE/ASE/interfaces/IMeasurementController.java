package ASE.interfaces;

import dataTransferObjects.ProductBatchCompDTO;

public interface IMeasurementController {

	public void enqueue(ProductBatchCompDTO measurement);
	
}
