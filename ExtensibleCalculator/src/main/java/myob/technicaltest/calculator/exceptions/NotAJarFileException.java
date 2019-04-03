package myob.technicaltest.calculator.exceptions;

public class NotAJarFileException extends Exception{
	private static final long serialVersionUID = -8991787287584017251L;

	public NotAJarFileException(String filename) {
		super("file ("+filename+") is not a Jar file");
	}

}
