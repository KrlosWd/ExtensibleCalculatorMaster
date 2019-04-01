package myob.technicaltest.calculator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import myob.technicaltest.calculator.admin.ServiceManager;
import myob.technicaltest.calculator.services.AdditionService;
import myob.technicaltest.calculator.services.MultiplicationService;

@Configuration
public class InitRoutine {

	@Bean
	CommandLineRunner initCalculatorServices() {
		ServiceManager.getInstance().setService("addition", new AdditionService());
		ServiceManager.getInstance().setService("multiplication", new MultiplicationService());
		return null;
	}
}
