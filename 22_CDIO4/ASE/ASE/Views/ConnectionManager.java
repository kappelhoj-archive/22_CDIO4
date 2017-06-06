package ASE.Views;

import ASE.Views.ConnectionReader;
import ASE.Controllers.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

public class ConnectionManager implements Runnable {

	ConnectionReader connectionReader = new ConnectionReader(null);
	WeightController[] weightController;
	MeasurementController measurementController;
	Socket weightSocket;

	@Override
	public void run() {
		try {
			for (int i = 0; i < connectionReader.getAllPortNumbers().size(); i++) {
				weightSocket = new Socket(connectionReader.getIPString(i), connectionReader.getPortInt(i));
				System.out.println("Connection established.");
			}
		} catch (UnknownHostException e) {
			System.out.println("Error occured: Host unknown " + e);
		} catch (IOException e) {
			System.out.println("Error occured: Port number unknown " + e);
		}
	}

	public void threadStarter() {
		for (int i = 0; i < connectionReader.getAllIPAdresses().size(); i++) {
			try {
				weightSocket = new Socket(connectionReader.getIPString(i), connectionReader.getPortInt(i));
			} catch (IOException e) {
				System.out.println("Connection failed: " + e);
			}

			try {
				weightController[i] = new WeightController(measurementController, weightSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			(new Thread(weightController[i])).start();
		}
	}
}
