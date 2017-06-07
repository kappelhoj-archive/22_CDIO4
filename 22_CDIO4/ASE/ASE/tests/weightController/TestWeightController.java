package ASE.tests.weightController;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Controllers.WeightController;
import ASE.interfaces.IWeightCommunicator.Buttons;
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
		measureCon = new MeasurementControllerStub();
		comStub = new WeightCommunicatorStub();
		wCon = new WeightController(measureCon, comStub);
		myThread = new Thread(wCon);

	}

	@After
	public void tearDown() throws Exception {
		userDAO = null;
		rbDAO = null;
		pbDAO = null;
		comStub = null;
		measureCon = null;
		wCon = null;
	}

	@Test
	public void successTest() {
		UserDTO user = new UserDTO(11, "Test Per", "TP", "1111111118", "jFUe9UFEOwiejfe", "Nobody");
		RawMaterialBatchDTO rb = new RawMaterialBatchDTO(10, 5, 200);
		ProductBatchDTO pb = new ProductBatchDTO(1024, 1, 24);
		userDAO = new WeightControlDAOStub(user);
		rbDAO = new WeightControlDAOStub(rb);
		pbDAO = new WeightControlDAOStub(pb);

		wCon.setDAO(userDAO, rbDAO, pbDAO);

		// Indtast bruger ID
		comStub.textFromWeight.add("11");
		// Bekræft bruger.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast pb ID
		comStub.textFromWeight.add("1024");
		// Bekræft recept id.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		
		// Indtast rb ID
		comStub.textFromWeight.add("10");
		// Bekræft råvare ID.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		
		//Foretag Afvejning:
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.measurementFromWeight.add(0.0);
		comStub.measurementFromWeight.add(5.0);
		comStub.measurementFromWeight.add(100.0);
		

		myThread.start();
		
		while(measureCon.getMeasurement()==null);
		myThread.interrupt();
		
	}

	@Test
	public void backButtonTest() {

	}

	@Test
	public void logoutMidSessionTest() {

	}

}
