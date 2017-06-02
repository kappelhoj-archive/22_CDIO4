package staticClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import exceptions.DALException;

public class FileManagement {

	
	/**
	 * Reads and decodes a file with data and converts the data into
	 * objects.
	 * 
	 * @param type : What type of DTO. Operatoer, ProduktBatch, ...
	 * @return An ArrayList of DTO data from the given type.
	 * @throws DALException
	 *             The exception to be thrown something goes wrong under the
	 *             reading and decoding.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> retrieveFrom(String type) throws DALException {
		ArrayList<Object> objects = new ArrayList<Object>();

		ObjectInputStream OIS = null;
		try {
			FileInputStream fIS = new FileInputStream(type+".data");
			OIS = new ObjectInputStream(fIS);
			Object inObj = OIS.readObject();
			if (inObj instanceof ArrayList<?>) {
				objects = (ArrayList<Object>) inObj;
			} else {
				throw new DALException("Wrong object in file");
			}
		} catch (FileNotFoundException e) {
			return objects;
		} catch (IOException e) {
			throw new DALException("Error while reading disk! " + e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new DALException("Error while reading file - Class not found! "+ e.getMessage());
		} finally {
			if (OIS != null) {
				try {
					OIS.close();
				} catch (IOException e) {
					throw new DALException("Error closing pObjectStream! " + e.getMessage());
				}
			}
		}
		return objects;
	}
	
	/**
	 * Saves the user data to a text file.
	 * 
	 * @param objects
	 *            The array of data that shall be saved.
	 * @param type 
	 * 			  What type of DTO. Operatoer, ProduktBatch, ...
	 * @throws DALException
	 *             The exception to be thrown if something goes wrong under the
	 *             saving.
	 */
	public static void saveData(ArrayList<Object> objects, String type) throws DALException {
		ObjectOutputStream OOS = null;
		try {
			FileOutputStream FOS = new FileOutputStream(type+".data");
			OOS = new ObjectOutputStream(FOS);
			OOS.writeObject(objects);
		} catch (FileNotFoundException e) {
			throw new DALException("Error locating file " + e.getMessage());
		} catch (IOException e) {
			throw new DALException("Error writing to disk " + e.getMessage());
		} finally {
			if (OOS != null) {
				try {
					OOS.close();
				} catch (IOException e) {
					throw new DALException("Unable to close ObjectStream " + e.getMessage());
				}
			}
		}
	}
}
