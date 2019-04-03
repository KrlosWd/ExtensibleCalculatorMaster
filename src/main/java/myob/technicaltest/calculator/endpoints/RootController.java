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
	
	@GetMapping(value = "/calculator", produces = "application/json; charset=UTF-8")
	public SimpleResponse helloWorld() {
		return new SimpleResponse("Hello World");
	}
	
	@GetMapping(value = "/calculator/status", produces = "application/json; charset=UTF-8")
	public List<CalculatorServiceEntity> getCalculatorServiceList(){
		LinkedList<CalculatorServiceEntity> entities = new LinkedList<>();
		for(String key: serviceManager.keySet()){
			entities.add(new CalculatorServiceEntity(key, serviceManager.getService(key).getClass().getCanonicalName()));
		}
		return entities;
	}
	
	@GetMapping(value = "/calculator/metadata", produces = "application/json; charset=UTF-8")
	public Metadata getProjectMetadata() throws IOException{
		HashMap<String, String> authormeta = new HashMap<>();
		HashMap<String, String> gitmeta;
		HashMap<String, String> mavenmeta;
		
		authormeta.put("name", "Juan Carlos Fuentes Carranza");
		authormeta.put("email", "juan.fuentes.carranza@gmail.com");
		
		gitmeta = PropertiesReader.loadProperties("git.properties", 
				new LinkedList<String>(Arrays.asList("git.branch","git.build.version", "git.commit.id")));
		
		mavenmeta = PropertiesReader.loadProperties("maven.properties", 
				new LinkedList<String>(Arrays.asList("name","version", "description")));
		
		return new Metadata(authormeta, mavenmeta, gitmeta);
	}
	
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
	
	
	@GetMapping(value = "/calculator/service/{path}/help", produces = "application/json; charset=UTF-8")
	public SimpleResponse provideServiceHelp(@PathVariable String path) throws CalculatorServiceNotFoundException {
		if(!serviceManager.containsService(path)) {
			throw new CalculatorServiceNotFoundException(path);
		}
		CalculatorService srv = serviceManager.getService(path);
		return new SimpleResponse(srv.getDescription());
	}
	
}
