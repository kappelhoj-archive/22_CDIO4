package ASE.Controllers;

import java.util.LinkedList;
import java.util.Queue;

import ASE.DTOs.MeasurementDTO;
import ASE.interfaces.IMeasurementController;
import dataAccessObjects.interfaces.IProductBatchCompDAO;
import dataAccessObjects.interfaces.IProductBatchDAO;
import dataTransferObjects.ProductBatchCompDTO;
import dataTransferObjects.ProductBatchDTO;
import exceptions.DALException;

public class MeasurementController implements IMeasurementController, Runnable {

	MeasurementDTO temp;
	Queue<MeasurementDTO> measurements;
	IProductBatchCompDAO productBatchCompDAO;
	IProductBatchDAO productBatchDAO;

	public MeasurementController(IProductBatchCompDAO produktBatchComp,IProductBatchDAO productBatchDAO) {
		this.productBatchCompDAO = produktBatchComp;
		this.measurements = new LinkedList<MeasurementDTO>();
		this.productBatchDAO=productBatchDAO;
	}

	/**
	 * This method keeps the Measurement controller running forever.
	 */
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

	/**
	 * This method process the top MeasurementDTO in the queue.
	 */
	public void dequeue() {
		while (measurements.size() > 0) {

			temp = measurements.remove();
			for (ProductBatchCompDTO comp : temp.getMeasurements())
				try {
					productBatchCompDAO.createProductBatchComp(comp);
					System.out.println(comp);
				} catch (DALException e) {
					System.out.println("Could not add ProductBatchComp: " + comp);
					e.printStackTrace();
				}

			try {
				ProductBatchDTO productBatchDTO =productBatchDAO.getProductBatch(temp.getPbId());
				productBatchDTO.setStatus(temp.getNewStatus());
				productBatchDAO.updateProductBatch(productBatchDTO);
				
			} catch (DALException e) {
				System.out.println("Something went wrong when changing status of productbatch : " + temp.getPbId());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Add a measurement DTO to the queue.
	 */
	@Override
	public void enqueue(MeasurementDTO measurement) {
		measurements.add(measurement);
	}

}