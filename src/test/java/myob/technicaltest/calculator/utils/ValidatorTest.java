/**
 * 
 */
package myob.technicaltest.calculator.utils;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		Validator.validateInteger("12312.1");
		fail("Expected InvalidValueException");
	}
	
	@Test
	public void testValidateInteger_invalidInteger2() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "Some string";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		Validator.validateInteger(value);
		fail("Expected InvalidValueException");
	}
	
	@Test
	public void testValidateInteger_invalidInteger3() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "12312a";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		Validator.validateInteger(value);
		fail("Expected InvalidValueException");
	}
	
	@Test
	public void testValidateInteger_tooHigh() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "2147483650";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		Validator.validateInteger("2147483650");
		fail("Expected InvalidValueException");
	}
	
	@Test
	public void testValidateInteger_tooLow() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		String value = "-2147483650";
		String errmsg = "The value ["+ value +"] is not a valid integer";
		expected.expectMessage(errmsg);
		Validator.validateInteger(value);
		fail("Expected InvalidValueException");
	}

	@Test
	public void testValidateInteger_OutOfRange1() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage(startsWith("Out of range"));
		Validator.validateInteger("100", 110, 150);
		fail("Expected InvalidValueException");
	}
	
	@Test
	public void testValidateInteger_OutOfRange2() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage(startsWith("Out of range"));		
		Validator.validateInteger("100", 50, 80);
		fail("Expected InvalidValueException");
	}
	
	@Test
	public void testValidateInteger_invalidRange() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage(startsWith("Invalid range"));
		Validator.validateInteger("100", 120, 80);
		fail("Expected InvalidValueException");
	}
	
	@Test
	public void testValidateInteger_validInteger1() throws InvalidValueException {
		int valid = Validator.validateInteger("100", 80, 120);
		assertEquals(100, valid);
	}
	
	@Test
	public void testValidateInteger_validInteger2() throws InvalidValueException {
		int valid = Validator.validateInteger("1000");
		assertEquals(1000, valid);
	}
	
	@Test
	public void testValidateInteger_lowerBoundTooSmall() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage("The lowerbound min is too small");
		Validator.validateInteger("100", -2147483650L, 80);
		fail("Expected InvalidValueException");
	}
	
	@Test
	public void testValidateInteger_upperBoundTooBig() throws InvalidValueException {
		expected.expect(InvalidValueException.class);
		expected.expectMessage("The upperbound max is too big");
		Validator.validateInteger("100", 0, 2147483650L);
		fail("Expected InvalidValueException");
	}
}
