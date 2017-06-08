package staticClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.List;

import dataTransferObjects.DTO;

public class FileStreamtest {
	
	public enum TypeOfData{
		PRODUCTBATCHCOMP,
		PRODUCTBATCH,
		RAWMATERIALBATCH,
		RAWMATERIAL,
		RECIPECOMP,
		RECIPE,
		USER
	}

	public static void testwrite(Object dto, TypeOfData type){
		FileOutputStream f = null;
		ObjectOutputStream o = null;

		try {
			f = new FileOutputStream(new File(type.toString()+".data"));
			o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(dto);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream " +e);
		}finally{
			try {
				o.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Object testRead(TypeOfData type){

		FileInputStream fi = null;
		ObjectInputStream oi = null;

		try{
			fi = new FileInputStream(new File(type.toString()+".data"));
			oi = new ObjectInputStream(fi);

			Object dto = (Object) oi.readObject();
			
			return dto;

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				oi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

}

