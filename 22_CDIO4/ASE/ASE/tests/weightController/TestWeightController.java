package ASE.tests.weightController;

import static org.junit.Assert.*;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ASE.Controllers.WeightController;
import ASE.interfaces.IWeightCommunicator.Buttons;
import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataTransferObjects.ProductBatchCompDTO;
import dataTransferObjects.ProductBatchDTO;
import dataTransferObjects.RawMaterialBatchDTO;
import dataTransferObjects.RecipeCompDTO;
import dataTransferObjects.UserDTO;

class ThreadStopHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (e instanceof NoSuchElementException) {
			System.out.println("Thread stopped because the test class had no more inputs.");
			e.printStackTrace();
		}

	}

}

public class TestWeightController {
	WeightController wCon;
	MeasurementControllerStub measureCon;
	WeightCommunicatorStub comStub;

	WeightControlDAOStub userDAO;
	WeightControlDAOStub rbDAO;
	WeightControlDAOStub pbDAO;
	IRecipeCompDAO rcDAO;

	Thread myThread;
	ThreadStopHandler threadStopper = new ThreadStopHandler();

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
		RecipeCompDTO rc = new RecipeCompDTO(24, 5, 100, 1);
		userDAO = new WeightControlDAOStub(user);
		rbDAO = new WeightControlDAOStub(rb);
		pbDAO = new WeightControlDAOStub(pb);
		rcDAO = new RecipeCompDAOStub(rc);

		wCon.setDAO(userDAO, rbDAO, pbDAO, rcDAO);

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

		// Foretag Afvejning:
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.measurementFromWeight.add(0.0);
		comStub.measurementFromWeight.add(5.0);
		comStub.measurementFromWeight.add(100.0);
		comStub.measurementFromWeight.add(-105.0);

		myThread.setUncaughtExceptionHandler(threadStopper);
		myThread.start();

		while (measureCon.getMeasurement() == null)
			;

		ProductBatchCompDTO expected = new ProductBatchCompDTO(pb.getPbId(), rb.getRbId(), 5, 100, 11);
		ProductBatchCompDTO actual = measureCon.getMeasurement();

		assertTrue(expected.equals(actual));

	}

	@Test
	public void backButtonTest() {
		UserDTO user = new UserDTO(11, "Test Per", "TP", "1111111118", "jFUe9UFEOwiejfe", "Nobody");
		RawMaterialBatchDTO rb = new RawMaterialBatchDTO(10, 5, 200);
		ProductBatchDTO pb = new ProductBatchDTO(1024, 1, 24);
		RecipeCompDTO rc = new RecipeCompDTO(24, 5, 100, 1);
		userDAO = new WeightControlDAOStub(user);
		rbDAO = new WeightControlDAOStub(rb);
		pbDAO = new WeightControlDAOStub(pb);
		rcDAO = new RecipeCompDAOStub(rc);

		wCon.setDAO(userDAO, rbDAO, pbDAO, rcDAO);

		// Indtast bruger ID
		comStub.textFromWeight.add("11");
		// Gå tilbage
		comStub.buttonsPresssedOnWeight.add(Buttons.BACK);

		// Indtast bruger ID
		comStub.textFromWeight.add("11");
		// Bekræft bruger.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast pb ID
		comStub.textFromWeight.add("1024");
		// Gå tilbage
		comStub.buttonsPresssedOnWeight.add(Buttons.BACK);
		// Indtast pb ID
		comStub.textFromWeight.add("1024");
		// Bekræft recept ID
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast rb ID
		comStub.textFromWeight.add("10");
		// Gå tilbage
		comStub.buttonsPresssedOnWeight.add(Buttons.BACK);

		// Indtast rb ID
		comStub.textFromWeight.add("10");
		// Bekræft råvare ID.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Foretag Afvejning:
		comStub.buttonsPresssedOnWeight.add(Buttons.BACK);

		// Indtast pb ID
		comStub.textFromWeight.add("1024");
		// Bekræft recept ID
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast rb ID
		comStub.textFromWeight.add("10");
		// Bekræft råvare ID.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		comStub.measurementFromWeight.add(0.0);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		comStub.measurementFromWeight.add(5.0);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		comStub.measurementFromWeight.add(100.0);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		comStub.measurementFromWeight.add(-105.0);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		myThread.setUncaughtExceptionHandler(threadStopper);
		myThread.start();

		while (measureCon.getMeasurement() == null)
			;

		ProductBatchCompDTO expected = new ProductBatchCompDTO(pb.getPbId(), rb.getRbId(), 5, 100, 11);
		ProductBatchCompDTO actual = measureCon.getMeasurement();

		assertTrue(expected.equals(actual));
	}

	@Test
	public void logoutMidSessionTest() {
		UserDTO user = new UserDTO(11, "Test Per", "TP", "1111111118", "jFUe9UFEOwiejfe", "Nobody");
		RawMaterialBatchDTO rb = new RawMaterialBatchDTO(10, 5, 200);
		ProductBatchDTO pb = new ProductBatchDTO(1024, 1, 24);
		RecipeCompDTO rc = new RecipeCompDTO(24, 5, 100, 1);
		userDAO = new WeightControlDAOStub(user);
		rbDAO = new WeightControlDAOStub(rb);
		pbDAO = new WeightControlDAOStub(pb);
		rcDAO = new RecipeCompDAOStub(rc);

		wCon.setDAO(userDAO, rbDAO, pbDAO, rcDAO);

		// Indtast bruger ID
		comStub.textFromWeight.add("11");
		// Bekræft bruger.
		comStub.buttonsPresssedOnWeight.add(Buttons.LOGOUT);
		

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
		comStub.buttonsPresssedOnWeight.add(Buttons.LOGOUT);
		
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

		// Foretag Afvejning:
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.measurementFromWeight.add(0.0);
		comStub.measurementFromWeight.add(5.0);
		comStub.measurementFromWeight.add(100.0);
		comStub.measurementFromWeight.add(-105.0);

		myThread.setUncaughtExceptionHandler(threadStopper);
		myThread.start();

		while (measureCon.getMeasurement() == null)
			;

		ProductBatchCompDTO expected = new ProductBatchCompDTO(pb.getPbId(), rb.getRbId(), 5, 100, 11);
		ProductBatchCompDTO actual = measureCon.getMeasurement();

		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void toleraceTest() {
		UserDTO user = new UserDTO(11, "Test Per", "TP", "1111111118", "jFUe9UFEOwiejfe", "Nobody");
		RawMaterialBatchDTO rb = new RawMaterialBatchDTO(10, 5, 200);
		ProductBatchDTO pb = new ProductBatchDTO(1024, 1, 24);
		RecipeCompDTO rc = new RecipeCompDTO(24, 5, 100, 1);
		userDAO = new WeightControlDAOStub(user);
		rbDAO = new WeightControlDAOStub(rb);
		pbDAO = new WeightControlDAOStub(pb);
		rcDAO = new RecipeCompDAOStub(rc);

		wCon.setDAO(userDAO, rbDAO, pbDAO, rcDAO);

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

		// Foretag Afvejning:
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.measurementFromWeight.add(0.0);
		comStub.measurementFromWeight.add(5.0);
		comStub.measurementFromWeight.add(100.0);
		comStub.measurementFromWeight.add(-120.0);
		
		//Bekræft fejl
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		
		// Indtast pb ID
		comStub.textFromWeight.add("1024");
		// Bekræft recept id.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast rb ID
		comStub.textFromWeight.add("10");
		// Bekræft råvare ID.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Foretag Afvejning:
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		comStub.measurementFromWeight.add(0.0);
		comStub.measurementFromWeight.add(5.0);
		comStub.measurementFromWeight.add(100.0);
		comStub.measurementFromWeight.add(-105.0);

		myThread.setUncaughtExceptionHandler(threadStopper);
		myThread.start();

		while (measureCon.getMeasurement() == null)
			;

		ProductBatchCompDTO expected = new ProductBatchCompDTO(pb.getPbId(), rb.getRbId(), 5, 100, 11);
		ProductBatchCompDTO actual = measureCon.getMeasurement();

		assertTrue(expected.equals(actual));
	}

}
