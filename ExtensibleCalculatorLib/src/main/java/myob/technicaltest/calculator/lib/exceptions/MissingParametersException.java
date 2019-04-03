package myob.technicaltest.calculator.lib.exceptions;

public class MissingParametersException extends CalculatorException{
	private static final long serialVersionUID = 8282373707792223876L;

	public MissingParametersException(String msg) {
		super(msg);
	}

}
