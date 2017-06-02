package ASE.interfaces;

import ASE.DTOs.MeasurementDTO;

public interface IMeasurementCont {

	public void enqueue(MeasurementDTO measurement);
	
}
