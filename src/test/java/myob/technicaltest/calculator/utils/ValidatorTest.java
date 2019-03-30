/**
 * 
 */
package myob.technicaltest.calculator.utils;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import myob.technicaltest.calculator.exceptions.InvalidValueException;

/**
 * @author Juan Carlos Fuentes Carranza
 *
 */
public class ValidatorTest {
	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Test
	public void testValidateInteger_invalidInteger1() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "12312.1";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		System.out.println("testValidateInteger_invalidInteger1: Testing Validator.validateInteger("+value+")");
		Validator.validateInteger("12312.1");
	}
	
	@Test
	public void testValidateInteger_invalidInteger2() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "Some string";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		System.out.println("testValidateInteger_invalidInteger2: Testing Validator.validateInteger("+value+")");
		Validator.validateInteger(value);
	}
	
	@Test
	public void testValidateInteger_invalidInteger3() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "12312a";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		System.out.println("testValidateInteger_invalidInteger3: Testing Validator.validateInteger("+value+")");
		Validator.validateInteger(value);
	}
	
	@Test
	public void testValidateInteger_tooHigh() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "2147483650";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		System.out.println("testValidateInteger_tooHigh: Testing Validator.validateInteger("+ value +")");
		Validator.validateInteger("2147483650");
	}
	
	@Test
	public void testValidateInteger_tooLow() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "-2147483650";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		System.out.println("testValidateInteger_tooLow: Testing Validator.validateInteger("+value+")");
		Validator.validateInteger(value);
	}

	@Test
	public void testValidateInteger_OutOfRange1() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage(startsWith("Out of range"));
		System.out.println("testValidateInteger_OutOfRange1: Testing Validator.validateInteger(100, 110, 150)");
		Validator.validateInteger("100", 110, 150);
	}
	
	@Test
	public void testValidateInteger_OutOfRange2() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage(startsWith("Out of range"));
		System.out.println("testValidateInteger_OutOfRange2: Testing Validator.validateInteger(100, 50, 80)");
		Validator.validateInteger("100", 50, 80);
	}
	
	@Test
	public void testValidateInteger_invalidRange() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage(startsWith("Invalid range"));
		System.out.println("testValidateInteger_invalidRange: Testing Validator.validateInteger(100, 120, 80)");
		Validator.validateInteger("100", 120, 80);
	}
	
	@Test
	public void testValidateInteger_validInteger1() throws InvalidValueException {
		System.out.println("testValidateInteger_validInteger1: Testing Validator.validateInteger(100, 120, 80)");
		int valid = Validator.validateInteger("100", 80, 120);
		assertEquals(100, valid);
	}
	
	@Test
	public void testValidateInteger_validInteger2() throws InvalidValueException {
		System.out.println("testValidateInteger_validInteger2: Testing Validator.validateInteger(100, 120, 80)");
		int valid = Validator.validateInteger("1000");
		assertEquals(1000, valid);
	}
	
	@Test
	public void testValidateInteger_lowerBoundTooSmall() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage("The lowerbound min is too small");
		System.out.println("testValidateInteger_lowerBoundTooSmall: Testing Validator.validateInteger(100, -2147483650, 80)");
		Validator.validateInteger("100", -2147483650L, 80);
	}
	
	@Test
	public void testValidateInteger_upperBoundTooBig() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage("The upperbound max is too big");
		System.out.println("testValidateInteger_lowerBoundTooSmall: Testing Validator.validateInteger(100, 0, 2147483650L)");
		Validator.validateInteger("100", 0, 2147483650L);
	}
}
