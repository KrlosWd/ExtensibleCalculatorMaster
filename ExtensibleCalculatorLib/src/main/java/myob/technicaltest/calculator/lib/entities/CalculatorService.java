package myob.technicaltest.calculator.lib.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.MissingParametersException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;

public abstract class CalculatorService {

	/**
	 * This method is where the users should implement the functionality of their own CalculatorService
	 * @param	input	Map containing the <String key, List<String> values> pairs of the parameters expected by the CalculatorService
	 * 					as defined by the getExpectedParameters method. All values are previously validated by the validateInput method
	 * @return	Users MUST returns a String representation of the result of the operation
	 * @throws	OperationException is expected to be thrown by the user if something goes wrong with their service 
	 * */
	public abstract String performOperation(Map<String, List<String>> input) throws OperationException;	
	
	/**
	 * This method SHOULD be used by the user to define the validation process of this CalculatorService input
	 * @param input		The input to be validated
	 * @throws InvalidInputException if the input is invalid
	 */
	public abstract void validateInput(Map<String, List<String>> input) throws InvalidInputException;
	
	
	/**
	 * This method MUST return a description of the Service along with the names/constrains of the required parameters, and a description
	 * of the output 
	 * @return the description of the service
	 */
	public abstract CalculatorServiceDescription getDescription();
	
	
	/**
	 * Final method used by the ExtensibleCalculator to execute all CalculatorService implementations
	 * @param parameters	list of parameters as received from the GET request
	 * @return	String representation of the response as defined by the method performOperation
	 * @throws MissingParametersException	if any parameter from the list of expected parameters is missing from the input parameters
	 * @throws OperationException	if the list of expected parameters is null or if something goes wrong in the performOperation method
	 * @throws InvalidInputException	if the input is invalid according to the method validateInput
	 */
	public final String executeService(Map<String, List<String>> parameters) throws MissingParametersException, OperationException, InvalidInputException {
		Set<String> expectedParameters = getDescription().getParameters().keySet();
		Set<String> keys = parameters.keySet();
		Map<String, List<String>> input = new HashMap<>();
		
		//We filter out unexpected parameters
		if(expectedParameters == null) {
			throw new OperationException("List of expected parameters MUST NOT be null");
		}
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
