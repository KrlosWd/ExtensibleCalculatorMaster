package myob.technicaltest.calculator.endpoints;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import myob.technicaltest.calculator.services.AdditionService;
import myob.technicaltest.calculator.services.MultiplicationService;
import myob.technicaltest.calculator.utils.JarfileClassLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RootControllerIntegrationTest {

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
    	serviceManager.removeService("addition");
    	serviceManager.removeService("multiplication");
		JarfileClassLoader.getInstance().updateInstance(Thread.currentThread().getContextClassLoader());
	}
    
	@Test
	public void testHelloWorld() throws Exception {
		mvc.perform(get("/calculator"))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is("Hello World")));
	}	
	

	@Test
	public void testGetCalculatorServiceList() throws Exception {		
		mvc.perform(get("/calculator/status"))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void testProvideServiceAddition() throws Exception {
		mvc.perform(get("/calculator/service/addition")
				.param("operand", "10")
	            .param("operand", "50"))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.output", is("60")));
	}
	
	@Test
	public void testProvideServiceAddition_throwCalculatorServiceNotFoundException() throws Exception {
		mvc.perform(get("/calculator/service/invalid"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Calculator Service for (invalid) not found")));
	}

	@Test
	public void testProvideServiceAddition_throwMissingParametersException() throws Exception {
		mvc.perform(get("/calculator/service/addition"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("The parameter [operand] is missing")));
	}
	
	@Test
	public void testProvideServiceAddition_throwInvalidInputException() throws Exception {
		mvc.perform(get("/calculator/service/addition")
				.param("operand", "10")
	            .param("operand", "not an integer"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("The value [not an integer] is not a valid integer")));
	}

	
	@Test
	public void testProvideServiceHelp() throws Exception {
		AdditionService addition = new AdditionService();
		mvc.perform(get("/calculator/service/addition/help"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description", is(addition.getDescription().getDescription())));
	}

}
