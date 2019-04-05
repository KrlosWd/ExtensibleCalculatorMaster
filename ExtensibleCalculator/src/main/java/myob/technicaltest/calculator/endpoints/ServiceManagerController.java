package myob.technicaltest.calculator.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.entities.Constants;
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
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServiceManagerController.class);
	@Autowired
	ServiceManager serviceManager; 
	
	/**
	 * USE CASE 6:	Load jar file in a given path
	 * END POINT:	/calculator/manager/jar
	 * METHOD:		POST
	 * PARAMS:		- filepath Absolute path to the jar to be loaded
	 * Endpoint used to load a jar given its absolute path in the system
	 * @param filepath	absolute path of the jarfile to load
	 * @return	Response message
	 * @throws JarNotFoundException	if the jar was not found
	 * @throws NotAJarFileException if the file is not a jar file
	 * @throws UnableToReadJarException if it was not possible to load the jar
	 * @throws JarAlreadyLoadedException if the jar had been loaded previously
	 * @throws ClassAlreadyLoadedException if one class of the jar had been loaded before
	 */
	@PostMapping(value = "/calculator/manager/jar", produces = Constants.ENDPOINT_PRODUCES)
	public SimpleResponse loadJar(@RequestParam String filepath) throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		serviceManager.loadJarfile(filepath);
		log.trace("POST request to /calculator/manager/jar");
		SimpleResponse res = new SimpleResponse("Jarfile ["+filepath+"] loaded successfully!");
		log.trace("RESPONSE: "+ res);
		return res;
	}
	
	/**
	 * USE CASE 7:	Load a CalculatorService into a given path
	 * END POINT:	/calculator/manager/service
	 * METHOD:		PUT
	 * PARAMS:		- path Path where the CalculatorService is to be set (See Use cases 4 and 5)
	 *  			- className the canonical name of the class to be loaded
	 * Used to load a CalculatorService provided that the jar containing it had been loaded before
	 * @param path	path where the service will be available at (e.g., /calculator/service/{path})
	 * @param className Name of the class to be loaded
	 * @return Response message
	 * @throws ClassNotFoundException if the class className was not found
	 * @throws InstantiationException if an error occurred while instantiating the class
	 * @throws NotACalculatorServiceException if the class is not a CalculatorService
	 * @throws IllegalAccessException if an illegal access was attempted as a result of loading the class
	 */
	@PutMapping(value = "/calculator/manager/service", produces = Constants.ENDPOINT_PRODUCES)
	public SimpleResponse loadService(@RequestParam String path, @RequestParam String className) 
			throws ClassNotFoundException, InstantiationException, NotACalculatorServiceException, IllegalAccessException {
		serviceManager.setService(path, className);
		log.trace("PUT request to /calculator/manager/service");
		SimpleResponse res = new SimpleResponse("CalculatorService ["+className+"] loaded in path ["+path+"] successfully!");
		log.trace("RESPONSE: "+ res);
		return res;
	}
	
	/**
	 * USE CASE 8:	Removes the CalculatorService servicing in a given path
	 * END POINT:	/calculator/manager/service
	 * METHOD:		DELETE
	 * PARAMS:		- path Path of the CalculatorService to be removed
	 * Endpoint used to remove a service from the list of available services. WARNING: this method does not "unloads" the class
	 * related to the service from the ClassLoader
	 * @param path the service to remove
	 * @return Response message
	 * @throws CalculatorServiceNotFoundException if the service in "path" does not exists
	 */
	@DeleteMapping(value = "/calculator/manager/service", produces = Constants.ENDPOINT_PRODUCES)
	public SimpleResponse removeService(@RequestParam String path) throws CalculatorServiceNotFoundException {
		log.trace("DELETE request to /calculator/manager/service");
		if(!serviceManager.containsService(path)) {
			throw new CalculatorServiceNotFoundException(path);
		}
		serviceManager.removeService(path);
		SimpleResponse res = new SimpleResponse("CalculatorService in path ["+path+"] removed successfully!");
		log.trace("RESPONSE: "+ res);
		return res;
	}
	
	/**
	 * USE CASE 9:	Removes all CalculatorService's
	 * END POINT:	/calculator/manager/allServices
	 * METHOD:		DELETE
	 * PARAMS:		- path Path of the CalculatorService to be removed
	 * End point used to remove all services from the lists of available services. WARNINNG: this method does not "unload" classes
	 * related to the services from the ClassLoader
	 * @return Response Message
	 */
	@DeleteMapping(value = "/calculator/manager/allServices", produces = Constants.ENDPOINT_PRODUCES)
	public SimpleResponse removeAllServices() {
		log.trace("DELETE request to /calculator/manager/allServices");
		int serviceCount = serviceManager.getServicesCount();
		serviceManager.emptyServices();
		SimpleResponse res = new SimpleResponse("Removed ["+serviceCount+"] CalculatorServices successfully!");
		log.trace("RESPONSE: "+ res);
		return res;
	}
}
