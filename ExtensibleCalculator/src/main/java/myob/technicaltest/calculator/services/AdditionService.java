package myob.technicaltest.calculator.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.InvalidValueException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;
import myob.technicaltest.calculator.lib.utils.Validator;

/**
 * Basic CalculatorService used to multiply numbers from a list
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
public class AdditionService extends CalculatorService{

	@Override
	public String performOperation(Map<String, List<String>> input) throws OperationException {
		List<String> operands = input.get("operand");
		int total = 0;
		for(String operand: operands) {
			try {
				total += Validator.validateInteger(operand);
			} catch (InvalidValueException e) {
				throw new OperationException(e.getMessage());
			}
		}
		return Integer.toString(total);
	}

	@Override
	public List<String> getExpectedParameters() {
		LinkedList<String> expectedParams = new LinkedList<>();
		expectedParams.add("operand");
		return expectedParams;
	}

	@Override
	public void validateInput(Map<String, List<String>> input) throws InvalidInputException {
		if(!input.containsKey("operand")) {
			throw new InvalidInputException("Missing argument [operand]");
		}
		
		List<String> operands = input.get("operand");
		for(String operand: operands) {
			try {
				Validator.validateInteger(operand);
			} catch (InvalidValueException e) {
				throw new InvalidInputException(e.getMessage());
			}
		}
	}

	@Override
	public String getDescription() {
		return "Addition Service: Calculates the addition of 'n' numbers\n"
				+ "			@param  operand List of one or more numbers to add\n"
				+ "			@return The sum of all operand values provided\n";
	}


	
}
