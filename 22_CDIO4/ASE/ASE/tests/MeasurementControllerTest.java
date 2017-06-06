package ASE.tests;
import ASE.Controllers.MeasurementController;
import dataTransferObjects.ProductBatchCompDTO;


public class MeasurementControllerTest {

	public static void main(String[] args) {
		MeasurementController test = new MeasurementController();
		(new Thread(test)).start();
		ProductBatchCompDTO measurement = new ProductBatchCompDTO(1, 1, 1.1, 1.1, 1);

		System.out.println(measurement);
		test.enqueue(measurement);

	}

}
