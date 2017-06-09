package ASE.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ASE.Controllers.MeasurementController;
import ASE.DTOs.MeasurementDTO;
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
		MeasurementController test = new MeasurementController(produktBatchComp,new ProductBatchStub());
		Queue<ProductBatchCompDTO> expectedMeasurements = new LinkedList<ProductBatchCompDTO>();
		(new Thread(test)).start();
		int pbId=1;
		int rbId1=1;

		int rbId2=4;
		
		ProductBatchCompDTO measurement1 = new ProductBatchCompDTO(pbId, rbId1, 1.1, 1.1, 1);
		ProductBatchCompDTO measurement2 = new ProductBatchCompDTO(pbId, rbId2, 2.0, 1.1, 1);
		ArrayList<ProductBatchCompDTO> measurements= new ArrayList<ProductBatchCompDTO>();
		measurements.add(measurement1);
		measurements.add(measurement2);
		MeasurementDTO product=new MeasurementDTO(2, pbId, measurements);
		
		for(int i =0; i<5 ; i++)
		{
			expectedMeasurements.add(measurement1);
			expectedMeasurements.add(measurement2);
			test.enqueue(product);
		}
		//This is just to let the other thread keep up, as it has a thread sleep, to prevent unnesceary CPU usage.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try 
		{	
			int i=1;
			while(!expectedMeasurements.isEmpty())
			{
				System.out.println("Emptied batch number: "+i++);
			assertEquals(expectedMeasurements.remove(),produktBatchComp.getProductBatchComp(pbId,0));
			}
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
