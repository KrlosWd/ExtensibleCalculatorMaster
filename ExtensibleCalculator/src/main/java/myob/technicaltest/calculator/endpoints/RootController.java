package myob.technicaltest.calculator.endpoints;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.entities.CalculatorServiceEntity;
import myob.technicaltest.calculator.entities.Metadata;
import myob.technicaltest.calculator.entities.ServiceResponse;
import myob.technicaltest.calculator.entities.SimpleResponse;
import myob.technicaltest.calculator.exceptions.CalculatorServiceNotFoundException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.MissingParametersException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;
import myob.technicaltest.calculator.utils.PropertiesReader;

@RestController
public class RootController {
	@Autowired
	ServiceManager serviceManager; 
	
	/**
	 * Hello World endpoint
	 * @return SimpleResponse message
	 */
	@GetMapping(value = "/calculator", produces = "application/json; charset=UTF-8")
	public SimpleResponse helloWorld() {
		return new SimpleResponse("Hello World");
	}
	
	/**
	 * Endpoint to check the current status of the ExtensibleCalculator. It shows the list of currently 
	 * available services
	 * @return List of currently available services
	 */
	@GetMapping(value = "/calculator/status", produces = "application/json; charset=UTF-8")
	public List<CalculatorServiceEntity> getCalculatorServiceList(){
		LinkedList<CalculatorServiceEntity> entities = new LinkedList<>();
		for(String key: serviceManager.keySet()){
			entities.add(new CalculatorServiceEntity(key, serviceManager.getService(key).getClass().getCanonicalName()));
		}
		return entities;
	}
	
	/**
	 * Metadata endpoint
	 * @return Metadata object with metadata info of the project
	 * @throws IOException is an error occurred while loading the properties files containing the metadata
	 */
	@GetMapping(value = "/calculator/metadata", produces = "application/json; charset=UTF-8")
	public Metadata getProjectMetadata() throws IOException{
		HashMap<String, String> authormeta = new HashMap<>();
		HashMap<String, String> projectmeta;
		
		authormeta.put("name", "Juan Carlos Fuentes Carranza");
		authormeta.put("email", "juan.fuentes.carranza@gmail.com");
		
		projectmeta = PropertiesReader.loadProperties("maven.properties", 
				new LinkedList<String>(Arrays.asList("name","version", "description", "git.branch", "git.commit.id", "git.build.version")));
		
		return new Metadata(authormeta, projectmeta);
	}
	
	
	/**
	 * Endpoint used as router to access all CalculatorService implementations loaded
	 * @param path This is the path related to one CalculatorService
	 * @param allParams Complete list of parameters from the request
	 * @return A service response from the CalculatorService requested
	 * @throws CalculatorServiceNotFoundException if no CalculatorService is associated to the path
	 * @throws MissingParametersException if parameters were missing in the request for the CalculatorService
	 * @throws OperationException if an error occurred during the execution of the service
	 * @throws InvalidInputException if the input was rejected by the CalculatorService
	 */
	@GetMapping(value = "/calculator/service/{path}", produces = "application/json; charset=UTF-8")
	public ServiceResponse provideService(@PathVariable String path, 
			@RequestParam MultiValueMap<String, String> allParams) 
					throws CalculatorServiceNotFoundException, MissingParametersException, 
					OperationException, InvalidInputException {
		if(!serviceManager.containsService(path)) {
			throw new CalculatorServiceNotFoundException(path);
		}
		CalculatorService srv = serviceManager.getService(path);
		String output = srv.executeService(allParams);
		long iniTime = System.nanoTime();		
		ServiceResponse res = new ServiceResponse(System.nanoTime() - iniTime, 
				allParams.toString(), 
				output, 
				srv.getClass().getName());
		return res;
	}
	
	
	/**
	 * Endpoint to get help of any given CalculatorService
	 * @param path The path to the CalculatorService
	 * @return help message for the CalculatorService
	 * @throws CalculatorServiceNotFoundException if no CalculatorService was found in path
	 */
	@GetMapping(value = "/calculator/service/{path}/help", produces = "application/json; charset=UTF-8")
	public SimpleResponse provideServiceHelp(@PathVariable String path) throws CalculatorServiceNotFoundException {
		if(!serviceManager.containsService(path)) {
			throw new CalculatorServiceNotFoundException(path);
		}
		CalculatorService srv = serviceManager.getService(path);
		return new SimpleResponse(srv.getDescription());
	}
	
}
