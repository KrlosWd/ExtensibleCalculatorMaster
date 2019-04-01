package myob.technicaltest.calculator.admin;

import java.io.File;
import java.util.HashMap;

import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.utils.CalculatorServiceLoader;

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
	
	public void setService(String key, String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NotACalculatorServiceException {
		services.put(key, CalculatorServiceLoader.getServiceInstance(className));
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
	
	public void loadJarfile(String filepath) throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		File file = new File(filepath);
		CalculatorServiceLoader.loadJarfile(file);
	}
}
