package myob.technicaltest.calculator.exceptions;

public class JarAlreadyLoadedException extends Exception{
	private static final long serialVersionUID = -7678185450570106268L;

	public JarAlreadyLoadedException(String filename) {
		super("Jar ("+filename+") is already loaded");
	}

}
