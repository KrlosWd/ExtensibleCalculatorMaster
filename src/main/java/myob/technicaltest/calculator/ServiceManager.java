package myob.technicaltest.calculator;

import java.util.HashMap;

import myob.technicaltest.calculator.services.CalculatorService;

public class ServiceManager {
	private static ServiceManager INSTANCE = null;
	private final HashMap<String, CalculatorService> services;
	
	private ServiceManager() {
		services = new HashMap<>();
	}
	
	public static synchronized ServiceManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ServiceManager();
		}
		return INSTANCE;
	}
	
	public CalculatorService getService(String key) {
		return null;
	}
	
	public void setService(String key, CalculatorService service) {
		
	}
	
	public CalculatorService removeService(String key) {
		return null;
	}
	
	public void emptyServices() {
		
	}
	
	public int getServicesCount() {
		return 0;
	}
}
