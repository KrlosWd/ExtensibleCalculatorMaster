package myob.technicaltest.calculator.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class PropertiesReader {

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
