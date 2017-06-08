package staticClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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


public class FileManagement {
	
	final static String PATH = "C:\\22_CDIO4\\";


	public enum TypeOfData{
		PRODUCTBATCHCOMP,
		PRODUCTBATCH,
		RAWMATERIALBATCH,
		RAWMATERIAL,
		RECIPECOMP,
		RECIPE,
		USER
	}

	public static void writeData(Object dto, TypeOfData type){
		FileOutputStream f = null;
		ObjectOutputStream o = null;

		try {
			f = new FileOutputStream(new File(PATH+type.toString()+".data"));
			o = new ObjectOutputStream(f);

			o.writeObject(dto);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream " +e);

		}finally{
			try {
				o.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				f.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static Object retrieveData(TypeOfData type){

		FileInputStream fi = null;
		ObjectInputStream oi = null;

		try{
			fi = new FileInputStream(new File(PATH+type.toString()+".data"));
			oi = new ObjectInputStream(fi);

			Object dto = (Object) oi.readObject();

			return dto;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream "+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}finally{
			try {
				oi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
}
