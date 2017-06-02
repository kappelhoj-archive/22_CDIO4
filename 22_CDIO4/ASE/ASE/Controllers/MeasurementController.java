package ASE.Controllers;

import java.util.Queue;

import ASE.DTOs.MeasurementDTO;
import ASE.interfaces.IMeasurementCont;

public class MeasurementController implements IMeasurementCont, Runnable {
	Queue<MeasurementDTO> measurements;
	
	@Override
	public void enqueue(MeasurementDTO measurement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	
}
