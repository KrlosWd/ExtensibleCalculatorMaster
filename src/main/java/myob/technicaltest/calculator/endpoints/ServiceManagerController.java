package myob.technicaltest.calculator.endpoints;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.entities.SimpleResponse;
import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;

@RestController
public class ServiceManagerController {
	
	@PostMapping("/calculator/manager/jar")
	public SimpleResponse loadJar(@RequestParam String filepath) throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		ServiceManager.getInstance().loadJarfile(filepath);
		return new SimpleResponse("Jarfile ["+filepath+"] loaded successfully!");
	}
	
	@PostMapping("/calculator/manager/service")
	public SimpleResponse loadService(@RequestParam String path, @RequestParam String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NotACalculatorServiceException {
		ServiceManager.getInstance().setService(path, className);
		return new SimpleResponse("CalculatorService ["+className+"] loaded in path ["+path+"] successfully!");
	}
	
	@DeleteMapping("/calculator/manager/service")
	public SimpleResponse removeService(@RequestParam String path) {
		ServiceManager.getInstance().removeService(path);
		return new SimpleResponse("CalculatorService in path ["+path+"] removed successfully!");
	}
	
	@DeleteMapping("/calculator/manager/allServices")
	public SimpleResponse removeAllServices() {
		int serviceCount = ServiceManager.getInstance().getServicesCount();
		ServiceManager.getInstance().emptyServices();;
		return new SimpleResponse("Removed ["+serviceCount+"] CalculatorServices successfully!");
	}
}
