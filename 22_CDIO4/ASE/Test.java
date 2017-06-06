import ASE.Controllers.MeasurementController;
import dataTransferObjects.ProductBatchCompDTO;

public class Test {

	public static void main(String[] args) {
		MeasurementController test = new MeasurementController();
		(new Thread(test)).start();
		ProductBatchCompDTO measurement = new ProductBatchCompDTO(1, 1, 1.1, 1.1, 1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(measurement);
		test.enqueue(measurement);

	}

}
