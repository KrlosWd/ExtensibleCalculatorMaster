package myob.technicaltest.calculator.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.entities.SimpleResponse;
import myob.technicaltest.calculator.exceptions.CalculatorServiceNotFoundException;
import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;

/**
 * Controller for endpoints of the API related to loading jars and services
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
@RestController
public class ServiceManagerController {
	@Autowired
	ServiceManager serviceManager; 
	
	/**
	 * Endpoint used to load a jar given its absolute path in the system
	 * @param filepath	absolute path of the jarfile to load
	 * @return	Response message
	 * @throws JarNotFoundException	if the jar was not found
	 * @throws NotAJarFileException if the file is not a jar file
	 * @throws UnableToReadJarException if it was not possible to load the jar
	 * @throws JarAlreadyLoadedException if the jar had been loaded previously
	 * @throws ClassAlreadyLoadedException if one class of the jar had been loaded before
	 */
	@PostMapping("/calculator/manager/jar")
	public SimpleResponse loadJar(@RequestParam String filepath) throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		serviceManager.loadJarfile(filepath);
		return new SimpleResponse("Jarfile ["+filepath+"] loaded successfully!");
	}
	
	/**
	 * Used to load a CalculatorService provided that the jar containing it had been loaded before
	 * @param path	path where the service will be available at (e.g., /calculator/service/{path})
	 * @param className Name of the class to be loaded
	 * @return Response message
	 * @throws ClassNotFoundException if the class className was not found
	 * @throws InstantiationException if an error occurred while instantiating the class
	 * @throws NotACalculatorServiceException if the class is not a CalculatorService
	 * @throws IllegalAccessException if an illegal access was attempted as a result of loading the class
	 */
	@PostMapping("/calculator/manager/service")
	public SimpleResponse loadService(@RequestParam String path, @RequestParam String className) 
			throws ClassNotFoundException, InstantiationException, NotACalculatorServiceException, IllegalAccessException {
		serviceManager.setService(path, className);
		return new SimpleResponse("CalculatorService ["+className+"] loaded in path ["+path+"] successfully!");
	}
	
	/**
	 * Endpoint used to remove a service from the list of available services. WARNING: this method does not "unloads" the class
	 * related to the service from the ClassLoader
	 * @param path the service to remove
	 * @return Response message
	 * @throws CalculatorServiceNotFoundException if the service in "path" does not exists
	 */
	@DeleteMapping("/calculator/manager/service")
	public SimpleResponse removeService(@RequestParam String path) throws CalculatorServiceNotFoundException {
		if(!serviceManager.containsService(path)) {
			throw new CalculatorServiceNotFoundException(path);
		}
		serviceManager.removeService(path);
		return new SimpleResponse("CalculatorService in path ["+path+"] removed successfully!");
	}
	
	/**
	 * End point used to remove all services from the lists of available services. WARNINNG: this method does not "unload" classes
	 * related to the services from the ClassLoader
	 * @return Response Message
	 */
	@DeleteMapping("/calculator/manager/allServices")
	public SimpleResponse removeAllServices() {
		int serviceCount = serviceManager.getServicesCount();
		serviceManager.emptyServices();
		return new SimpleResponse("Removed ["+serviceCount+"] CalculatorServices successfully!");
	}
}
