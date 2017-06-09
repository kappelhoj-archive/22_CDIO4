package ASE.Views;

import ASE.Views.ConnectionReader;
import ASE.Controllers.*;

import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.IOException;

public class ConnectionManager {

	ConnectionReader connectionReader;
	private ArrayList<String> allConnectedIPAddresses = new ArrayList<String>();
	private ArrayList<Integer> allConnectedPortNumbers = new ArrayList<Integer>();
	WeightController[] weightController;
	MeasurementController measurementController;
	Socket weightSocket;

	/**
	 * Method which tries to establish a connection to all of the listed weights
	 * in the WeightTable.txt file, while adding each successful IP/Port number
	 * to an array.
	 */

	public ConnectionManager(String fileLocation) {
		connectionReader = new ConnectionReader(fileLocation);
	}

	/**
	 * Attempt to establish connection to the weights listed on the
	 * WeightTable.txt. The method runs through the IPs once.
	 */
	public void getConnections() {

		try {
			connectionReader.getWeightIPs();
			for (int i = 0; i < connectionReader.getAllIPAddresses().size(); i++) {
				try {
					weightSocket = new Socket(connectionReader.getIPString(i), connectionReader.getPortInt(i));
					allConnectedIPAddresses.add(connectionReader.getIPString(i));
					allConnectedPortNumbers.add(connectionReader.getPortInt(i));
				} catch (ConnectException e) {
					System.out.println("Error: Attempted connection failed!");
					System.out.println(
							"Error source: " + connectionReader.getIPString(i) + " " + connectionReader.getPortInt(i));
				}
				// Add the newly connected IP/Socket to a list of successfully
				// connected weights.
			}

		} catch (UnknownHostException e) {
			System.out.println("Error occured: Host unknown " + e);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();

		}
	}

	/**
	 * Starts each individual thread for each socket connection established.
	 */
	public void threadStarter() {
		for (int i = 0; i < connectionReader.getAllIPAddresses().size(); i++) {
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

	/**
	 * Method to return all of the connected IP addresses in an Array.
	 * 
	 * @return Returns an arraylist of all the connected IP addresses.
	 */
	public ArrayList<String> getAllConnectedIPAddresses() {
		return allConnectedIPAddresses;
	}

	/**
	 * Method to return all of the connected Port Numbers in an Array.
	 * 
	 * @return Returns an arraylist of all the connected Port numbers.
	 */
	public ArrayList<Integer> getAllConnectedPortNumbers() {
		return allConnectedPortNumbers;
	}

	/**
	 * Method to quickly get the number of connected weights
	 * 
	 * @return Returns the number of connected weights (defined by the number of
	 *         connected IP's), as an Integer.
	 */
	public int getNumberOfConnectedIPs() {
		return allConnectedIPAddresses.size();
	}
}
