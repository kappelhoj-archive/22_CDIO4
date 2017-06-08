package staticClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dataTransferObjects.DTO;
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
	public static List<DTO> retrieveFrom(String type) throws DALException {
		import java.io.File;
		import java.io.FileInputStream;
		import java.io.FileNotFoundException;
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.io.ObjectInputStream;
		import java.io.ObjectOutputStream;

		public class WriterReader {

			public static void main(String[] args) {

				Person p1 = new Person("John", 30, "Male");
				Person p2 = new Person("Rachel", 25, "Female");

				try {
					FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
					ObjectOutputStream o = new ObjectOutputStream(f);

					// Write objects to file
					o.writeObject(p1);
					o.writeObject(p2);

					o.close();
					f.close();

					FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
					ObjectInputStream oi = new ObjectInputStream(fi);

					// Read objects
					Person pr1 = (Person) oi.readObject();
					Person pr2 = (Person) oi.readObject();

					System.out.println(pr1.toString());
					System.out.println(pr2.toString());

					oi.close();
					fi.close();

				} catch (FileNotFoundException e) {
					System.out.println("File not found");
				} catch (IOException e) {
					System.out.println("Error initializing stream");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
}
