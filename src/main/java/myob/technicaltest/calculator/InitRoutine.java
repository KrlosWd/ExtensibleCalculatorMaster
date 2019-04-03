package myob.technicaltest.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.services.AdditionService;
import myob.technicaltest.calculator.services.MultiplicationService;

@Configuration
public class InitRoutine {
	@Autowired
	ServiceManager serviceManager;
	
	@Bean
	CommandLineRunner initCalculatorServices() {
		serviceManager.setService("addition", new AdditionService());
		serviceManager.setService("multiplication", new MultiplicationService());
		return null;
	}
}
