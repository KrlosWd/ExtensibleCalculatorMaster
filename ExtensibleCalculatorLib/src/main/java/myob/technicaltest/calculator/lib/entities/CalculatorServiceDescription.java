package myob.technicaltest.calculator.lib.entities;

import java.util.Map;

public class CalculatorServiceDescription {
	private String description;
	private Map<String, String> parameters;
	private String output;
	
	public CalculatorServiceDescription(String description, Map<String, String> parameters, String output) {
		this.description = description;
		this.parameters = parameters;
		this.output = output;
	}
	
	public CalculatorServiceDescription() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "CalculatorServiceDescription [description=" + description + ", parameters=" + parameters + ", output="
				+ output + "]";
	}
}
