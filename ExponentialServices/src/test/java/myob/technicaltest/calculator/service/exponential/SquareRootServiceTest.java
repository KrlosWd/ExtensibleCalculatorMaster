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

public class SquareRootServiceTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();
	private static SquareRootService squareRoot = new SquareRootService();
	
	@Test
	public void testValidateInput() throws InvalidInputException {
		expected.expect(InvalidInputException.class);
		expected.expectMessage(startsWith("Missing argument ["));
		HashMap<String, List<String>> input = new HashMap<>();
		squareRoot.validateInput(input);
		fail("Expected InvalidInputException");
	}
	
	@Test
	public void testValidateInput2() throws InvalidInputException {
		expected.expect(InvalidInputException.class);
		expected.expectMessage("The value [not an integer] is not a valid integer");
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("radicand", new LinkedList<>());
		input.get("radicand").add("10");
		input.get("radicand").add("not an integer");
		squareRoot.validateInput(input);
		fail("Expected InvalidInputException");
	}
	
	@Test
	public void testExecuteService() throws MissingParametersException, OperationException, InvalidInputException {
		HashMap<String, List<String>> input = new HashMap<>();
		input.put("radicand", new LinkedList<>());
		input.get("radicand").add("10");
		input.get("radicand").add("20");
		String result = squareRoot.executeService(input);
		LinkedList<Double> squareRoots = new LinkedList<>();
		squareRoots.add(Math.sqrt(10));
		squareRoots.add(Math.sqrt(20));
		assertEquals(squareRoots.toString(), result);	
	}
	
	
	@Test
	public void testExecuteService2() throws MissingParametersException, OperationException, InvalidInputException {
		expected.expect(MissingParametersException.class);
		HashMap<String, List<String>> input = new HashMap<>();
		squareRoot.executeService(input);
		fail("Expected MissinParametersException");
	}

}
