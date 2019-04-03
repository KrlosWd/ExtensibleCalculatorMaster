package myob.technicaltest.calculator.admin;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.utils.CalculatorServiceLoader;

@Component
@Scope("singleton")
public class ServiceManager {
	private final HashMap<String, CalculatorService> services;

	
	public ServiceManager() {
		services = new HashMap<>();
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
	
	public Set<String> keySet(){
		return services.keySet();
	}
	
	public boolean containsService(String key) {
		return services.containsKey(key);
	}
}
