package ASE.Controllers;

import java.util.LinkedList;
import java.util.Queue;

import ASE.DTOs.MeasurementDTO;
import ASE.interfaces.IMeasurementController;
import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.DALException;

public class MeasurementController implements IMeasurementController, Runnable {

	ProductBatchCompDTO temp;
	Queue<ProductBatchCompDTO> measurements;
	public IProductBatchCompDAO productBatchComp;

	public MeasurementController(IProductBatchCompDAO produktBatchComp) {
		this.productBatchComp =produktBatchComp;
		this.measurements = new LinkedList<ProductBatchCompDTO>();
	}

	public void run() {
		while (true) {
			if (measurements != null) {
				dequeue();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}


	public void dequeue() {
		while (measurements.size()>0) {
			try {

				temp = measurements.remove();
				productBatchComp.createProductBatchComp(temp);

			} catch (DALException e) {
				measurements.add(temp);
				// TODO Handle exceptions correctly (Waiting for Peter)
			}

		}
	}

	@Override
	public void enqueue(MeasurementDTO measurement) {
		// TODO Auto-generated method stub
		
	}

}