package myob.technicaltest.calculator.utils;

import java.io.File;

import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.utils.JarfileClassLoader;

public class CalculatorServiceLoader {
	public static void loadJarfile(File file) throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		JarfileClassLoader.getInstance().addFile(file);
	}
	
	public static CalculatorService getServiceInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NotACalculatorServiceException {
		Class<?> clazz = JarfileClassLoader.getInstance().loadClass(className);
		Object obj = clazz.newInstance();
		
		if(obj instanceof CalculatorService) {
			return (CalculatorService) obj;
		} else {
			throw new NotACalculatorServiceException(className);
		}
	} 
}
