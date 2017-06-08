package ASE.Controllers;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import ASE.DTOs.MeasurementDTO;
import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.LogOutException;
import ASE.exceptions.ProtocolErrorException;
import ASE.interfaces.IMeasurementController;
import ASE.interfaces.IWeightCommunicator;
import ASE.interfaces.IWeightCommunicator.Buttons;
import dataAccessObjects.RecipeDAO;
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
	IMeasurementController measurementAdder;
	IWeightCommunicator weightCommunication;

	IWeightControlDAO userDAO;
	IWeightControlDAO rbDAO;
	IWeightControlDAO pbDAO;
	IRecipeCompDAO recipeCompDAO;
	IRecipeDAO recipeDAO;

	UserDTO userDTO = new UserDTO(0, null, null, null, null, null);
	RawMaterialBatchDTO rbDTO = new RawMaterialBatchDTO(0, 0, 0);
	ProductBatchDTO pbDTO = new ProductBatchDTO(0, 0, 0);

	HashMap<Integer, RecipeCompDTO> remainingReceptComp;

	HashMap<Integer, RecipeCompDTO> finnishedReceptComp;

	List<ProductBatchCompDTO> measurements;

	public WeightController(IMeasurementController measurementAdder, Socket weightConnection) throws IOException {
		this.measurementAdder = measurementAdder;
		weightCommunication = new WeightCommunicator(weightConnection);
	}

	public WeightController(IMeasurementController measurementAdder, IWeightCommunicator weightCommunication)
			throws IOException {
		this.measurementAdder = measurementAdder;
		this.weightCommunication = weightCommunication;
	}

	public void setDAO(IWeightControlDAO userDAO, IWeightControlDAO rbDAO, IWeightControlDAO pbDAO,
			IRecipeCompDAO recipeCompDAO, IRecipeDAO recipeDAO) {
		this.userDAO = userDAO;
		this.rbDAO = rbDAO;
		this.pbDAO = pbDAO;
		this.recipeCompDAO = recipeCompDAO;
		this.recipeDAO = recipeDAO;
	}

	@Override
	public void run() {
		weightCommunication.restartWeightDisplay();
		while (true) {
			try {
				login();
				registerProduct();

				while (true) {
					registerRawMaterial();
					ProductBatchCompDTO measurement = measureProduct();
					if (measurement == null) {
						continue;
					}
					measurements.add(measurement);
					RecipeCompDTO finnishedComp=remainingReceptComp.remove(rbDTO.getRawMaterialId());
					finnishedReceptComp.put(rbDTO.getRawMaterialId(),  finnishedComp);
					if(remainingReceptComp.isEmpty()){
						break;
					}
				}
				
				MeasurementDTO result= new MeasurementDTO(2, pbDTO.getPbId(), measurements);
				measurementAdder.enqueue(result);
				
			} catch (LogOutException e) {
				try {
					sendMessageAndConfirm("Du er nu logget ud. ->]");
				} catch (ProtocolErrorException | LogOutException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (ProtocolErrorException e) {
				weightCommunication.restartWeightDisplay();
				
				try {
					sendMessageAndConfirm("Der skete en systemfejl. ->]");
				} catch (ProtocolErrorException | LogOutException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
	}

	private void login() throws ProtocolErrorException {

		Buttons buttonConfirmation = Buttons.NULL;
		do {
			try {
				try {
					buttonConfirmation = getDTOAndConfirm(userDTO, userDAO, "user id", "name");
					if (buttonConfirmation == Buttons.BACK)
						continue;
				} catch (DALException e) {
					sendMessageAndConfirm("Denne bruger findes ikke. ->]");
				}
			} catch (LogOutException e) {
				try {
					sendMessageAndConfirm("Du er allerede logget ud. ->]");
				} catch (LogOutException e1) {
					throw new ProtocolErrorException("User tried to log out twice.");
				}
			}

		} while (buttonConfirmation != Buttons.CONFIRM);

	}

	private void registerProduct() throws ProtocolErrorException, LogOutException {
		Buttons buttonConfirmation = Buttons.NULL;

		// Register produkt batch
		do {
			try {
				buttonConfirmation = getDTOAndConfirm(pbDTO, pbDAO, "produkt batch id", "recept id");
				if (buttonConfirmation == Buttons.BACK)
					continue;
			} catch (DALException e) {
				sendMessageAndConfirm("Produkt eksistere ikke. ->]");
				continue;
			}

			// Lav liste med componenter der skal fremstilles
			remainingReceptComp = new HashMap<Integer, RecipeCompDTO>();
			finnishedReceptComp = new HashMap<Integer, RecipeCompDTO>();
			measurements = new ArrayList<ProductBatchCompDTO>();

			try {
				Stack<RecipeCompDTO> myRecipe = (Stack<RecipeCompDTO>) recipeCompDAO
						.getRecipeCompList(pbDTO.getReceptId());
				for (RecipeCompDTO comp : myRecipe) {
					remainingReceptComp.put(comp.getRawMaterialId(), comp);
				}

			} catch (DALException e) {
				sendMessageAndConfirm("Kunne ikke findes i systemet ->]");
				continue;
			}

			// Ændre status for produkt batch.
			IProductBatchDAO pbCast = (IProductBatchDAO) pbDAO;
			pbDTO.setStatus(1);
			try {
				pbCast.updateProductBatch(pbDTO);

				sendMessageAndConfirm("Recept:" + recipeDAO.getRecipe(pbDTO.getReceptId()).getRecipeName() + " ->]");
			} catch (DALException e1) {
				sendMessageAndConfirm("Produkt batch status ikke opdateret. ->]");
			}

		} while (buttonConfirmation != Buttons.CONFIRM);

	}

	private void registerRawMaterial() throws ProtocolErrorException, LogOutException {
		Buttons buttonConfirmation = Buttons.NULL;

		// Register råvarebatch.
		do {
			try {
				buttonConfirmation = getDTOAndConfirm(rbDTO, rbDAO, "råvare batch id", "råvare id");
				if (buttonConfirmation == Buttons.BACK)
					continue;
			} catch (DALException e) {
				sendMessageAndConfirm("Råvare eksistere ikke i systemet. ->]");
			}

			if (remainingReceptComp.containsKey(rbDTO.getRawMaterialId())) {
				sendMessageAndConfirm("Afvejning af påbegyndt: ->]");
			} else if (finnishedReceptComp.containsKey(rbDTO.getRawMaterialId())) {
				sendMessageAndConfirm("Denne råvare er allerede afvejet. ->]");
				continue;
			} else {
				sendMessageAndConfirm("Denne råvare er ikke i recepten. ->]");
				continue;
			}

		} while (buttonConfirmation != Buttons.CONFIRM);
	}

	/**
	 * 
	 * @return The produktBathKomponent. NULL if the user wants to go back.
	 * @throws ProtocolErrorException
	 * @throws LogOutException
	 */
	private ProductBatchCompDTO measureProduct() throws ProtocolErrorException, LogOutException {
		ProductBatchCompDTO measurement = new ProductBatchCompDTO(pbDTO.getPbId(), rbDTO.getRbId(), 0.0, 0.0,
				userDTO.getId());

		RecipeCompDTO myRecipeComp = null;
		try {
			myRecipeComp = recipeCompDAO.getRecipeComp(pbDTO.getReceptId(), rbDTO.getRawMaterialId());

		} catch (DALException e) {
			sendMessageAndConfirm("Could not find a matching recipe, try again ->]");
			return null;
		}

		while (true) {
			weightCommunication.taraWeight();
			Double currentWeight = getCurrentWeight("Please clear the weight ->]");

			// If the user want to go back, go back to choosing a new product.
			if (currentWeight == null) {
				return null;
			}

			weightCommunication.taraWeight();
			currentWeight = getCurrentWeight("Please put on the tara ->]");
			// Try do another measurement.
			if (currentWeight == null) {
				continue;
			} else {
				measurement.setTara(currentWeight);
			}

			weightCommunication.taraWeight();
			currentWeight = getCurrentWeight("Please put on the netto ->]");
			// Try do another measurement.
			if (currentWeight == null) {
				continue;
			} else {
				measurement.setNetto(currentWeight);
			}
			weightCommunication.taraWeight();
			currentWeight = getCurrentWeight("Please remove the product ->]");

			// Try do another measurement.
			if (currentWeight == null) {
				continue;
			}

			// Check if the measurement is as expected from the recipe
			double weightedTolerance = myRecipeComp.getTolerance()
					- Math.abs(currentWeight + measurement.getTara() + measurement.getNetto());

			if (weightedTolerance >= Math.abs(measurement.getNetto() - myRecipeComp.getNomNetto())) {
				// TODO: remove the recipeKomp from the list when its done.
				return measurement;
			} else {
				sendMessageAndConfirm("Measurement dosen't match expected tolerance. ->]");
				sendMessageAndConfirm("Measurement wasn't added to the production. ->]");
				sendMessageAndConfirm("Please redo the measurement ->]");
				// TODO: Text should be updated to dansk! Maybe add option to
				// force a measurement, even if it dosen't match.
				return null;
			}
		}

	}

	private Double getCurrentWeight(String message) throws ProtocolErrorException, LogOutException {
		Buttons buttonPressed = sendMessageAndConfirm(message);
		switch (buttonPressed) {
		case CONFIRM:
			return weightCommunication.getWeight();
		case BACK:
			return null;
		default:
			return null;

		}

	}

	private Buttons getDTOAndConfirm(IWeightControlDTO dto, IWeightControlDAO dao, String questionSubject,
			String expectedIdentity) throws ProtocolErrorException, LogOutException, DALException {
		try {
			String id = weightCommunication.askForInformation("Indtast " + questionSubject + " og tryk ok.");
			dto.copy(dao.getDTOById(Integer.parseInt(id)));
			String identity = dto.getIdentity();

			weightCommunication.sendMessage("Befrækt info: " + expectedIdentity + ": " + identity + " ->]");
			return weightCommunication.receiveButtonPush();

		} catch (InvalidReturnMessageException e) {
			return weightCommunication.receiveButtonPush();
		}

	}

	private Buttons sendMessageAndConfirm(String message) throws ProtocolErrorException, LogOutException {
		try {
			weightCommunication.sendMessage(message + " ->]");
			return weightCommunication.receiveButtonPush();
		} catch (InvalidReturnMessageException e) {
			return weightCommunication.receiveButtonPush();
		}
	}

}
