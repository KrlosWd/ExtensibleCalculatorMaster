package myob.technicaltest.calculator.exceptions;

public class NotACalculatorServiceException extends Exception {
	private static final long serialVersionUID = -4118908731879338276L;

	public NotACalculatorServiceException(String classname) {
		super("Instance from class ("+classname+") is not a CalculatorService");
	}

}
