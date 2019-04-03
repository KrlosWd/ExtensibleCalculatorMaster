package myob.technicaltest.calculator.lib.exceptions;

public class InvalidInputException extends CalculatorException{
	private static final long serialVersionUID = -3435482745868154553L;

	public InvalidInputException(String msg) {
		super(msg);
	}

}
