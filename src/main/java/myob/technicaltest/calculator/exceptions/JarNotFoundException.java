package myob.technicaltest.calculator.exceptions;

public class JarNotFoundException extends Exception{
	private static final long serialVersionUID = 2226061731864728097L;

	public JarNotFoundException(String filename) {
		super("Jar ("+filename+") not found");
	}

}
