package ASE.Controllers;

import java.util.LinkedList;
import java.util.Queue;
import ASE.interfaces.IMeasurementController;
import dataAccessObjects.interfaces.ProduktBatchKompDAO;
import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.DALException;

public class MeasurementController implements IMeasurementController, Runnable {

	ProduktBatchKompDTO temp;
	Queue<ProduktBatchKompDTO> measurements;
	public ProduktBatchKompDAO produktBatchKomp;

	public MeasurementController(ProduktBatchKompDAO produktBatchKomp) {
		this.produktBatchKomp =produktBatchKomp;
		this.measurements = new LinkedList<ProduktBatchKompDTO>();
	}

	public void run() {
		while (true) {
			if (measurements != null) {
				dequeue();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	public void enqueue(ProduktBatchKompDTO measurement) {
		measurements.add(measurement);
	}

	public void dequeue() {
		while (measurements.size() != 0) {
			try {
				temp = measurements.remove();
				produktBatchKomp.createProduktBatchKomp(temp);

			} catch (DALException e) {
				measurements.add(temp);
				// TODO Handle exceptions correctly (Waiting for Peter)
			}

		}
	}

}