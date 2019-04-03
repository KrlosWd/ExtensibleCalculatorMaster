package myob.technicaltest.calculator.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Test;

import myob.technicaltest.calculator.entities.Metadata;

public class PropertiesReaderTest {

	@Test
	public void testLoadProperties() throws IOException {
		HashMap<String, String> authormeta = new HashMap<>();
		authormeta.put("name", "Juan Carlos Fuentes Carranza");
		authormeta.put("email", "juan.fuentes.carranza@gmail.com");
		
		HashMap<String, String> mavenmeta = PropertiesReader.loadProperties("maven.properties", 
				new LinkedList<String>(Arrays.asList("name","version", "description")));
		System.out.println(new Metadata(authormeta, mavenmeta));
	}

}
