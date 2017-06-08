package ASE.tests;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ASE.Controllers.MeasurementController;
import dataTransferObjects.ProductBatchCompDTO;
import exceptions.DALException;

public class MeasurementControllerTest {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ProductBatchCompDAOStub produktBatchComp = new ProductBatchCompDAOStub();
		MeasurementController test = new MeasurementController(produktBatchComp);
		Queue<ProductBatchCompDTO> measurements = new LinkedList<ProductBatchCompDTO>();
		(new Thread(test)).start();
		int pbId = 1;
		int rbId = 1;


		for (int i = 0; i < 1000; i++) {
			ProductBatchCompDTO measurement = new ProductBatchCompDTO(pbId, rbId, 1.1, 1.1, 1);
			System.out.println(measurement);
			measurements.add(measurement);
			test.enqueue(measurement);
			pbId++;
			rbId++;
		}

//		for (int i = 0; i < 5; i++) {
//			System.out.println(measurements.remove() + "efter");
//		}

		// This is just to let the other thread keep up, as it has a thread
		// sleep, to prevent unnesceary CPU usage.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (!measurements.isEmpty()) {
				assertEquals(measurements.remove(), produktBatchComp.getProductBatchComp(pbId, rbId));
			}
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
