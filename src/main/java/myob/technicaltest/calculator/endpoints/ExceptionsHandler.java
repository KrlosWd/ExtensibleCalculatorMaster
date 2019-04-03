package myob.technicaltest.calculator.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import myob.technicaltest.calculator.entities.SimpleResponse;
import myob.technicaltest.calculator.exceptions.CalculatorServiceNotFoundException;
import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotACalculatorServiceException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.exceptions.InvalidInputException;
import myob.technicaltest.calculator.lib.exceptions.MissingParametersException;
import myob.technicaltest.calculator.lib.exceptions.OperationException;
import myob.technicaltest.calculator.utils.JarfileClassLoader;


@ControllerAdvice
public class ExceptionsHandler {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionsHandler.class);
	
	@ResponseBody
	@ExceptionHandler(CalculatorServiceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public SimpleResponse calculatorServiceNotFoundExceptionHandler(CalculatorServiceNotFoundException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(MissingParametersException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse missingParametersExceptionHandler(MissingParametersException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(OperationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse operationExceptionHandler(OperationException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(InvalidInputException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse invalidInputExceptionHandler(InvalidInputException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	
	@ResponseBody
	@ExceptionHandler(JarNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public SimpleResponse jarNotFoundExceptionHandler(JarNotFoundException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(NotAJarFileException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse notAJarFileExceptionHandler(NotAJarFileException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	
	@ResponseBody
	@ExceptionHandler(UnableToReadJarException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public SimpleResponse unableToReadJarExceptionHandler(UnableToReadJarException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	
	@ResponseBody
	@ExceptionHandler(JarAlreadyLoadedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse jarAlreadyLoadedExceptionHandler(JarAlreadyLoadedException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(ClassAlreadyLoadedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse classAlreadyLoadedExceptionHandler(ClassAlreadyLoadedException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	
	@ResponseBody
	@ExceptionHandler(ClassNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public SimpleResponse classNotFoundExceptionHandler(ClassNotFoundException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(InstantiationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse instantiationExceptionHandler(InstantiationException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(NotACalculatorServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public SimpleResponse notACalculatorServiceExceptionHandler(NotACalculatorServiceException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(IllegalAccessException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public SimpleResponse illegalAccessExceptionHandler(IllegalAccessException ex) {
		log.trace("Handling exception: " + ex.getMessage());
		return new SimpleResponse(ex.getMessage());
	}
}
