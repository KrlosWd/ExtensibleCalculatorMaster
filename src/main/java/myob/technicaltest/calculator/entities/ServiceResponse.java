package myob.technicaltest.calculator.entities;

public class ServiceResponse {
	private long responseTime;
	private String input;
	private String output;
	private String serviceProvider;
	
	public ServiceResponse(long responseTime, String input, String output, String serviceProvider) {
		this.responseTime = responseTime;
		this.input = input;
		this.output = output;
		this.serviceProvider = serviceProvider;
	}

	public long getResponseTime() {
		return responseTime;
	}
	
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
}
