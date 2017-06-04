package ASE.Views;

import ASE.Views.ConnectionReader;
import ASE.Controllers.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;

public class ConnectionManager implements Runnable {

	ConnectionReader connectionReader = new ConnectionReader();
	WeightController weightController;
	MeasurementController measurementController;
	Socket weightSocket;

	@Override
	public void run() {
		try {
			weightSocket = new Socket(connectionReader.WeightIP, connectionReader.getPortInt());
			System.out.println("Connection established.");
		} catch (UnknownHostException e) {
			System.out.println("Error occured: Host unknown " + e);
		} catch (IOException e) {
			System.out.println("Error occured: Port number unknown " + e);
		}

	}

	public void threadStarter() {
		for (int i = 0; i < connectionReader.getIPArray().length; i++);
			try {
				weightSocket = new Socket(connectionReader.getIPString(), connectionReader.getPortInt());
			} catch (IOException e) {
				System.out.println("Connection failed: " +e);
			}
			
			weightController = new WeightController(measurementController, weightSocket);
			
			(new Thread(new weightThread())).start();
			(new weightThread()).start();
			
			
			
		// For hver IP/Port detekteret. Check
		// Lav nyt socket med ip/port Check
		// Lav ny weightController til hvert socket.
		// Start tråd
		// tråd.start;
	}

}
