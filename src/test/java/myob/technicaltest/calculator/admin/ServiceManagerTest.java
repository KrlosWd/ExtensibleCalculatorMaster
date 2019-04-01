package myob.technicaltest.calculator.admin;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import myob.technicaltest.calculator.admin.ServiceManager;
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
	
}
