package ASE.Views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConnectionReader {

	String WeightIP, WeightPort;

	public void WeightReader() throws FileNotFoundException {

		// Attempt to retrieve information from the WeightTable.txt file.
		try {
			// Creation of the scanner
			Scanner WeightScanner = new Scanner(new FileInputStream("WeightTable.txt"));

			// Retrieve information
			while (WeightScanner.hasNextLine()) {
				String newWeightLine = WeightScanner.nextLine().trim();
				if (newWeightLine.equals("XXX")) {
					WeightIP = newWeightLine;
				} else if (newWeightLine.equals("YYY")) {
					WeightPort = newWeightLine;
				}
				// Print information for confirmation
				System.out.println("WeightIP " + "WeightPort");
			}

			WeightScanner.close();

		} catch (Exception e) {
			System.out.println("Error occured: " + e);
		}
	}
}