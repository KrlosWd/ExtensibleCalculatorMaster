package myob.technicaltest.calculator.exceptions;

public class ClassAlreadyLoadedException extends Exception {
	private static final long serialVersionUID = 1056376322428849833L;

	public ClassAlreadyLoadedException(String clazzname) {
		super("Class ("+clazzname+") is already loaded");
	}

}
