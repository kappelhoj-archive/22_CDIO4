package staticClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileManagement {

	final static String PATH = "C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\";


	public enum TypeOfData{
		PRODUCTBATCHCOMP,
		PRODUCTBATCH,
		RAWMATERIALBATCH,
		RAWMATERIAL,
		RECIPECOMP,
		RECIPE,
		USER,
		TXT
	}

	/**
	 * Writes the object data on a specific file.
	 * @param dto
	 * 			Data object which shall be stored
	 * @param type
	 * 			Type of the Data. For example : "User" -> TypeOfData.USER
	 */
	public static void writeData(Object dto, TypeOfData type){
		FileOutputStream f = null;
		ObjectOutputStream o = null;

		try {
			if(type == TypeOfData.TXT)
				f = new FileOutputStream(new File(PATH+"WeightTable.txt"));
			else
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

	/**
	 * Retrieves Data from a specific file.
	 * @param type
	 * 			Type of the Data. For example : "User" -> TypeOfData.USER
	 * @return Retrieved Object
	 */
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
