package myob.technicaltest.calculator.service.exponential;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.InvalidValueException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;
import myob.technicaltest.calculator.lib.utils.Validator;

public class PowerService extends CalculatorService{

	@Override
	public String performOperation(Map<String, List<String>> input) throws OperationException {
		List<String> bases = input.get("base");
		List<String> exponents = input.get("exponent");
		List<Double> results = new LinkedList<>(); 
		
		if(bases.size() != exponents.size()) {
			throw new OperationException("There has to be as many exponents as bases");
		}
		
		double result;
		for(int i = 0; i < bases.size(); i++) {
			try {
				result = Math.pow(Validator.validateInteger(bases.get(i)), Validator.validateInteger(exponents.get(i)));
				results.add(result);
			} catch (InvalidValueException e) {
				throw new OperationException(e.getMessage());
			}
		}
		return results.toString();
	}

	@Override
	public List<String> getExpectedParameters() {
		LinkedList<String> expectedParams = new LinkedList<>();
		expectedParams.add("base");
		expectedParams.add("exponent");
		return expectedParams;
	}

	@Override
	public void validateInput(Map<String, List<String>> input) throws InvalidInputException {
		if(!input.containsKey("base")) {
			throw new InvalidInputException("Missing argument [base]");
		}
		if(!input.containsKey("exponent")) {
			throw new InvalidInputException("Missing argument [exponent]");
		}
		
		List<String> bases = input.get("base");
		List<String> exponents = input.get("exponent");
		
		if(bases.size() != exponents.size()) {
			throw new InvalidInputException("There has to be as many exponents as bases");
		}
		
		for(String base: bases) {
			try {
				Validator.validateInteger(base);
			} catch (InvalidValueException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
		
		for(String exponent: exponents) {
			try {
				Validator.validateInteger(exponent);
			} catch (InvalidValueException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
	}

	@Override
	public String getDescription() {
		return "PowerService: Takes a list [p_1, p_2, ..., p_n] of exponents and a list [b_1, b_2, ..., b_n] of bases\n"
				+ "and returns a String representation of the list [b_1^p_1, b_2^p_2, ..., b_n^p_n] where b_i^p_i represents b to the pth power\n"
				+ "			@param  base List of n base numbers\n"
				+ "			@param  exponent List of n exponents\n"
				+ "			@return String representation of the list [b_1^p_1, b_2^p_2, ..., b_n^p_n]\n";
	}


	
}
