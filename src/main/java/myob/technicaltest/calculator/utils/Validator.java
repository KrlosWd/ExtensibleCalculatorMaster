package myob.technicaltest.calculator.utils;

import org.apache.commons.validator.routines.IntegerValidator;
import myob.technicaltest.calculator.exceptions.InvalidValueException;

public class Validator {
    /**
     * Checks if the integer represented by the string s is a valid integer in the range [min - max]
     *
     * @param s		String representation of a possible integer value to evaluate
     * @param min	minimum value (not less than Integer.MIN_VALUE)
     * @param max	maximum value (not more than Integer.MAX_VALUE)
     * @return
     * @throws InvalidValueException if min is lower than Integer.MIN_VALUE, max is higher than
     * 			Integer.MAX_VALUE, if s does not represent a valid Integer or if the integer 
     * 			represented by s is out of the valid range.
     */
    public static int validateInteger(String s, long min, long max) throws InvalidValueException {
    	if(min < Integer.MIN_VALUE){
    		throw new InvalidValueException("The lowerbound min is too small");
    	}
    	if(max > Integer.MAX_VALUE){
    		throw new InvalidValueException("The upperbound max is too big");
    	}
    	if(min > max){
    		throw new InvalidValueException("Invalid range [min:"+min+" , max:"+max+"]");
    	}
        if (!IntegerValidator.getInstance().isValid(s)) {
            throw new InvalidValueException("The value [" + s + "] is not a valid integer");
        }
        if (!IntegerValidator.getInstance().isInRange(Long.parseLong(s), min, max)) {
            throw new InvalidValueException("Out of range [" + min + "-" + max +"] value ["+ s +"]");
        }

        return Integer.parseInt(s);
    }

    /**
     * Checks if the integer represented by the string s is a valid integer
     *
     * @param s		String representation of a possible integer value to evaluate
     * @return
     * @throws InvalidValueException if s does not represent a valid Integer or if the integer 
     * 			represented by s is out of the valid range [Integer.MIN_VALUE - Integer.MAX_VALUE].
     */
    public static int validateInteger(String s) throws InvalidValueException {
        return validateInteger(s, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}




