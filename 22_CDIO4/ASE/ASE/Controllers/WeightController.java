package ASE.Controllers;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ASE.DTOs.MeasurementDTO;
import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.LogOutException;
import ASE.exceptions.ProtocolErrorException;
import ASE.interfaces.IMeasurementController;
import ASE.interfaces.IWeightCommunicator;
import ASE.interfaces.IWeightCommunicator.Buttons;
import controller.Initializer;
import dataAccessObjects.interfaces.IProductBatchDAO;
import dataAccessObjects.interfaces.IRecipeCompDAO;
import dataAccessObjects.interfaces.IRecipeDAO;
import dataAccessObjects.interfaces.IWeightControlDAO;
import dataTransferObjects.IWeightControlDTO;
import dataTransferObjects.ProductBatchCompDTO;
import dataTransferObjects.ProductBatchDTO;
import dataTransferObjects.RawMaterialBatchDTO;
import dataTransferObjects.RecipeCompDTO;
import dataTransferObjects.UserDTO;
import exceptions.DALException;

/**
 * 
 * @author arvid
 *
 */
public class WeightController implements Runnable {
	// Weightcommunication and class to handle the adding of new measurement.
	IMeasurementController measurementAdder;
	IWeightCommunicator weightCommunication;

	// All kinds of DAO to perform different task.
	IWeightControlDAO userDAO;
	IWeightControlDAO rbDAO;
	IWeightControlDAO pbDAO;
	IRecipeCompDAO recipeCompDAO;
	IRecipeDAO recipeDAO;

	// Creates objects to allocate memory.
	UserDTO userDTO = new UserDTO(0, null, null, null, null, null);
	RawMaterialBatchDTO rbDTO = new RawMaterialBatchDTO(0, 0, 0);
	ProductBatchDTO pbDTO = new ProductBatchDTO(0, 0, 0);

	// HashMap that contains all the Recipe Components in a recipe.
	HashMap<Integer, RecipeCompDTO> remainingReceptComp;
	HashMap<Integer, RecipeCompDTO> finnishedReceptComp;

	// The finished measureMents.
	List<ProductBatchCompDTO> measurements;

	/**
	 * Create a measurementController that takes a socket. Also get all the DAOs
	 * automaticly.
	 * 
	 * @param measurementAdder
	 *            object that receives measurements.
	 * @param weightConnection
	 *            socket to communicate with the weight.
	 * @throws IOException
	 */
	public WeightController(IMeasurementController measurementAdder, Socket weightConnection) throws IOException {
		this.measurementAdder = measurementAdder;
		weightCommunication = new WeightCommunicator(weightConnection);
		setDAO(Initializer.getUserDAO(), Initializer.getRawMaterialBatchDAO(), Initializer.getProductBatchDAO(),
				Initializer.getRecipeCompDAO(), Initializer.getRecipeDAO());
	}

	/**
	 * Create a WeightController with a weightCommunicator.
	 * 
	 * @param measurementAdder
	 *            Object that receives measurements.
	 * @param weightCommunication
	 *            Object that controls communication with a weight.
	 * @throws IOException
	 */
	public WeightController(IMeasurementController measurementAdder, IWeightCommunicator weightCommunication)
			throws IOException {
		this.measurementAdder = measurementAdder;
		this.weightCommunication = weightCommunication;
	}

	/**
	 * Set all the DAOs!
	 * 
	 * @param userDAO
	 * @param rbDAO
	 * @param pbDAO
	 * @param recipeCompDAO
	 * @param recipeDAO
	 */
	public void setDAO(IWeightControlDAO userDAO, IWeightControlDAO rbDAO, IWeightControlDAO pbDAO,
			IRecipeCompDAO recipeCompDAO, IRecipeDAO recipeDAO) {
		this.userDAO = userDAO;
		this.rbDAO = rbDAO;
		this.pbDAO = pbDAO;
		this.recipeCompDAO = recipeCompDAO;
		this.recipeDAO = recipeDAO;
	}

	/**
	 * The method that is started by the thread.
	 */
	@Override
	public void run() {
		// Restart the weight, to initialize.
		weightCommunication.restartWeightDisplay();
		while (true) {
			try {
				// Prompts the user to login.
				login();
				// Prompts the user to choose a product batch.
				registerProduct();
				// Starts measuring materials.
				while (true) {
					// Ask to enter Raw Material id.
					registerRawMaterial();
					// Measure materials.
					ProductBatchCompDTO measurement = measureRawMaterial();
					// Check that if the user pressed the back button.
					if (measurement == null) {
						continue;
					}
					// Add the ned measurement to the list.
					measurements.add(measurement);
					// Move the recipe Component to the finnished list.
					RecipeCompDTO finnishedComp = remainingReceptComp.remove(rbDTO.getRawMaterialId());
					finnishedReceptComp.put(rbDTO.getRawMaterialId(), finnishedComp);

					// If the production is finished break the loop.
					if (remainingReceptComp.isEmpty()) {
						break;
					}
				}
				// Add the result to the system.
				MeasurementDTO result = new MeasurementDTO(2, pbDTO.getPbId(), measurements);
				measurementAdder.enqueue(result);

			}
			// The user logged out
			catch (LogOutException e) {
				try {
					sendMessageAndConfirm("Du er nu logget ud.");
				} catch (ProtocolErrorException | LogOutException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// System error
			catch (ProtocolErrorException e) {
				weightCommunication.restartWeightDisplay();

				try {
					sendMessageAndConfirm("Systemfejl!");
				} catch (ProtocolErrorException | LogOutException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

	/**
	 * Handles user login.
	 * 
	 * @throws ProtocolErrorException
	 */
	private void login() throws ProtocolErrorException {

		Buttons buttonConfirmation = Buttons.NULL;
		// Keep trying to login until successful.
		do {
			try {
				try {
					// Get a userDTO and receive confirmation from user.
					buttonConfirmation = getDTOAndConfirm(userDTO, userDAO, "user id", "name");
					// If they press back start over.
					if (buttonConfirmation == Buttons.BACK)
						continue;
				} catch (DALException e) {
					sendMessageAndConfirm("Bruger findes ikke.");
				}
			} catch (LogOutException e) {
				try {
					sendMessageAndConfirm("Allerede logget ud.");
				} catch (LogOutException e1) {
					throw new ProtocolErrorException("User tried to log out twice.");
				}
			}

		} while (buttonConfirmation != Buttons.CONFIRM);

	}

	/**
	 * Handles registration of a product.
	 * 
	 * @throws ProtocolErrorException
	 * @throws LogOutException
	 */
	private void registerProduct() throws ProtocolErrorException, LogOutException {
		Buttons buttonConfirmation = Buttons.NULL;

		// Register produkt batch
		do {
			try {
				buttonConfirmation = getDTOAndConfirm(pbDTO, pbDAO, "pb id", "recept id");
				if (buttonConfirmation == Buttons.BACK)
					continue;
			} catch (DALException e) {
				sendMessageAndConfirm("Produkt findes ikke.");
				continue;
			}

			// Create list of recipe components that need to be created.
			remainingReceptComp = new HashMap<Integer, RecipeCompDTO>();
			finnishedReceptComp = new HashMap<Integer, RecipeCompDTO>();
			measurements = new ArrayList<ProductBatchCompDTO>();

			try {
				// Find all the recipe components.
				ArrayList<RecipeCompDTO> myRecipe = (ArrayList<RecipeCompDTO>) recipeCompDAO
						.getRecipeCompList(pbDTO.getRecipeId());

				for (RecipeCompDTO comp : myRecipe) {
					remainingReceptComp.put(comp.getRawMaterialId(), comp);
				}

			} catch (DALException e) {
				sendMessageAndConfirm("Recept ikke fundet.");
				continue;
			}

			// Change status for product batch.
			IProductBatchDAO pbCast = (IProductBatchDAO) pbDAO;
			pbDTO.setStatus(1);
			try {
				pbCast.updateProductBatch(pbDTO);
				sendMessageAndConfirm("Recept:" + recipeDAO.getRecipe(pbDTO.getRecipeId()).getRecipeName());
			} catch (DALException e1) {
				sendMessageAndConfirm("Status ikke opdateret.");
			}

		} while (buttonConfirmation != Buttons.CONFIRM);

	}

	/**
	 * Register a raw material.
	 * 
	 * @throws ProtocolErrorException
	 * @throws LogOutException
	 */
	private void registerRawMaterial() throws ProtocolErrorException, LogOutException {
		Buttons buttonConfirmation = Buttons.NULL;

		// Register rawmaterial.
		do {
			try {
				buttonConfirmation = getDTOAndConfirm(rbDTO, rbDAO, "rb id", "raavare id");
				if (buttonConfirmation == Buttons.BACK)
					continue;
			} catch (DALException e) {
				sendMessageAndConfirm("Raavare findes ikke.");
			}

			if (remainingReceptComp.containsKey(rbDTO.getRawMaterialId())) {
				sendMessageAndConfirm("Afvejning af paabegyndt:");
			} else if (finnishedReceptComp.containsKey(rbDTO.getRawMaterialId())) {
				sendMessageAndConfirm("Raavare allerede afvejet.");
				continue;
			} else {
				sendMessageAndConfirm("Raavare ikke i recept");
				continue;
			}

		} while (buttonConfirmation != Buttons.CONFIRM);
	}

	/**
	 * Measure the raw material.
	 * 
	 * @return The produktBathKomponent. NULL if the user wants to go back.
	 * @throws ProtocolErrorException
	 * @throws LogOutException
	 */
	private ProductBatchCompDTO measureRawMaterial() throws ProtocolErrorException, LogOutException {
		ProductBatchCompDTO measurement = new ProductBatchCompDTO(pbDTO.getPbId(), rbDTO.getRbId(), 0.0, 0.0,
				userDTO.getId());

		RecipeCompDTO myRecipeComp = null;
		try {
			myRecipeComp = recipeCompDAO.getRecipeComp(pbDTO.getRecipeId(), rbDTO.getRawMaterialId());

		} catch (DALException e) {
			sendMessageAndConfirm("Kunne ikke finde recept.");
			return null;
		}

		while (true) {
			weightCommunication.taraWeight();
			Double currentWeight = getCurrentWeight("Ryd vaegten.");

			// If the user want to go back, go back to choosing a new product.
			if (currentWeight == null) {
				return null;
			}

			weightCommunication.taraWeight();
			currentWeight = getCurrentWeight("Placer tara.");
			// Try do another measurement.
			if (currentWeight == null) {
				continue;
			} else {
				measurement.setTara(currentWeight);
			}

			weightCommunication.taraWeight();
			currentWeight = getCurrentWeight("Placer netto.");
			// Try do another measurement.
			if (currentWeight == null) {
				continue;
			} else {
				measurement.setNetto(currentWeight);
			}
			weightCommunication.taraWeight();
			currentWeight = getCurrentWeight("Please remove product");

			// Try do another measurement.
			if (currentWeight == null) {
				continue;
			}

			// Check if the measurement is as expected from the recipe
			double weightedTolerance = ((myRecipeComp.getTolerance()/100)*myRecipeComp.getNomNetto())
					- Math.abs(currentWeight + measurement.getTara() + measurement.getNetto());

			if (weightedTolerance >= Math.abs(measurement.getNetto() - myRecipeComp.getNomNetto())) {
				return measurement;
			} else {
				sendMessageAndConfirm("Measure do not match tol");
				sendMessageAndConfirm("Measure wasn't added");
				sendMessageAndConfirm("Please redo the measure");
				return null;
			}
		}

	}

	/**
	 * Gets the current weight measured. After telling the user what to do.
	 * 
	 * @param message
	 *            The message to send to the user.
	 * @return The weight measured.
	 * @throws ProtocolErrorException
	 * @throws LogOutException
	 */
	private Double getCurrentWeight(String message) throws ProtocolErrorException, LogOutException {
		// Send a message.
		Buttons buttonPressed = sendMessageAndConfirm(message);

		try {
			weightCommunication.sendMessage("Vent venligst");
		} catch (InvalidReturnMessageException e) {
			switch (weightCommunication.receiveButtonPush()) {
			case BACK:
				return null;
			default:
				break;
			}
		}

		switch (buttonPressed) {
		case CONFIRM:

			return weightCommunication.getWeight();
		case BACK:
			return null;
		default:
			return null;

		}

	}

	/**
	 * Ask the user for an input and awaits answer.
	 * 
	 * @param dto
	 * @param dao
	 * @param questionSubject
	 * @param expectedIdentity
	 * @return
	 * @throws ProtocolErrorException
	 * @throws LogOutException
	 * @throws DALException
	 */
	private Buttons getDTOAndConfirm(IWeightControlDTO dto, IWeightControlDAO dao, String questionSubject,
			String expectedIdentity) throws ProtocolErrorException, LogOutException, DALException {
		try {
			// Ask for some id.
			String id = weightCommunication.askForInformation("Indtast " + questionSubject);
			// Look up the id in the specified DAO.
			dto.copy(dao.getDTOById(Integer.parseInt(id)));
			String identity = dto.getIdentity();

			// Send a message so the user can confirm the information.
			weightCommunication.sendMessage(expectedIdentity + ": " + identity + " ->]");
			return weightCommunication.receiveButtonPush();

		} catch (InvalidReturnMessageException e) {
			System.out.println("Caught Exception:" + e);
			return weightCommunication.receiveButtonPush();
		}

	}

	/**
	 * Sends a message and wait for confirmation from the user.
	 * 
	 * @param message
	 * @return
	 * @throws ProtocolErrorException
	 * @throws LogOutException
	 */
	private Buttons sendMessageAndConfirm(String message) throws ProtocolErrorException, LogOutException {
		try {
			// Send a message to the user.
			weightCommunication.sendMessage(message + " ->]");
			// Wait for user.
			return weightCommunication.receiveButtonPush();
		} catch (InvalidReturnMessageException e) {
			return weightCommunication.receiveButtonPush();
		}
	}

}
