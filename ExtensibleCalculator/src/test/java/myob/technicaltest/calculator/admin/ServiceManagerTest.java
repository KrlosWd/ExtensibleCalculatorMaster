package myob.technicaltest.calculator.admin;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
import myob.technicaltest.calculator.utils.JarfileClassLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceManagerTest {
	private static AdditionService addition = new AdditionService();
	private static MultiplicationService multiplication = new MultiplicationService();
	
	@Autowired
	ServiceManager serviceManager;
	@Autowired
	ServiceManager serviceManager2;
	
	
	@Before
	public void setUp() {
		serviceManager.setService("addition", addition);
		serviceManager.setService("multiplication", multiplication);
		//If we do not update the JarfileClassLoader instance before each test, since the JarfileClassLoader is a singleton
		//classes loaded in one test could affect other test (e.g., because they do not expect any loaded classes yet)
		JarfileClassLoader.getInstance().updateInstance(Thread.currentThread().getContextClassLoader());
	}
	
	@After
	public void tearDown() {
		serviceManager.emptyServices();
	}
	
	@Test
	public void testIsSingleton() {
		assertEquals(serviceManager, serviceManager2);
	}

	@Test
	public void testGetService() {
		assertEquals(addition, serviceManager.getService("addition"));
		assertEquals(multiplication, serviceManager.getService("multiplication"));
		assertEquals(null, serviceManager.getService("non-existent-key"));
	}
	
	@Test
	public void testSetService() {
		serviceManager.setService("addition2", addition);
		assertEquals(addition, serviceManager.getService("addition2"));
	}

	@Test
	public void testRemoveService() {
		CalculatorService srv = serviceManager.removeService("addition");
		assertEquals(null, serviceManager.getService("addition"));
		assertEquals(srv, addition);
	}
	
	@Test
	public void testEmptyServices() {
		serviceManager.emptyServices();
		assertEquals(null, serviceManager.getService("addition"));
		assertEquals(null, serviceManager.getService("multiplication"));
	}
	
	@Test
	public void testGetServicesCount() {
		assertEquals(2, serviceManager.getServicesCount());
		serviceManager.emptyServices();
		assertEquals(0, serviceManager.getServicesCount());	
	}
	
	@Test
	public void testLoadJarANDsetServiceByClassname() {
		File file = new File(ClassLoader.getSystemClassLoader().getResource("ExponentialServices-1.0.0.jar").getFile());
		AdditionService addition = new AdditionService();
		try {
			serviceManager.loadJarfile(file.getAbsolutePath());
			serviceManager.setService("squareRoot", "myob.technicaltest.calculator.service.exponential.SquareRootService");
			CalculatorService srv = serviceManager.getService("squareRoot");
			String serviceExpectedDescription = "SquareRootService: Takes a list [r_1, r_2, ..., r_n] of radicands and returns a "
					+ "String representation of the list [s_1, s_2, ..., s_n] where s_i represents the square root of r_i";
			assertEquals(serviceExpectedDescription, srv.getDescription().getDescription());
			
			serviceManager.setService("addition2", "myob.technicaltest.calculator.services.AdditionService");
			srv = serviceManager.getService("addition2");
			assertEquals(addition.getDescription().getDescription(), srv.getDescription().getDescription());
		} catch (JarNotFoundException | NotAJarFileException | UnableToReadJarException | JarAlreadyLoadedException
				| ClassAlreadyLoadedException | ClassNotFoundException | InstantiationException | IllegalAccessException | NotACalculatorServiceException e) {
			fail("Unexpected Error: " + e.getMessage());
		}
	}
}
