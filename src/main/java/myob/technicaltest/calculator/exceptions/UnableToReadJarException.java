package myob.technicaltest.calculator.exceptions;

public class UnableToReadJarException extends Exception {
	private static final long serialVersionUID = -4118908731879338276L;

	public UnableToReadJarException(String filename) {
		super("Unable to read file ("+filename+")");
	}

}
