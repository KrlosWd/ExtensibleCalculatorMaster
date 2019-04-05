package myob.technicaltest.calculator.endpoints;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.entities.Constants;
import myob.technicaltest.calculator.lib.entities.CalculatorService;
import myob.technicaltest.calculator.services.AdditionService;
import myob.technicaltest.calculator.services.MultiplicationService;


@RunWith(SpringRunner.class)
@WebMvcTest( RootController.class)
public class RootControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @MockBean
    ServiceManager serviceManager;
    
	@Test
	public void testHelloWorld() throws Exception {
		mvc.perform(get("/calculator"))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message", is(Constants.WELCOME_MESSAGE)));
	}

	@Test
	public void testGetCalculatorServiceList() throws Exception {
		HashMap<String, CalculatorService> services = new HashMap<>();
		services.put("multiplication", new MultiplicationService());
		services.put("addition", new AdditionService());
		when(serviceManager.keySet()).thenReturn(services.keySet());
		when(serviceManager.getService("multiplication")).thenReturn(services.get("multiplication"));
		when(serviceManager.getService("addition")).thenReturn(services.get("addition"));
		
		mvc.perform(get("/calculator/status"))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void testProvideServiceAddition() throws Exception {
		AdditionService addition = new AdditionService();
		when(serviceManager.containsService("addition")).thenReturn(true);
		when(serviceManager.getService("addition")).thenReturn(addition);
		mvc.perform(get("/calculator/service/addition")
				.param("operand", "10")
	            .param("operand", "50"))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.output", is("60")));
	}
	
	@Test
	public void testProvideServiceAddition_throwCalculatorServiceNotFoundException() throws Exception {
		when(serviceManager.containsService("addition")).thenReturn(false);
		mvc.perform(get("/calculator/service/addition")
				.param("operand", "10")
	            .param("operand", "50"))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("Calculator Service for (addition) not found")));
	}

	@Test
	public void testProvideServiceAddition_throwMissingParametersException() throws Exception {
		AdditionService addition = new AdditionService();
		when(serviceManager.containsService("addition")).thenReturn(true);
		when(serviceManager.getService("addition")).thenReturn(addition);
		mvc.perform(get("/calculator/service/addition"))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message", is("The parameter [operand] is missing")));
	}
	
	@Test
	public void testProvideServiceAddition_throwInvalidInputException() throws Exception {
		AdditionService addition = new AdditionService();
		when(serviceManager.containsService("addition")).thenReturn(true);
		when(serviceManager.getService("addition")).thenReturn(addition);
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
		when(serviceManager.containsService("addition")).thenReturn(true);
		when(serviceManager.getService("addition")).thenReturn(addition);
		mvc.perform(get("/calculator/service/addition/help"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.description", is(addition.getDescription().getDescription())));
	}

}
