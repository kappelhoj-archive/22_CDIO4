package ASE.tests;
import ASE.Controllers.MeasurementController;
import dataTransferObjects.ProduktBatchKompDTO;

public class MeasurementControllerTest {

	public static void main(String[] args) {
		MeasurementController test = new MeasurementController();
		(new Thread(test)).start();
		ProduktBatchKompDTO measurement = new ProduktBatchKompDTO(1, 1, 1.1, 1.1, 1);

		System.out.println(measurement);
		test.enqueue(measurement);

	}

}
