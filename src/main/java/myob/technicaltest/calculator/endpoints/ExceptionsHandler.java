package myob.technicaltest.calculator.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import myob.technicaltest.calculator.exceptions.CalculatorServiceNotFoundException;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.MissingParametersException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;

@ControllerAdvice
public class ExceptionsHandler {
	
	@ResponseBody
	@ExceptionHandler(CalculatorServiceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String calculatorServiceNotFoundExceptionHandler(CalculatorServiceNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(MissingParametersException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String missingParametersExceptionHandler(MissingParametersException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(OperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String operationExceptionHandler(OperationException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String invalidInputExceptionHandler(InvalidInputException ex) {
		return ex.getMessage();
	}
}
