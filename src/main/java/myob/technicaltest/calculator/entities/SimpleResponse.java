package myob.technicaltest.calculator.entities;

/**
 * POJO that represents the most simple response of the API
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
public class SimpleResponse {
	private String message;

	public SimpleResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ServiceManagerResponse [message=" + message + "]";
	}
}
