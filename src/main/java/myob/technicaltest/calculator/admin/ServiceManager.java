package myob.technicaltest.calculator.admin;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.utils.CalculatorServiceLoader;

/**
 * Class used to manage available CalculatorService implementations and to gain access to the CalculatorServiceLoader
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
@Component
@Scope("singleton")
public class ServiceManager {
	/**
	 * Map containing the available CalculatorServices
	 */
	private final HashMap<String, CalculatorService> services;

	
	public ServiceManager() {
		services = new HashMap<>();
	}
	
	/**
	 * Returns the CalculatorService in a given key
	 * @param key Key for the CalculatorService
	 * @return The CalculatorService implementation requested
	 */
	public CalculatorService getService(String key) {
		return services.get(key);
	}
	
	/**
	 * Sets or Overrides key with the given CalculatorService Implementation
	 * @param key Key to be set or overridden
	 * @param service The CalculatorService implementation to set
	 */
	public void setService(String key, CalculatorService service) {
		services.put(key, service);
	}
	
	/**
	 * Attempts to load the class className and create an instance of it, and the make it available through the key key
	 * @param key Key where the instance of the CalculatorService represented by the className is to be stored
	 * @param className name of the class to be loaded and instantiated
	 * @throws ClassNotFoundException if the class was not found
	 * @throws InstantiationException if an error occurred during the instantiation of the class
	 * @throws IllegalAccessException if an illegal access was attempted as a result of the class instantiation
	 * @throws NotACalculatorServiceException if the class className is not a CalculatorService
	 */
	public void setService(String key, String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NotACalculatorServiceException {
		services.put(key, CalculatorServiceLoader.getInstance().getServiceInstance(className));
	}
	
	/**
	 * Service to be removed from the map of available CalculatorServices
	 * @param key key of the service to be removed
	 * @return the instance of the CalculatorClass removed
	 */
	public CalculatorService removeService(String key) {
		return services.remove(key);
	}
	
	/**
	 * Removes all available CalculatorServices
	 */
	public void emptyServices() {
		services.clear();
	}
	
	/**
	 * Returns the number of available CalculatorService's
	 * @return the count of available CalculatorService's
	 */
	public int getServicesCount() {
		return services.size();
	}
	
	/**
	 * Attempts to load the jar file in the given filepath
	 * @param filepath jar to be loaded
	 * @throws JarNotFoundException if the file was not found
	 * @throws NotAJarFileException if the file is not a jar file
	 * @throws UnableToReadJarException if it was not possible to load the jar due to missing permissions
	 * @throws JarAlreadyLoadedException if the jar had been loaded before
	 * @throws ClassAlreadyLoadedException if one class in the jar had already been loaded
	 */
	public void loadJarfile(String filepath) throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		File file = new File(filepath);
		CalculatorServiceLoader.getInstance().loadJarfile(file);
	}
	
	/**
	 * Returns a Set with all the keys of available CalculatorService's
	 * @return the key set
	 */
	public Set<String> keySet(){
		return services.keySet();
	}
	
	/**
	 * Returns true if the key exist in the map of CalculatorService's and false otherwise 
	 * @param key the key to find
	 * @return true if the key exist in the map of CalculatorService's and false otherwise
	 */
	public boolean containsService(String key) {
		return services.containsKey(key);
	}
}
