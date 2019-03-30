package myob.technicaltest.calculator.entities;

import java.util.HashMap;

import myob.technicaltest.calculator.exceptions.InvalidInputException;
import myob.technicaltest.calculator.exceptions.OperationException;

public interface Operation {

	public String performOperation(HashMap<String, Object> input) throws OperationException;
	public HashMap<String, String> getExpectedInput();
	public void validateInput(HashMap<String, Object> input) throws InvalidInputException; 
}
