package myob.technicaltest.calculator;

import java.security.Provider.Service;
import java.util.HashMap;

public class ServiceManager {
	private static ServiceManager INSTANCE = null;
	private final HashMap<String, Service> services;
	
	private ServiceManager() {
		services = new HashMap<>();
	}
	
	public static synchronized ServiceManager getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ServiceManager();
		}
		return INSTANCE;
	}
}
