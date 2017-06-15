package ASE.interfaces;

import ASE.DTOs.MeasurementDTO;

public interface IMeasurementController {

	public void enqueue(MeasurementDTO measurement);
	
}
