package myob.technicaltest.calculator.endpoints;

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.entities.CalculatorServiceEntity;
import myob.technicaltest.calculator.entities.ServiceResponse;
import myob.technicaltest.calculator.exceptions.CalculatorServiceNotFoundException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.MissingParametersException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;

@RestController
public class RootController {

	@GetMapping("/calculator")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping(value = "/calculator/status", produces = "application/json; charset=UTF-8")
	public List<CalculatorServiceEntity> getCalculatorServiceList(){
		LinkedList<CalculatorServiceEntity> entities = new LinkedList<>();
		for(String key: ServiceManager.getInstance().keySet()){
			entities.add(new CalculatorServiceEntity(key, ServiceManager.getInstance().getService(key).getClass().getCanonicalName()));
		}
		return entities;
	}
	
	@GetMapping(value = "/calculator/service/{path}", produces = "application/json; charset=UTF-8")
	public ServiceResponse provideService(@PathVariable String path, 
			@RequestParam MultiValueMap<String, String> allParams) 
					throws CalculatorServiceNotFoundException, MissingParametersException, 
					OperationException, InvalidInputException {
		if(!ServiceManager.getInstance().containsService(path)) {
			throw new CalculatorServiceNotFoundException(path);
		}
		CalculatorService srv = ServiceManager.getInstance().getService(path);
		String output = srv.executeService(allParams);
		long iniTime = System.currentTimeMillis();		
		ServiceResponse res = new ServiceResponse(System.currentTimeMillis() - iniTime, 
				allParams.toString(), 
				output, 
				srv.getClass().getName());
		return res;
	}
	
}
