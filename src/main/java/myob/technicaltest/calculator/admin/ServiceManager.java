package myob.technicaltest.calculator.admin;

import java.util.HashMap;

import myob.technicaltest.calculator.lib.entities.CalculatorService;

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
		return services.get(key);
	}
	
	public void setService(String key, CalculatorService service) {
		services.put(key, service);
	}
	
	public CalculatorService removeService(String key) {
		return services.remove(key);
	}
	
	public void emptyServices() {
		services.clear();
	}
	
	public int getServicesCount() {
		return services.size();
	}
}
