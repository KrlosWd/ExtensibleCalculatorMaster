package myob.technicaltest.calculator.admin;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.services.AdditionService;
import myob.technicaltest.calculator.services.MultiplicationService;

public class ServiceManagerTest {
	private static AdditionService addition = new AdditionService();
	private static MultiplicationService multiplication = new MultiplicationService();
	
	
	@Before
	public void setUp() {
		ServiceManager.getInstance().setService("addition", addition);
		ServiceManager.getInstance().setService("multiplication", multiplication);
	}
	
	@After
	public void tearDown() {
		ServiceManager.getInstance().emptyServices();
	}
	
	@Test
	public void testGetInstance() {
		System.out.println("testGetInstance");
		ServiceManager instance1 = ServiceManager.getInstance();
		ServiceManager instance2 = ServiceManager.getInstance();
		assertEquals(instance1, instance2);
	}

	@Test
	public void testGetService() {
		assertEquals(addition, ServiceManager.getInstance().getService("addition"));
		assertEquals(multiplication, ServiceManager.getInstance().getService("multiplication"));
		assertEquals(null, ServiceManager.getInstance().getService("non-existent-key"));
	}
	
	@Test
	public void testSetService() {
		ServiceManager.getInstance().setService("addition2", addition);
		assertEquals(addition, ServiceManager.getInstance().getService("addition2"));
	}

	@Test
	public void testRemoveService() {
		CalculatorService srv = ServiceManager.getInstance().removeService("addition");
		assertEquals(null, ServiceManager.getInstance().getService("addition"));
		assertEquals(srv, addition);
	}
	
	@Test
	public void testEmptyServices() {
		ServiceManager.getInstance().emptyServices();
		assertEquals(null, ServiceManager.getInstance().getService("addition"));
		assertEquals(null, ServiceManager.getInstance().getService("multiplication"));
	}
	
	@Test
	public void testGetServicesCount() {
		assertEquals(2, ServiceManager.getInstance().getServicesCount());
		ServiceManager.getInstance().emptyServices();
		assertEquals(0, ServiceManager.getInstance().getServicesCount());	
	}
	
	@Test
	public void testLoadJarANDsetServiceByClassname() {
		File file = new File(ClassLoader.getSystemClassLoader().getResource("ExponentialServices-1.0.0.jar").getFile());
		try {
			ServiceManager.getInstance().loadJarfile(file.getAbsolutePath());
			ServiceManager.getInstance().setService("squareRoot", "myob.technicaltest.calculator.service.exponential.SquareRootService");
			CalculatorService srv = ServiceManager.getInstance().getService("squareRoot");
			String serviceExpectedDescription = "SquareRootService: Takes a list [r_1, r_2, ..., r_n] of radicands and returns a String representation \n"
					+ "of the list [s_1, s_2, ..., s_n] where s_i represents the square root of r_i\n"
					+ "			@param  radicand List of n radicand numbers\n"
					+ "			@return String representation of the list [s_1, s_2, ..., s_n]\n";
			
			ServiceManager.getInstance().setService("addition2", "myob.technicaltest.calculator.services.AdditionService");
			srv = ServiceManager.getInstance().getService("addition2");
			serviceExpectedDescription = "Addition Service: Calculates the addition of 'n' numbers\n"
					+ "			@param  operand List of one or more numbers to add\n"
					+ "			@return The sum of all operand values provided\n";
			assertEquals(serviceExpectedDescription, srv.getDescription());
		} catch (JarNotFoundException | NotAJarFileException | UnableToReadJarException | JarAlreadyLoadedException
				| ClassAlreadyLoadedException | ClassNotFoundException | InstantiationException | IllegalAccessException | NotACalculatorServiceException e) {
			fail("Unexpected Error: " + e.getMessage());
		}
	}
}
