package ASE.Controllers;

import java.util.LinkedList;
import java.util.Queue;

import ASE.interfaces.IMeasurementController;
import dataAccessObjects.ProductBatchCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.DALException;

public class MeasurementController implements IMeasurementController, Runnable {

	ProductBatchCompDTO temp;
	Queue<ProductBatchCompDTO> measurements;
	public ProductBatchCompDAO produktBatchKomp;

	public MeasurementController(ProductBatchCompDAO produktBatchKomp) {
		this.produktBatchKomp =produktBatchKomp;
		this.measurements = new LinkedList<ProductBatchCompDTO>();
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

	public void enqueue(ProductBatchCompDTO measurement) {
		measurements.add(measurement);
	}

	public void dequeue() {
		while (measurements.size() != 0) {
			try {
				temp = measurements.remove();
				produktBatchKomp.createProductBatchComp(temp);

			} catch (DALException e) {
				measurements.add(temp);
				// TODO Handle exceptions correctly (Waiting for Peter)
			}

		}
	}

}