package myob.technicaltest.calculator.service.exponential;

import static org.hamcrest.CoreMatchers.startsWith;
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

public class PowerServiceTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();
	private static PowerService power = new PowerService();

	@Test
	public void testValidateInput() throws InvalidInputException {
		expected.expect(InvalidInputException.class);
		expected.expectMessage(startsWith("Missing argument ["));
		HashMap<String, List<String>> input = new HashMap<>();
		power.validateInput(input);
		fail("Expected InvalidInputException");
	}
	
	@Test
	public void testValidateInput2() throws InvalidInputException {
		expected.expect(InvalidInputException.class);
		expected.expectMessage("The value [not an integer] is not a valid integer");
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("base", new LinkedList<>());
		input.get("base").add("10");
		input.get("base").add("20");
		input.put("exponent", new LinkedList<>());
		input.get("exponent").add("2");
		input.get("exponent").add("not an integer");
		power.validateInput(input);
		fail("Expected InvalidInputException");
	}

	@Test
	public void testExecuteService() throws MissingParametersException, OperationException, InvalidInputException {
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("base", new LinkedList<>());
		input.get("base").add("10");
		input.get("base").add("20");
		input.put("exponent", new LinkedList<>());
		input.get("exponent").add("2");
		input.get("exponent").add("3");
		String result = power.executeService(input);
		LinkedList<Double> powers = new LinkedList<>();
		powers.add(Math.pow(10, 2));
		powers.add(Math.pow(20, 3));
		assertEquals(powers.toString(), result);	
	}
	
	
	@Test
	public void testExecuteService2() throws MissingParametersException, OperationException, InvalidInputException {
		expected.expect(MissingParametersException.class);
		HashMap<String, List<String>> input = new HashMap<>();
		power.executeService(input);
		fail("Expected MissinParametersException");
	}

}
