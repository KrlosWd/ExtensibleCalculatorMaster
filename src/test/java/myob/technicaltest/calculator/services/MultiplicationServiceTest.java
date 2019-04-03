/**
 * 
 */
package myob.technicaltest.calculator.services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.MissingParametersException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;
import myob.technicaltest.calculator.services.MultiplicationService;

/**
 * @author Juan Carlos Fuentes Carranza
 *
 */
public class MultiplicationServiceTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();
	private static MultiplicationService multiply = new MultiplicationService();

	@Test
	public void testMultiplication_validateInput() throws InvalidInputException  {
		expected.expect(InvalidInputException.class);
		expected.expectMessage("Missing argument [operand]");
		HashMap<String, List<String>> input = new HashMap<>();
		multiply.validateInput(input);
		fail("Expected InvalidInputException");
	}
	
	@Test
	public void testMultiplication_validateInput2() throws InvalidInputException  {
		expected.expect(InvalidInputException.class);
		expected.expectMessage("The value [not an integer] is not a valid integer");
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("operand", new LinkedList<>());
		input.get("operand").add("not an integer");
		multiply.validateInput(input);
		fail("Expected InvalidInputException");
	}
	
	@Test
	public void testMultiplication_executeService() throws MissingParametersException, OperationException, InvalidInputException {
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("operand", new LinkedList<>());
		input.get("operand").add("10");
		input.get("operand").add("20");
		input.get("operand").add("30");
		String result = multiply.executeService(input);
		assertEquals("6000", result);		
	}
	
	@Test
	public void testMultiplication_executeService2() throws MissingParametersException, OperationException, InvalidInputException {
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("operand", new LinkedList<>());
		input.get("operand").add("10");
		input.get("operand").add("20");
		input.get("operand").add("-30");
		String result = multiply.executeService(input);
		assertEquals("-6000", result);		
	}
	
	@Test
	public void testMultiplication_executeService3() throws MissingParametersException, OperationException, InvalidInputException {
		expected.expect(MissingParametersException.class);
		HashMap<String, List<String>> input = new HashMap<>();
		multiply.executeService(input);
		fail("Expected MissinParametersException");
	}

}
