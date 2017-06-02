package ASE.Views;

import ASE.Views.ConnectionReader;
import ASE.Controllers.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;


public class ConnectionManager implements Runnable {

	ConnectionReader connectionReader;
	WeightController weightController;
	MeasurementController measurementController;
	Socket weightSocket;
	
	@Override
	public void run() {
		try {
			weightSocket = new Socket(connectionReader.WeightIP, connectionReader.getPortInt());
			System.out.println("Connection established.");
		} catch (UnknownHostException e) {
			System.out.println("Error occured: Host unknown "+ e);
		} catch (IOException e) {
			System.out.println("Error occured: Port number unknown "+ e);
		}	

	}
	public static void main(String args[]) {
        (new Thread(new WeightController(MeasurementController.measurementAdder, ip, port))).start();
    }

}
