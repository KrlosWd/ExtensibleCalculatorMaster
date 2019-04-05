package myob.technicaltest.calculator.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;

public class CalculatorServiceLoaderTest {

	@Before
	public void setUp() {
		//If we do not update the JarfileClassLoader instance before (or after) each test, since the JarfileClassLoader is a singleton
		//classes loaded in one test could affect other test (e.g., because they do not expect any loaded classes yet)
		JarfileClassLoader.getInstance().updateInstance(Thread.currentThread().getContextClassLoader());
	}
	
	@Test
	public void testLoadJarfileGetInstance() throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException, ClassNotFoundException, InstantiationException, IllegalAccessException, NotACalculatorServiceException {
		String fileName = "ExponentialServices-1.0.0.jar";
		File file = new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		CalculatorServiceLoader.getInstance().loadJarfile(file);
		CalculatorService srv = CalculatorServiceLoader.getInstance().getServiceInstance("myob.technicaltest.calculator.service.exponential.SquareRootService");
		String serviceExpectedDescription = "SquareRootService: Takes a list [r_1, r_2, ..., r_n] of radicands and returns a String representation of the list [s_1, s_2, ..., s_n] where s_i represents the square root of r_i";
		assertEquals(serviceExpectedDescription, srv.getDescription().getDescription());
	}

}
