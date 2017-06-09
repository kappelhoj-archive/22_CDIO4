package ASE.Views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConnectionReader {

	private String weightIP;
	private ArrayList<String> allIPAddresses = new ArrayList<String>();
	private ArrayList<String> allPortNumbers = new ArrayList<String>();
	private String fileLocation;

	public ConnectionReader(String fileLocation) {
		this.fileLocation = fileLocation;

		if (fileLocation.equals(null)) {
			fileLocation = "src/WeightTable.txt";
		}
	}

	/**
	 * WeightReader's primary class. Opens the "src/WeightTable.txt" file, and
	 * verifies all the information in the .txt file. Unless a new file is
	 * explicitly stated, the default is always "src/WeightTable.txt".
	 * 
	 * @throws FileNotFoundException
	 *             throws an exception if the file is not located in
	 *             "src/WeightTable.txt"
	 */
	public void getWeightIPs() throws FileNotFoundException {

		// Creation of the scanner
		Scanner weightScanner = new Scanner(new FileInputStream(fileLocation));
		// Attempt to retrieve information from the WeightTable.txt file.
		// Retrieve information.
		while (weightScanner.hasNext()) {
			try {
				String tokenCheck = weightScanner.next();

				// Syntax check.
				if (tokenCheck.equals("IP")) {
					weightIP = weightScanner.nextLine().trim();
					weightScanner.skip("PORT");
					String weightPort = weightScanner.nextLine().trim();

					// Adds the scanned IP addresses if, and only if, it passes
					// the check.
					if (validateIP(weightIP)) {
						allIPAddresses.add(weightIP);
					} else {
						System.out.println(weightIP + " " + weightPort);
					}
					// Adds the scanned Port number if, and only if, it passes
					// the check.
					if (validatePORT(weightPort)) {
						allPortNumbers.add(weightPort);
					} else {
						System.out.println(weightIP + " " + weightPort);
					}

				} else if (tokenCheck != "IP") {
					System.out.println("Error: Invalid syntax in file!");
				}
			}

			catch (Exception e) {
				System.out.println("Error occured: " + e);
				e.printStackTrace();
			}
		}
		weightScanner.close();
	}

	/**
	 * Method to return IP numbers as Arraylist.
	 * 
	 * @return Returns an Arraylist of all the correctly typed-in IP addresses
	 */
	public ArrayList<String> getAllIPAddresses() {
		return allIPAddresses;
	}

	/**
	 * Method to return Port numbers as Arraylist.
	 * 
	 * @return Returns an Arraylist of all the correctly typed-in Port numbers
	 */
	public ArrayList<String> getAllPortNumbers() {
		return allPortNumbers;
	}

	/**
	 * Method to return IP number as a String.
	 * 
	 * @param i
	 *            The specified index to return.
	 * @return
	 */
	public String getIPString(int i) {
		return allIPAddresses.get(i);
	}

	/**
	 * Method to return Port number as an Integer.
	 * 
	 * @param i
	 *            The specified index to return.
	 * @return Returns the value of the Port number as an integer.
	 */
	public int getPortInt(int i) {
		int portNumber = Integer.parseInt(allPortNumbers.get(i));
		return portNumber;
	}

	/**
	 * Method to verify the validness of the IP.
	 * 
	 * @param weightIP
	 *            the String value of WeightIP
	 * @return Returns true if all checks passes without problems.
	 */
	private boolean validateIP(String weightIP) {

		// Check to see if the IP contains the right number of periods.
		String[] IPArray = weightIP.split(Pattern.quote("."));

		if (IPArray.length != 4) {
			System.out.println("Error: Invalid number of periods!");
			System.out.print("Error source: ");
			return false;
		}

		// Check to see if any characters are letters.
		try {
			for (int i = 0; i < IPArray.length; i++) {
				Integer.parseInt(IPArray[i]);
			}
		} catch (Exception e) {
			System.out.println("Error: IP contains invalid characters!");
			System.out.print("Error source: ");
			return false;
		}

		// Check to see if the IP is invalid/unknown, i.e. 0.0.0.0.
		if (weightIP.equals("0.0.0.0")) {
			System.out.println("Error: IP reference invalid/unknown!");
			System.out.print("Error source: ");
			return false;
		}

		// Check to see if any of the numbers are too small or too big.
		try {
			for (int i = 0; i < IPArray.length; i++) {
				int a = Integer.parseInt(IPArray[i]);
				if (a < 0 || a > 255) {
					System.out.println("Error: IP Contains invalid numbers!");
					System.out.print("Error source: ");
					return false;
				}
			}
		} catch (Exception e) {
			System.out.print("Error source: ");
		}
		return true;
	}

	/**
	 * @param weightPort
	 *            the String value of weightPort
	 * @return Returns true if all checks passes without problems.
	 */
	// Method to verify the validness of the Port number.
	private static boolean validatePORT(String weightPort) {

		// Checks to see if the Port number contains letters.
		try {
			int weightPortInt = Integer.parseInt(weightPort);

			// Check to see if the Port number is within the acceptable range.
			if (weightPortInt < 0 || weightPortInt > 65535) {
				System.out.println("Error: Port Number Not Valid!");
				System.out.print("Error source: ");
				return false;
			}
			return true;

		} catch (Exception e) {
			System.out.println("Error: Port contains invalid characters!");
			return false;

		}
	}
}
