package myob.technicaltest.calculator.utils;

import java.io.File;

import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.utils.JarfileClassLoader;

/**
 * Wrapper to faccilitate the use of the JarfileClassLoader
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
public class CalculatorServiceLoader {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CalculatorServiceLoader.class);
	/**
	 * The class is implemented as a Singleton to ensure that only one instance is created
	 */
	private static CalculatorServiceLoader INSTANCE = null;
	
	private CalculatorServiceLoader() {
		JarfileClassLoader.getInstance().updateInstance(this.getClass().getClassLoader());
	}
	
	public static synchronized CalculatorServiceLoader getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new CalculatorServiceLoader();
		}
		return INSTANCE;
	}
	
	/**
	 * Tries to load the Jar file represented by the File file
	 * @param file	jar file to be loaded
	 * @throws JarNotFoundException if the jar file is not found
	 * @throws NotAJarFileException if the file is not a jar file
	 * @throws UnableToReadJarException if it was not possible to read the jar file
	 * @throws JarAlreadyLoadedException if the jar had been previously loaded
	 * @throws ClassAlreadyLoadedException if one of the classes in the jar had been previously loaded
	 */
	public synchronized void loadJarfile(File file) throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		log.trace("Trying to load jarfile ("+file.getAbsolutePath()+")");
		JarfileClassLoader.getInstance().addFile(file);
	}
	
	
	
	/**
	 * After loading a jar file with the method loadJarfile this class can be used to create a CalculatorService instance of a class given its name 
	 * @param className Name of the class that we want to create and instance of
	 * @return	returns an object of type CalculatorService
	 * @throws ClassNotFoundException	if the class was not found (e.g., the jar containing it has not been loaded)
	 * @throws InstantiationException	if there was an error instantiating the class
	 * @throws IllegalAccessException	if an invalid access was attempted while loading the class
	 * @throws NotACalculatorServiceException	if the object obtained from the class className is not a CalculatorService
	 */
	public synchronized CalculatorService getServiceInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NotACalculatorServiceException {
		log.trace("Trying to create an instance of class ("+className+")");
		Class<?> clazz = JarfileClassLoader.getInstance().loadClass(className);
		Object obj = clazz.newInstance();
		
		if(obj instanceof CalculatorService) {
			return (CalculatorService) obj;
		} else {
			throw new NotACalculatorServiceException(className);
		}
	} 
}
