package myob.technicaltest.calculator.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;

public class CalculatorServiceLoaderTest {

	@Test
	public void testLoadJarfileGetInstance() throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException, ClassNotFoundException, InstantiationException, IllegalAccessException, NotACalculatorServiceException {
		String fileName = "ExponentialServices-1.0.0.jar";
		File file = new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		CalculatorServiceLoader.loadJarfile(file);
		CalculatorService srv = CalculatorServiceLoader.getServiceInstance("myob.technicaltest.calculator.service.exponential.SquareRootService");
		String serviceExpectedDescription = "SquareRootService: Takes a list [r_1, r_2, ..., r_n] of radicands and returns a String representation \n"
				+ "of the list [s_1, s_2, ..., s_n] where s_i represents the square root of r_i\n"
				+ "			@param  radicand List of n radicand numbers\n"
				+ "			@return String representation of the list [s_1, s_2, ..., s_n]\n";
		assertEquals(serviceExpectedDescription, srv.getDescription());
	}

}
