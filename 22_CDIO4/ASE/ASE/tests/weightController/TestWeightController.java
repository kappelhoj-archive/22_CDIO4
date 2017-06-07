package ASE.tests.weightController;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Controllers.WeightController;
import dataTransferObjects.ProductBatchDTO;
import dataTransferObjects.RawMaterialBatchDTO;
import dataTransferObjects.UserDTO;

public class TestWeightController {
	WeightController wCon;
	MeasurementControllerStub measureCon;
	WeightCommunicatorStub comStub;
	
	WeightControlDAOStub userDAO;
	WeightControlDAOStub rbDAO;
	WeightControlDAOStub pbDAO;
	
	Thread myThread;

	@Before
	public void setUp() throws Exception {
		measureCon=new MeasurementControllerStub();
		comStub=new WeightCommunicatorStub();
		wCon=new WeightController(measureCon, comStub);
		myThread=new Thread(wCon);

		
	}

	@After
	public void tearDown() throws Exception {
		userDAO=null;
		rbDAO=null;
		pbDAO=null;
		comStub=null;
		measureCon=null;
		wCon=null;
	}

	@Test
	public void successTest() {
		UserDTO user = new UserDTO(11, "test per", "TP", "1111111118", "jFUe9UFEOwiejfe", "Nobody");
		RawMaterialBatchDTO rb = new RawMaterialBatchDTO(10, 5, 200);
		ProductBatchDTO pb = new ProductBatchDTO(1024, 1, 24);
		userDAO=new WeightControlDAOStub(user);
		rbDAO=new WeightControlDAOStub(rb);
		pbDAO=new WeightControlDAOStub(pb);
		
		wCon.setDAO(userDAO, rbDAO, pbDAO);
		
		//TODO: Setup communication.
		
		myThread.start();
	}
	
	@Test
	public void backButtonTest() {
		
	}
	
	@Test
	public void logoutMidSessionTest() {
		
	}

}
