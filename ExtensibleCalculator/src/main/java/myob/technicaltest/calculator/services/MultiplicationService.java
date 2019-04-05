package myob.technicaltest.calculator.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.lib.entities.CalculatorServiceDescription;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.InvalidValueException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;
import myob.technicaltest.calculator.lib.utils.Validator;

/**
 * Basic CalculatorService used to add up numbers from a list
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
public class MultiplicationService extends CalculatorService{

	@Override
	public String performOperation(Map<String, List<String>> input) throws OperationException {
		List<String> operands = input.get("operand");
		int total = 1;
		for(String operand: operands) {
			try {
				total *= Validator.validateInteger(operand);
			} catch (InvalidValueException e) {
				throw new OperationException(e.getMessage());
			}
		}
		return Integer.toString(total);
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
	public CalculatorServiceDescription getDescription() {
		CalculatorServiceDescription description = new CalculatorServiceDescription();
		HashMap<String, String> parameters = new HashMap<>(); 
		description.setDescription("MultiplicationService: Calculates the multiplication of 'n' numbers");
		parameters.put("operand", "operand List of one or more numbers to multiply");
		description.setParameters(parameters);
		description.setOutput("The multiplication of all operand values provided");
		return description;
	}


	
}
