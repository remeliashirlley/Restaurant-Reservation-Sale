package restaurant.controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * <p> Abstract controller class that handles reading and writing of IO files using {@link FileInputStream}, {@link ObjectInputStream},
 * {@link FileOutputStream}, {@link ObjectOutputStream}.
 * 
 * <p> All controllers following the MVC design pattern will inherit this class for IO operations pertaining to their Model classes.
 *
 * @author Muhaimin, Hakiim
 * @param <T> The type of model
 * 
 * @see FileInputStream
 * @see ObjectInputStream
 * @see FileOutputStream
 * @see ObjectOutputStream
 * 
 */
public abstract class AbstractController<T> {

//	private FileInputStream fileInputStream;
//	private ObjectInputStream objectInputStream;
//	private FileOutputStream fileOutputStream;
//	private ObjectOutputStream objectOutputStream;

	/**
     * 
     * <p>Executes input operation on IO file based on directory from parameter. During run-time, the type of Model will be retrieved from the
     * child controllers and {@link ObjectInputStream} will read the file based on the Model type.
     * 
     * @param directory - The directory of the io file
     * @return <b>T The model based on type from child controller
     */
	protected T loadReadIOFile(String directory) {

	
		T obj = null;
		File tFile = new File(directory);

		try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(tFile))) {
//			fileInputStream = new FileInputStream(directory);
//			objectInputStream = new ObjectInputStream(fileInputStream);
			obj = (T) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			try {
				tFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (EOFException e) {
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File failed to close successfully");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return obj;
	}

	/**
     * 
     * <p>Executes output operation on IO file based on directory from parameter. During run-time, the type of Model will be retrieved from the
     * child controllers and {@link ObjectOutputStream} will write the file based on the Model type and {@code obj} from parameter.
     * 
     * @param directory - The directory of the io file
     * @param obj - The model based on type from child controller
     */
	protected void writeSaveIOFile(String directory, T obj) {

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(directory))) {
//			fileOutputStream = new FileOutputStream(directory);
//			objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(obj);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File failed to close successfully");
		}
	}
}
