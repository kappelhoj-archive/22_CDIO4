package ASE.Views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConnectionReader {

	String WeightIP, WeightPort;

	public void WeightReader() throws FileNotFoundException {

		// Creation of the scanner
		Scanner WeightScanner = new Scanner(new FileInputStream("src/WeightTable.txt"));

		// Attempt to retrieve information from the WeightTable.txt file.
		try {
			// Retrieve information.
			while (WeightScanner.hasNextLine()) {
				String tokenCheck = WeightScanner.next();

				// Syntax check.
				if (tokenCheck.equals("IP")) {
					String weightIP = WeightScanner.nextLine().trim();
					WeightScanner.skip("PORT");
					String weightPort = WeightScanner.nextLine().trim();

					IPChecker(weightIP);

					PORTChecker(weightPort);

					System.out.println(weightIP + " " + weightPort);

				} else if (tokenCheck != "IP") {
					System.out.println("Error: Invalid syntax in file!");
				}
			}

		} catch (Exception e) {
			System.out.println("Error occured: " + e);
		} finally {
			WeightScanner.close();
		}
	}

	// Method to return Port number as integer.
	public int getPortInt() {
		int weightPortInt = Integer.parseInt(WeightPort);
		return weightPortInt;
	}

	// Method to verify the validness of the IP.
	private static boolean IPChecker(String WeightIP) {
		// Check to see if the IP contains the right number of periods.

		String[] IPArray = WeightIP.split(Pattern.quote("."));

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
