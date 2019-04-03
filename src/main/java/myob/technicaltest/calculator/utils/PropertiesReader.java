package myob.technicaltest.calculator.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Utility class for reading properties files located in project resources
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
public class PropertiesReader {
	/**
	 * Method to read a properties file "propertiesFile" and return a map containing only keys from the list "keys" and 
	 * their respective values
	 *  
	 * @param propertiesFile	name of the properties file
	 * @param keys				keys to get from the file
	 * @return	returns a HashMap<String, String> with the <key, values> pairs obtained from the properties files
	 * @throws IOException	if an IO error occurs
	 */
	public static HashMap<String, String> loadProperties(String propertiesFile, List<String> keys) throws IOException{
		HashMap<String, String> props = new HashMap<>(); 
		java.io.InputStream is = PropertiesReader.class.getClassLoader().getResourceAsStream(propertiesFile);
		java.util.Properties p = new Properties();
		p.load(is);
		for(String key: keys) {
			props.put(key, p.getProperty(key));
		}
		return props;
	}
}
