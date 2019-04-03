package myob.technicaltest.calculator.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;
import myob.technicaltest.calculator.lib.entities.CalculatorService;

public class JarfileClassLoaderTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	@Before
	public void setUp() {
		//If we do not update the JarfileClassLoader instance before each test, since the JarfileClassLoader is a singleton
		//classes loaded in one test could affect other test (e.g., because they do not expect any loaded classes yet)
		JarfileClassLoader.getInstance().updateInstance(Thread.currentThread().getContextClassLoader());
	}
	
	
	@Test
	public void testGetInstance() {
		JarfileClassLoader loader1 = JarfileClassLoader.getInstance();
		JarfileClassLoader loader2 = JarfileClassLoader.getInstance();
		assertEquals(loader1, loader2);
	}

	@Test
	public void testLoadClassString() throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String fileName = "ExponentialServices-1.0.0.jar";
		File file = new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		JarfileClassLoader.getInstance().addFile(file);
		Class<?> clazz = JarfileClassLoader.getInstance().loadClass("myob.technicaltest.calculator.service.exponential.SquareRootService");
		CalculatorService srv = (CalculatorService) clazz.newInstance();
		String serviceExpectedDescription = "SquareRootService: Takes a list [r_1, r_2, ..., r_n] of radicands and returns a String representation \n"
				+ "of the list [s_1, s_2, ..., s_n] where s_i represents the square root of r_i\n"
				+ "			@param  radicand List of n radicand numbers\n"
				+ "			@return String representation of the list [s_1, s_2, ..., s_n]\n";
		assertEquals(serviceExpectedDescription, srv.getDescription());
	}
	
	@Test
	public void testLoadClassString2() throws ClassNotFoundException  {
		expected.expect(ClassNotFoundException.class);
		JarfileClassLoader.getInstance().loadClass("myob.technicaltest.calculator.service.exponential.SquareRootService");
		fail("Expected ClassNotFoundException");
	}
	

	@Test
	public void testAddFile() throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		String fileName = "ExponentialServices-1.0.0.jar";
		File file = new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		JarfileClassLoader.getInstance().addFile(file);
		for(String jarfilename: JarfileClassLoader.getInstance().getJarNameList()) {
			if(jarfilename.contains(fileName)) {
				return;
			}
		}
		fail("Couldn't find filename ["+fileName+"] in the list of loaded jar files");
	}
	
	@Test
	public void testAddFile2() throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		expected.expect(JarNotFoundException.class);
		File file = new File("Unexistent.jar");
		JarfileClassLoader.getInstance().addFile(file);
		fail("Expected JarNotFoundException");
	}
	
	@Test
	public void testAddFile3() throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		expected.expect(JarAlreadyLoadedException.class);
		String fileName = "ExponentialServices-1.0.0.jar";
		File file = new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		JarfileClassLoader.getInstance().addFile(file);
		JarfileClassLoader.getInstance().addFile(file);
		fail("Expected JarAlreadyLoadedException");
	}
	
	@Test
	public void testAddFile4() throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		expected.expect(NotAJarFileException.class);
		String fileName = "notAJarFile.txt";
		File file = new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile());
		JarfileClassLoader.getInstance().addFile(file);
		fail("Expected NotAJarFileException");
	}
	
	@Test
	public void testAddFile5() throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		expected.expect(ClassAlreadyLoadedException.class);
		File file = new File(ClassLoader.getSystemClassLoader().getResource("ExponentialServices-1.0.0.jar").getFile());
		File file2 = new File(ClassLoader.getSystemClassLoader().getResource("CopyExponentialServices.jar").getFile());
		JarfileClassLoader.getInstance().addFile(file);
		JarfileClassLoader.getInstance().addFile(file2);
		fail("Expected ClassAlreadyLoadedException");
	}
}
