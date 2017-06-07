package ASE.Controllers;

import java.io.IOException;
import java.net.Socket;

import ASE.exceptions.InvalidReturnMessageException;
import ASE.exceptions.LogOutException;
import ASE.exceptions.ProtocolErrorException;
import ASE.interfaces.IMeasurementController;
import ASE.interfaces.IWeightCommunicator;
import ASE.interfaces.IWeightCommunicator.Buttons;
import dataAccessObjects.interfaces.IWeightControlDAO;
import dataTransferObjects.IWeightControlDTO;
import dataTransferObjects.ProductBatchCompDTO;
import dataTransferObjects.ProductBatchDTO;
import dataTransferObjects.RawMaterialBatchDTO;
import dataTransferObjects.UserDTO;


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

	UserDTO userDTO=new UserDTO(0, null, null, null, null, null);
	RawMaterialBatchDTO rbDTO=new RawMaterialBatchDTO(0, 0, 0);
	ProductBatchDTO pbDTO=new ProductBatchDTO(0, 0, 0);

	public WeightController(IMeasurementController measurementAdder, Socket weightConnection) throws IOException {
		this.measurementAdder = measurementAdder;
		weightCommunication = new WeightCommunicator(weightConnection);
	}
	public WeightController(IMeasurementController measurementAdder, IWeightCommunicator weightCommunication) throws IOException {
		this.measurementAdder = measurementAdder;
		this.weightCommunication = weightCommunication;
	}
	

	public void setDAO(IWeightControlDAO userDAO, IWeightControlDAO rbDAO, IWeightControlDAO pbDAO) {
		this.userDAO=userDAO;
		this.rbDAO=rbDAO;
		this.pbDAO=pbDAO;
	}

	@Override
	public void run() {
		weightCommunication.restartWeightDisplay();
		while (true) {
			try {
				login();
				while (true) {
					registerProduction();
					ProductBatchCompDTO measurement = measureProduct();
					if (measurement == null) {
						continue;
					}
					measurementAdder.enqueue(measurement);
				}
			} catch (LogOutException e) {
				// Let the user log out if he presses that button.
			} catch (ProtocolErrorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void login() throws ProtocolErrorException {

		Buttons buttonConfirmation = Buttons.NULL;
		do {
			try {
				buttonConfirmation = getDTOAndConfirm(userDTO, userDAO, "user id", "name");
				if (buttonConfirmation == Buttons.BACK)
					continue;
			} catch (LogOutException e) {
				// TODO Tell the user he is not logged in and can therefore not
				// logout.
			}
		} while (buttonConfirmation != Buttons.CONFIRM);

	}

	private void registerProduction() throws ProtocolErrorException, LogOutException {
		Buttons buttonConfirmation = Buttons.NULL;
		// Register produkt batch
		do {
			buttonConfirmation = getDTOAndConfirm(pbDTO, pbDAO, "produkt batch id", "recept id");
			if (buttonConfirmation == Buttons.BACK)
				continue;

		} while (buttonConfirmation != Buttons.CONFIRM);

		// Register råvarebatch.
		do {
			buttonConfirmation = getDTOAndConfirm(rbDTO, rbDAO, "råvare batch id", "råvare id");
			if (buttonConfirmation == Buttons.BACK)
				continue;

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

			return measurement;
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
			String expectedIdentity) throws ProtocolErrorException, LogOutException {
		try {
			String id = weightCommunication.askForInformation("Indtast " + questionSubject + " og tryk ok.");
			dto = dao.getDTOById(Integer.parseInt(id));
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
