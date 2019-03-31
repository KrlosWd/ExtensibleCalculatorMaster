/**
 * 
 */
package myob.technicaltest.calculator.entities;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import myob.technicaltest.calculator.exceptions.InvalidInputException;
import myob.technicaltest.calculator.exceptions.MissingParametersException;
import myob.technicaltest.calculator.exceptions.OperationException;
import myob.technicaltest.calculator.services.AdditionService;

/**
 * @author Juan Carlos Fuentes Carranza
 *
 */
public class AdditionServiceTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();
	private static AdditionService addition = new AdditionService();

	@Test
	public void testAddition_validateInput() throws InvalidInputException  {
		expected.expect(InvalidInputException.class);
		expected.expectMessage("Missing argument [operand]");
		HashMap<String, List<String>> input = new HashMap<>();
		addition.validateInput(input);
		fail("Expected InvalidInputException");
	}
	
	@Test
	public void testAddition_validateInput2() throws InvalidInputException  {
		expected.expect(InvalidInputException.class);
		expected.expectMessage("The value [not an integer] is not a valid integer");
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("operand", new LinkedList<>());
		input.get("operand").add("not an integer");
		addition.validateInput(input);
		fail("Expected InvalidInputException");
	}
	
	@Test
	public void testAddition_executeService() throws MissingParametersException, OperationException, InvalidInputException {
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("operand", new LinkedList<>());
		input.get("operand").add("10");
		input.get("operand").add("20");
		input.get("operand").add("30");
		String result = addition.executeService(input);
		assertEquals("60", result);		
	}
	
	@Test
	public void testAddition_CalculateResult2() throws MissingParametersException, OperationException, InvalidInputException {
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("operand", new LinkedList<>());
		input.get("operand").add("10");
		input.get("operand").add("20");
		input.get("operand").add("-30");
		String result = addition.executeService(input);
		assertEquals("0", result);		
	}
	
	@Test
	public void testAddition_CalculateResult3() throws MissingParametersException, OperationException, InvalidInputException {
		expected.expect(MissingParametersException.class);
		HashMap<String, List<String>> input = new HashMap<>();
		addition.executeService(input);
		fail("Expected MissinParametersException");
	}

}
