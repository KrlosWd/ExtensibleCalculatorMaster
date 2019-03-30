package myob.technicaltest.calculator.entities;

import java.util.HashMap;

import myob.technicaltest.calculator.exceptions.InvalidInputException;
import myob.technicaltest.calculator.exceptions.OperationException;

public class Addition implements Operation{

	@Override
	public String performOperation(HashMap<String, Object> input) throws OperationException {
		int op1 = (Integer) input.get("op1");
		int op2 = (Integer) input.get("op2");
		return Integer.toString(op1 + op2);
	}

	@Override
	public HashMap<String, String> getExpectedInput() {
		HashMap<String, String> expectedInput = new HashMap<>();
		expectedInput.put("op1", "First operand in the addition");
		expectedInput.put("op2", "Second operand in the addition");
		return expectedInput;
	}

	@Override
	public void validateInput(HashMap<String, Object> input) throws InvalidInputException {
		if(!input.containsKey("op1")){
			throw new InvalidInputException("Missing argument op1");
		}
		if(!input.containsKey("op2")){
			throw new InvalidInputException("Missing argument op2");
		}
		
	}

	
}
