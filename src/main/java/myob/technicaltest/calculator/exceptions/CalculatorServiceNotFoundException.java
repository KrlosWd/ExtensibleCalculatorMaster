package myob.technicaltest.calculator.exceptions;

public class CalculatorServiceNotFoundException extends Exception {
	private static final long serialVersionUID = 8839957347835247425L;

	public CalculatorServiceNotFoundException(String path) {
		super("Calculator Service for ("+path+") not found");
	}

}
