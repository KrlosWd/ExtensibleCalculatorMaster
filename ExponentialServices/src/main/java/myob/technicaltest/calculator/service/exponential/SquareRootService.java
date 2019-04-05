package myob.technicaltest.calculator.service.exponential;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.lib.entities.CalculatorServiceDescription;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.InvalidValueException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;
import myob.technicaltest.calculator.lib.utils.Validator;

public class SquareRootService extends CalculatorService{

	@Override
	public String performOperation(Map<String, List<String>> input) throws OperationException {
		List<String> radicands = input.get("radicand");
		List<Double> results = new LinkedList<>(); 
		
		double result;
		for(String radicand: radicands) {
			try {
				result = Math.sqrt(Validator.validateInteger(radicand, 0, Integer.MAX_VALUE));
			} catch (InvalidValueException e) {
				throw new OperationException(e.getMessage());
			}
			results.add(result);
		}
		return results.toString();
	}

	@Override
	public void validateInput(Map<String, List<String>> input) throws InvalidInputException {
		if(!input.containsKey("radicand")) {
			throw new InvalidInputException("Missing argument [radicand]");
		}
		
		List<String> radicands = input.get("radicand");
		
		for(String radicand: radicands) {
			try {
				Validator.validateInteger(radicand, 0, Integer.MAX_VALUE);
			} catch (InvalidValueException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
	}

	@Override
	public CalculatorServiceDescription getDescription() {
		CalculatorServiceDescription description = new CalculatorServiceDescription();
		HashMap<String, String> parameters = new HashMap<>(); 
		description.setDescription("SquareRootService: Takes a list [r_1, r_2, ..., r_n] of radicands and returns a String representation of the list [s_1, s_2, ..., s_n] where s_i represents the square root of r_i");
		parameters.put("radicand", "List of n radicand numbers");
		description.setParameters(parameters);
		description.setOutput("String representation of the list [s_1, s_2, ..., s_n]");
		return description;
	}
}
