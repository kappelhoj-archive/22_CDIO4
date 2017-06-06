package ASE.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Controllers.MeasurementController;
import dataAccessObjects.MyProduktBatchKompDAO;
import dataAccessObjects.interfaces.ProduktBatchKompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import dataTransferObjects.ProduktBatchKompDTO;
import exceptions.DALException;

public class MeasurementControllerTest1 {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ProductBatchCompDTO produktBatchKomp = new MyProduktBatchKompDAO();
		MeasurementController test = new MeasurementController(produktBatchKomp);
		(new Thread(test)).start();
		int pbId=1;
		int rbId=1;
		ProductBatchCompDTO measurement = new ProductBatchCompDTO(pbId, rbId, 1.1, 1.1, 1);

		test.enqueue(measurement);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {


			System.out.println("test");
			
			System.out.println(produktBatchKomp.getProduktBatchKompList().toString());
			
			assertEquals(measurement,produktBatchKomp.getProduktBatchKomp(pbId,rbId));
		
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}

}
