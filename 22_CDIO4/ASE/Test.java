import ASE.Controllers.MeasurementController;
import dataTransferObjects.ProduktBatchKompDTO;

public class Test {

	public static void main(String[] args) {
		MeasurementController test = new MeasurementController();
		(new Thread(test)).start();
		ProduktBatchKompDTO measurement = new ProduktBatchKompDTO(1, 1, 1.1, 1.1, 1);
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
