package myob.technicaltest.calculator.endpoints;



import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.exceptions.CalculatorServiceNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;


@RunWith(SpringRunner.class)
@WebMvcTest( ServiceManagerController.class)
public class ServiceManagerControllerTest {
	@Autowired
    private MockMvc mvc;
    
    @MockBean
    ServiceManager serviceManager;

    
	@Test
	public void testLoadJar() throws Exception {
		String jarfile = "dummy.jar";
		doNothing().when(serviceManager).loadJarfile(jarfile);
		mvc.perform(post("/calculator/manager/jar")
				.param("filepath", jarfile))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Jarfile ["+jarfile+"] loaded successfully!")));
	}
	
	@Test
	public void testLoadService() throws Exception {
		String path = "dummy";
		String className = "this.is.a.dummy.Class";
		doNothing().when(serviceManager).setService(path, className);
		mvc.perform(put("/calculator/manager/service")
				.param("path", path)
				.param("className", className))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("CalculatorService ["+className+"] loaded in path ["+path+"] successfully!")));
	}
	
	@Test
	public void testLoadService_thowsClassNotFoundException() throws Exception {
		String path = "dummy";
		String className = "this.is.a.dummy.Class";
		doThrow(new ClassNotFoundException("Class ("+className+") not found")).when(serviceManager).setService(path, className);
		mvc.perform(put("/calculator/manager/service")
				.param("path", path)
				.param("className", className))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Class ("+className+") not found")));
	}

	@Test
	public void testLoadService_thowsInstantiationException() throws Exception {
		String path = "dummy";
		String className = "this.is.a.dummy.Class";
		doThrow(new InstantiationException("Class ("+className+") instantiation error")).when(serviceManager).setService(path, className);
		mvc.perform(put("/calculator/manager/service")
				.param("path", path)
				.param("className", className))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Class ("+className+") instantiation error")));
	}
	
	@Test
	public void testLoadService_thowsNotACalculatorServiceException() throws Exception {
		String path = "dummy";
		String className = "this.is.a.dummy.Class";
		doThrow(new NotACalculatorServiceException(className)).when(serviceManager).setService(path, className);
		mvc.perform(put("/calculator/manager/service")
				.param("path", path)
				.param("className", className))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Instance from class ("+className+") is not a CalculatorService")));
	}
	
	@Test
	public void testLoadService_thowsIllegalAccessException() throws Exception {
		String path = "dummy";
		String className = "this.is.a.dummy.Class";
		doThrow(new IllegalAccessException("Illegal access to ("+className+") class")).when(serviceManager).setService(path, className);
		mvc.perform(put("/calculator/manager/service")
				.param("path", path)
				.param("className", className))
				.andExpect(status().isForbidden())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Illegal access to ("+className+") class")));
	}
	
	@Test
	public void testRemoveService() throws Exception {
		String path = "dummy";
		when(serviceManager.containsService(path)).thenReturn(true);
		when(serviceManager.removeService(path)).thenReturn(null);
		mvc.perform(delete("/calculator/manager/service")
				.param("path", path))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("CalculatorService in path ["+path+"] removed successfully!")));
	}
	
	@Test
	public void testRemoveService2() throws Exception {
		String path = "dummy";
		CalculatorServiceNotFoundException ex = new CalculatorServiceNotFoundException(path);
		when(serviceManager.containsService(path)).thenReturn(false);
		mvc.perform(delete("/calculator/manager/service")
				.param("path", path))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is(ex.getMessage())));
	}
	
	@Test
	public void testRemoveAllServices() throws Exception {
		int serviceCount = 2;
		when(serviceManager.getServicesCount()).thenReturn(serviceCount);
		doNothing().when(serviceManager).emptyServices();
		mvc.perform(delete("/calculator/manager/allServices"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("Removed ["+serviceCount+"] CalculatorServices successfully!")));
	}

}
