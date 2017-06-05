package ASE.Views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConnectionReader {

	private String WeightIP, WeightPort;
	private String[] IPArray;
	private String fileLocation;

	public ConnectionReader(String fileLocation) {
		this.fileLocation = fileLocation;

		if (fileLocation == null) {
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
	public void WeightReader() throws FileNotFoundException {

		// Creation of the scanner
		Scanner weightScanner = new Scanner(new FileInputStream(fileLocation));

		// Attempt to retrieve information from the WeightTable.txt file.
		// Retrieve information.
		while (weightScanner.hasNext()) {
			try {
				String tokenCheck = weightScanner.next();

				// Syntax check.
				if (tokenCheck.equals("IP")) {
					String weightIP = weightScanner.nextLine().trim();
					weightScanner.skip("PORT");
					String weightPort = weightScanner.nextLine().trim();

					IPChecker(weightIP);

					PORTChecker(weightPort);

					System.out.println(weightIP + " " + weightPort);

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
	 * Method to return size of IPArray
	 * 
	 * @return Returns the value of the IP Array as a String[] (Array).
	 */
	public String[] getIPArray() {
		return IPArray;
	}

	/**
	 * Method to return IP number as String.
	 * 
	 * @return Returns the value of the IP number as a String.
	 */
	public String getIPString() {
		return WeightIP;
	}

	/**
	 * Method to return Port number as integer.
	 * 
	 * @return Returns the value of the Port number as an integer.
	 */
	public int getPortInt() {
		int weightPortInt = Integer.parseInt(WeightPort);
		return weightPortInt;
	}

	/**
	 * Method to verify the validness of the IP.
	 * 
	 * @param weightIP
	 *            the String value of WeightIP
	 * @return Returns true if all checks passes without problems.
	 */
	private boolean IPChecker(String weightIP) {

		// Check to see if the IP contains the right number of periods.
		IPArray = weightIP.split(Pattern.quote("."));

		if (IPArray.length != 4) {
			System.out.println("Error: Invalid number of periods!");
			System.out.print("Error source: ");
		}

		// Check to see if any characters are letters.
		try {
			for (int i = 0; i < IPArray.length; i++) {
				Integer.parseInt(IPArray[i]);
			}
		} catch (Exception e) {
			System.out.println("Error: IP contains invalid characters!");
		}

		// Check to see if the IP is invalid/unknown, i.e. 0.0.0.0.
		if (weightIP == "0.0.0.0") {
			System.out.println("Error: IP reference invalid/unknown!");
			System.out.println("Error source: ");
		}

		// Check to see if any of the numbers are too small or too big.
		try {
			for (int i = 0; i < IPArray.length; i++) {
				int a = Integer.parseInt(IPArray[i]);
				if (a < 0 || a > 255) {
					System.out.println("Error: IP Contains invalid numbers!");
					System.out.print("Error source: ");
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
	private static boolean PORTChecker(String weightPort) {

		// Checks to see if the Port number contains letters.
		try {
			int weightPortInt = Integer.parseInt(weightPort);
		} catch (Exception e) {
			System.out.println("Error: Port contains invalid characters!");
		}
		int weightPortInt = Integer.parseInt(weightPort);

		// Check to see if the Port number is within the acceptable range.
		if (weightPortInt < 0 || weightPortInt > 65535) {
			System.out.println("Error: Port Number Not Valid!");
			System.out.print("Error source: ");
		}

		return true;
	}
}
