package myob.technicaltest.calculator.entities;

/**
 * Entity used to build a list of loaded CalculatorServices, the list is used as response for the /calculator/status endpoint
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
public class CalculatorServiceEntity {
	private String path;
	private String className;
	
	public CalculatorServiceEntity(String path, String className) {
		this.path = path;
		this.className = className;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public String toString() {
		return "CalculatorServiceEntity [path=" + path + ", className=" + className + "]";
	}
}
