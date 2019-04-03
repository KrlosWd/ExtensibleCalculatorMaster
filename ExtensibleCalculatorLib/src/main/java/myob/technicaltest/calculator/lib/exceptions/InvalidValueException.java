package myob.technicaltest.calculator.lib.exceptions;



public class InvalidValueException extends CalculatorException{
	private static final long serialVersionUID = 4704663055332173946L;

	public InvalidValueException(String msg) {
		super(msg);
	}

}
