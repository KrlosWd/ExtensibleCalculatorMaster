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
import myob.technicaltest.calculator.entities.Constants;
import myob.technicaltest.calculator.entities.Metadata;
import myob.technicaltest.calculator.entities.ServiceResponse;
import myob.technicaltest.calculator.entities.SimpleResponse;
import myob.technicaltest.calculator.exceptions.CalculatorServiceNotFoundException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.lib.entities.CalculatorServiceDescription;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.MissingParametersException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;
import myob.technicaltest.calculator.utils.CalculatorServiceLoader;
import myob.technicaltest.calculator.utils.PropertiesReader;

@RestController
public class RootController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RootController.class);
	@Autowired
	ServiceManager serviceManager; 
	
	/**
	 * USE CASE 1:	Hello world message
	 * ENDPOINT:	/calculator
	 * METHOD:		GET 
	 * PARAMS:		N/A
	 * Hello World endpoint
	 * @return SimpleResponse message
	 */
	@GetMapping(value = "/calculator", produces = Constants.ENDPOINT_PRODUCES)
	public SimpleResponse helloWorld() {
		log.trace("GET Request to /calculator");
		SimpleResponse response = new SimpleResponse(Constants.WELCOME_MESSAGE);
		log.trace("RESPONSE: " + response);
		return response;
	}
	
	/**
	 * USE CASE 2: Health point status
	 * END POINT:	/calculator/status
	 * METHOD:		GET
	 * PARAMS:		N/A
	 * Endpoint to check the current status of the ExtensibleCalculator. It shows the list of currently 
	 * available services
	 * @return List of currently available services
	 */
	@GetMapping(value = "/calculator/status", produces = Constants.ENDPOINT_PRODUCES)
	public List<CalculatorServiceEntity> getCalculatorServiceList(){
		log.trace("GET Request to /calculator/status");
		LinkedList<CalculatorServiceEntity> entities = new LinkedList<>();
		for(String key: serviceManager.keySet()){
			entities.add(new CalculatorServiceEntity(key, serviceManager.getService(key).getClass().getCanonicalName()));
		}
		log.trace("RESPONSE: " + entities);
		return entities;
	}
	
	/**
	 * USE CASE 3: Project metadata
	 * END POINT:	/calculator/metadata
	 * METHOD:		GET
	 * PARAMS:		N/A
	 * @return Metadata object with metadata info of the project
	 * @throws IOException is an error occurred while loading the properties files containing the metadata
	 */
	@GetMapping(value = "/calculator/metadata", produces = Constants.ENDPOINT_PRODUCES)
	public Metadata getProjectMetadata() throws IOException{
		log.trace("GET Request to /calculator/metadata");
		HashMap<String, String> authormeta = new HashMap<>();
		HashMap<String, String> projectmeta;
		
		authormeta.put("name", Constants.AUTHOR);
		authormeta.put("email", Constants.AUTHOR_EMAIL);
		
		projectmeta = PropertiesReader.loadProperties("maven.properties", 
				new LinkedList<String>(Arrays.asList("name","version", "description")));
		
		Metadata meta  =new Metadata(authormeta, projectmeta) ;
		log.trace("RESPONSE:" + meta);
		return meta;
		
	}
	
	
	/**
	 * USE CASE 4: Request CalculatorService in path 'path'
	 * END POINT:	/calculator/service/{path}
	 * METHOD:		GET
	 * PARAMS:		Those required by the requested service
	 * Endpoint used as router to access all CalculatorService implementations loaded
	 * @param path This is the path related to one CalculatorService
	 * @param allParams Complete list of parameters from the request
	 * @return A service response from the CalculatorService requested
	 * @throws CalculatorServiceNotFoundException if no CalculatorService is associated to the path
	 * @throws MissingParametersException if parameters were missing in the request for the CalculatorService
	 * @throws OperationException if an error occurred during the execution of the service
	 * @throws InvalidInputException if the input was rejected by the CalculatorService
	 */
	@GetMapping(value = "/calculator/service/{path}", produces = Constants.ENDPOINT_PRODUCES)
	public ServiceResponse provideService(@PathVariable String path, 
			@RequestParam MultiValueMap<String, String> allParams) 
					throws CalculatorServiceNotFoundException, MissingParametersException, 
					OperationException, InvalidInputException {
		log.trace("GET Request to /calculator/service/" + path);
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
		log.trace("RESPONSE:" + res);
		return res;
	}
	
	
	/**
	 * USE CASE 5: Request help information of the CalculatorService in path 'path'
	 * END POINT:	/calculator/service/{path}/help
	 * METHOD:		GET
	 * PARAMS:		N/A
	 * Endpoint to get help of any given CalculatorService
	 * @param path The path to the CalculatorService
	 * @return help message for the CalculatorService
	 * @throws CalculatorServiceNotFoundException if no CalculatorService was found in path
	 */
	@GetMapping(value = "/calculator/service/{path}/help", produces = Constants.ENDPOINT_PRODUCES)
	public CalculatorServiceDescription provideServiceHelp(@PathVariable String path) throws CalculatorServiceNotFoundException {
		log.trace("GET Request to /calculator/service/" + path + "/help");
		if(!serviceManager.containsService(path)) {
			throw new CalculatorServiceNotFoundException(path);
		}
		CalculatorService srv = serviceManager.getService(path);
		CalculatorServiceDescription desc = srv.getDescription();
		log.trace("RESPONSE:" + desc);
		return desc;
	}
	
}
