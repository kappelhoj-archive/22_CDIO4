package ASE.tests.weightController;

import static org.junit.Assert.*;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ASE.Controllers.WeightController;
import ASE.DTOs.MeasurementDTO;
import ASE.interfaces.IWeightCommunicator.Buttons;
import controller.Initializer;
import dataAccessObjects.ProductBatchDAO;
import dataAccessObjects.RawMaterialBatchDAO;
import dataAccessObjects.RecipeCompDAO;
import dataAccessObjects.RecipeDAO;
import dataAccessObjects.UserDAO;
import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataAccessObjects.interfaces.IRecipeDAO;
import dataAccessObjects.interfaces.IWeightControlDAO;
import dataTransferObjects.ProductBatchCompDTO;
import dataTransferObjects.ProductBatchDTO;
import dataTransferObjects.RawMaterialBatchDTO;
import dataTransferObjects.RecipeCompDTO;
import dataTransferObjects.RecipeDTO;
import dataTransferObjects.UserDTO;
import exceptions.DALException;

class ThreadStopHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		if (e instanceof NoSuchElementException) {
			System.out.println("Thread stopped because the test class had no more inputs.");
			e.printStackTrace();
		} else {
			System.out.println(e.getMessage());
		}

	}

}

public class TestWeightController {
	WeightController wCon;
	MeasurementControllerStub measureCon;
	WeightCommunicatorStub comStub;

	IWeightControlDAO userDAO;
	IWeightControlDAO rbDAO;
	IWeightControlDAO pbDAO;
	IRecipeCompDAO rcDAO;
	IRecipeDAO rDAO;

	Thread myThread;
	ThreadStopHandler threadStopper = new ThreadStopHandler();

	@BeforeClass
	public static void setUpData() {

		new Initializer().contextInitialized(null);
	}

	@Before
	public void setUp() throws Exception {
		measureCon = new MeasurementControllerStub();
		comStub = new WeightCommunicatorStub();
		wCon = new WeightController(measureCon, comStub);
		myThread = new Thread(wCon);
		userDAO = new UserDAO();
		rbDAO = new RawMaterialBatchDAO();
		pbDAO = new ProductBatchDAO();
		rcDAO = new RecipeCompDAO();
		rDAO = new RecipeDAO();

	}

	@After
	public void tearDown() throws Exception {
		measureCon = null;
		comStub = null;
		wCon = null;
		myThread = null;
		userDAO = null;
		rbDAO = null;
		pbDAO = null;
		rcDAO = null;
		rDAO = null;
	}

	@Test
	public void successTest() {

		wCon.setDAO(userDAO, rbDAO, pbDAO, rcDAO, rDAO);
		UserDTO user = null;
		List<RawMaterialBatchDTO> rb = null;
		ProductBatchDTO pb = null;
		RecipeDTO r = null;
		List<RecipeCompDTO> rcList = null;

		List<ProductBatchCompDTO> expectedMeasurements = new ArrayList<ProductBatchCompDTO>();

		try {
			user = ((UserDAO) userDAO).getUserList().get(0);
			pb = ((ProductBatchDAO) pbDAO).getProductBatchList().get(0);
			rb = ((RawMaterialBatchDAO) rbDAO).getRawMaterialBatchList();
			r = ((RecipeDAO) rDAO).getRecipeList().get(0);
			rcList = ((RecipeCompDAO) rcDAO).getRecipeCompList(r.getRecipeId());

		} catch (DALException e) {
			fail(e.getMessage());
		}

		// Indtast bruger ID
		comStub.textFromWeight.add(user.getId() + "");
		// Bekræft bruger.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast pb ID
		comStub.textFromWeight.add(pb.getPbId() + "");
		// Bekræft recept id.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		// Bekræft recept navn
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		for (RecipeCompDTO localRC : rcList) {
			int rawMaterialID = localRC.getRawMaterialId();
			double taraWeight = 20 + (Math.random() - 0.5);
			double nettoWeight = localRC.getNomNetto();
			int rbID = -1;
			for (RawMaterialBatchDTO rbDTO : rb) {
				if (rbDTO.getRawMaterialId() == rawMaterialID) {
					// Indtast rb ID
					comStub.textFromWeight.add(rbDTO.getRbId() + "");
					rbID = rbDTO.getRbId();
					// Bekræft råvare ID.
					comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
					comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
					break;
				}
			}
			if (rbID == -1) {
				fail("Product does not exist.");
			}
			// Foretag Afvejning:
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

			comStub.measurementFromWeight.add(-10000.0);
			comStub.measurementFromWeight.add(taraWeight);
			comStub.measurementFromWeight.add(nettoWeight);
			comStub.measurementFromWeight.add(-1 * (nettoWeight + taraWeight));

			expectedMeasurements
					.add(new ProductBatchCompDTO(pb.getPbId(), rbID, taraWeight, nettoWeight, user.getId()));

		}
		myThread.setUncaughtExceptionHandler(threadStopper);
		myThread.start();

		while (measureCon.getMeasurement() == null)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		MeasurementDTO actualMeasurement = measureCon.getMeasurement();

		assertEquals(2, actualMeasurement.getNewStatus());

		for (ProductBatchCompDTO actualPBComp : actualMeasurement.getMeasurements()) {
			for (ProductBatchCompDTO expectedPBComp : expectedMeasurements) {
				if (expectedPBComp.getRbId() == actualPBComp.getRbId()) {
					assertTrue(expectedPBComp.equals(actualPBComp));
				}
			}
		}

	}

	@Test
	public void backButtonTest() {
		wCon.setDAO(userDAO, rbDAO, pbDAO, rcDAO, rDAO);
		UserDTO user = null;
		List<RawMaterialBatchDTO> rb = null;
		ProductBatchDTO pb = null;
		RecipeDTO r = null;
		List<RecipeCompDTO> rcList = null;

		List<ProductBatchCompDTO> expectedMeasurements = new ArrayList<ProductBatchCompDTO>();

		try {
			user = ((UserDAO) userDAO).getUserList().get(0);
			pb = ((ProductBatchDAO) pbDAO).getProductBatchList().get(0);
			rb = ((RawMaterialBatchDAO) rbDAO).getRawMaterialBatchList();
			r = ((RecipeDAO) rDAO).getRecipeList().get(0);
			rcList = ((RecipeCompDAO) rcDAO).getRecipeCompList(r.getRecipeId());

		} catch (DALException e) {
			fail(e.getMessage());
		}

		// Indtast bruger ID
		comStub.textFromWeight.add(user.getId() + "");
		// Bekræft bruger.
		comStub.buttonsPresssedOnWeight.add(Buttons.BACK);

		// Indtast bruger ID
		comStub.textFromWeight.add(user.getId() + "");
		// Bekræft bruger.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast pb ID
		comStub.textFromWeight.add(pb.getPbId() + "");
		// Bekræft recept id.
		comStub.buttonsPresssedOnWeight.add(Buttons.BACK);

		// Indtast pb ID
		comStub.textFromWeight.add(pb.getPbId() + "");
		// Bekræft recept id.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		// Bekræft recept navn
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		for (RecipeCompDTO localRC : rcList) {
			int rawMaterialID = localRC.getRawMaterialId();
			double taraWeight = 20 + (Math.random() - 0.5);
			double nettoWeight = localRC.getNomNetto();
			int rbID = -1;
			for (RawMaterialBatchDTO rbDTO : rb) {
				if (rbDTO.getRawMaterialId() == rawMaterialID) {
					// Indtast rb ID
					comStub.textFromWeight.add(rbDTO.getRbId() + "");
					rbID = rbDTO.getRbId();
					// Bekræft råvare ID.
					comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
					comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
					break;
				}
			}
			if (rbID == -1) {
				fail("Product does not exist.");
			}
			// Foretag Afvejning:
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

			comStub.measurementFromWeight.add(-10000.0);
			comStub.measurementFromWeight.add(taraWeight);
			comStub.measurementFromWeight.add(nettoWeight);
			comStub.measurementFromWeight.add(-1 * (nettoWeight + taraWeight));

			expectedMeasurements
					.add(new ProductBatchCompDTO(pb.getPbId(), rbID, taraWeight, nettoWeight, user.getId()));

		}
		myThread.setUncaughtExceptionHandler(threadStopper);
		myThread.start();

		while (measureCon.getMeasurement() == null)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		MeasurementDTO actualMeasurement = measureCon.getMeasurement();

		assertEquals(2, actualMeasurement.getNewStatus());

		for (ProductBatchCompDTO actualPBComp : actualMeasurement.getMeasurements()) {
			for (ProductBatchCompDTO expectedPBComp : expectedMeasurements) {
				if (expectedPBComp.getRbId() == actualPBComp.getRbId()) {
					assertTrue(expectedPBComp.equals(actualPBComp));
				}
			}
		}
	}

	@Test
	public void logoutMidSessionTest() {
		wCon.setDAO(userDAO, rbDAO, pbDAO, rcDAO, rDAO);
		UserDTO user = null;
		List<RawMaterialBatchDTO> rb = null;
		ProductBatchDTO pb = null;
		RecipeDTO r = null;
		List<RecipeCompDTO> rcList = null;

		List<ProductBatchCompDTO> expectedMeasurements = new ArrayList<ProductBatchCompDTO>();

		try {
			user = ((UserDAO) userDAO).getUserList().get(0);
			pb = ((ProductBatchDAO) pbDAO).getProductBatchList().get(0);
			rb = ((RawMaterialBatchDAO) rbDAO).getRawMaterialBatchList();
			r = ((RecipeDAO) rDAO).getRecipeList().get(0);
			rcList = ((RecipeCompDAO) rcDAO).getRecipeCompList(r.getRecipeId());

		} catch (DALException e) {
			fail(e.getMessage());
		}

		// Indtast bruger ID
		comStub.textFromWeight.add(user.getId() + "");
		// Bekræft bruger.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast pb ID
		comStub.textFromWeight.add(pb.getPbId() + "");
		// Logud
		comStub.buttonsPresssedOnWeight.add(Buttons.LOGOUT);
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		
		// Indtast bruger ID
		comStub.textFromWeight.add(user.getId() + "");
		// Bekræft bruger.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		// Indtast pb ID
		comStub.textFromWeight.add(pb.getPbId() + "");
		// Bekræft recept id.
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
		// Bekræft recept navn
		comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

		for (RecipeCompDTO localRC : rcList) {
			int rawMaterialID = localRC.getRawMaterialId();
			double taraWeight = 20 + (Math.random() - 0.5);
			double nettoWeight = localRC.getNomNetto();
			int rbID = -1;
			for (RawMaterialBatchDTO rbDTO : rb) {
				if (rbDTO.getRawMaterialId() == rawMaterialID) {
					// Indtast rb ID
					comStub.textFromWeight.add(rbDTO.getRbId() + "");
					rbID = rbDTO.getRbId();
					// Bekræft råvare ID.
					comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
					comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
					break;
				}
			}
			if (rbID == -1) {
				fail("Product does not exist.");
			}
			// Foretag Afvejning:
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);
			comStub.buttonsPresssedOnWeight.add(Buttons.CONFIRM);

			comStub.measurementFromWeight.add(-10000.0);
			comStub.measurementFromWeight.add(taraWeight);
			comStub.measurementFromWeight.add(nettoWeight);
			comStub.measurementFromWeight.add(-1 * (nettoWeight + taraWeight));

			expectedMeasurements
					.add(new ProductBatchCompDTO(pb.getPbId(), rbID, taraWeight, nettoWeight, user.getId()));

		}
		myThread.setUncaughtExceptionHandler(threadStopper);
		myThread.start();

		while (measureCon.getMeasurement() == null)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		MeasurementDTO actualMeasurement = measureCon.getMeasurement();

		assertEquals(2, actualMeasurement.getNewStatus());

		for (ProductBatchCompDTO actualPBComp : actualMeasurement.getMeasurements()) {
			for (ProductBatchCompDTO expectedPBComp : expectedMeasurements) {
				if (expectedPBComp.getRbId() == actualPBComp.getRbId()) {
					assertTrue(expectedPBComp.equals(actualPBComp));
				}
			}
		}
	}


	@Test
	public void toleraceTest() {

	}

}
