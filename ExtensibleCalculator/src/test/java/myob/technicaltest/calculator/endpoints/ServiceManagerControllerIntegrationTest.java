package myob.technicaltest.calculator.endpoints;



import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.exceptions.CalculatorServiceNotFoundException;
import myob.technicaltest.calculator.services.AdditionService;
import myob.technicaltest.calculator.services.MultiplicationService;
import myob.technicaltest.calculator.utils.JarfileClassLoader;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceManagerControllerIntegrationTest {
	@Autowired
    private MockMvc mvc;
    
	@Autowired
    ServiceManager serviceManager;

    @Before
    public void setUp () {
    	serviceManager.setService("addition", new AdditionService());
    	serviceManager.setService("multiplication", new MultiplicationService());
    }
    
    @After
    public void tearDown() {
    	serviceManager.emptyServices();
		JarfileClassLoader.getInstance().updateInstance(Thread.currentThread().getContextClassLoader());
	}
	
    
	@Test
	public void testLoadJar() throws Exception {
		String fileName = "ExponentialServices-1.0.0.jar";
		File file = new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		mvc.perform(post("/calculator/manager/jar")
				.param("filepath", file.getAbsolutePath()))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Jarfile ["+file.getAbsolutePath()+"] loaded successfully!")));
	}
	
	@Test
	public void testLoadService() throws Exception {
		String path = "addition2";
		String className = AdditionService.class.getCanonicalName();
		mvc.perform(put("/calculator/manager/service")
				.param("path", path)
				.param("className", className))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("CalculatorService ["+className+"] loaded in path ["+path+"] successfully!")));
	}
	
	@Test
	public void testLoadService_thowsClassNotFoundException() throws Exception {
		String className = "this.is.a.dummy.Class";
		mvc.perform(put("/calculator/manager/service")
				.param("path", "dummy")
				.param("className", className))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", startsWith("Class ("+className+") not found")));
	}
	
	@Test
	public void testLoadService_thowsNotACalculatorServiceException() throws Exception {
		String path = "string";
		String className = String.class.getCanonicalName();
		mvc.perform(put("/calculator/manager/service")
				.param("path", path)
				.param("className", className))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Instance from class ("+className+") is not a CalculatorService")));
	}
	
	
	@Test
	public void testRemoveService() throws Exception {
		String path = "addition";
		mvc.perform(delete("/calculator/manager/service")
				.param("path", path))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("CalculatorService in path ["+path+"] removed successfully!")));
	}
	
	@Test
	public void testRemoveService2() throws Exception {
		String path = "dummy";
		CalculatorServiceNotFoundException ex = new CalculatorServiceNotFoundException(path);
		mvc.perform(delete("/calculator/manager/service")
				.param("path", path))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is(ex.getMessage())));
	}
	
	@Test
	public void testRemoveAllServices() throws Exception {
		int serviceCount = serviceManager.getServicesCount();
		mvc.perform(delete("/calculator/manager/allServices"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("Removed ["+serviceCount+"] CalculatorServices successfully!")));
	}

}
