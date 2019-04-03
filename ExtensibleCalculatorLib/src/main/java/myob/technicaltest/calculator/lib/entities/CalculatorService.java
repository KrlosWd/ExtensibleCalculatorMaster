package myob.technicaltest.calculator.lib.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.MissingParametersException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;

public abstract class CalculatorService {

	public abstract String performOperation(Map<String, List<String>> input) throws OperationException;
	
	public abstract List<String> getExpectedParameters();
	
	public abstract void validateInput(Map<String, List<String>> input) throws InvalidInputException;
	
	public abstract String getDescription();
	
	public String executeService(Map<String, List<String>> parameters) throws MissingParametersException, OperationException, InvalidInputException {
		List<String> expectedParameters = getExpectedParameters();
		Set<String> keys = parameters.keySet();
		Map<String, List<String>> input = new HashMap<>();
		
		//We filter out unexpected parameters
		for(String param: expectedParameters) {
			if(!keys.contains(param)) {
				throw new MissingParametersException("The parameter ["+param+"] is missing");
			}
			input.put(param, parameters.get(param));
		}	
		
		validateInput(input);
		return performOperation(input);
	}
}
