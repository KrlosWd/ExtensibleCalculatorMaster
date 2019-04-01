package myob.technicaltest.calculator.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import myob.technicaltest.calculator.exceptions.ClassAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarAlreadyLoadedException;
import myob.technicaltest.calculator.exceptions.JarNotFoundException;
import myob.technicaltest.calculator.exceptions.NotAJarFileException;
import myob.technicaltest.calculator.exceptions.UnableToReadJarException;


/**
 * <h1>JarfileClassLoader </h1>
 * JarfileClassLoader is a custom classloader used to loadclass from .jar files at runtime
 * <p>
 * 
 * All operations of the class are done through the active instance of the class
 * JarfileClassLoader accessed via the public method getInstance():
 * <pre>
 * {@code
 *  JarfileClassLoader.getInstance();
 * }
 * </pre> We first load a .jar file:
 * <pre>
 * {@code
 * File jFile = new File("SomeJarFile.jar");
 * JarfileClassLoader.getInstance().addFile(jFile);
 * }
 * </pre> Once the .jar has been loaded, we load a class from it using class Full Qualified Name:
 * <pre>
 * {@code
 * Class<?> clazz = JarfileClassLoader.getInstance().loadClass("some.qualified.name.Clazzname");
 * Clazzname instance = (Clazzname) clazz.newInstance();
 * ...
 * }
 * </pre>
 *
 *
 * @author Juan Carlos Fuentes Carranza
 */

public class JarfileClassLoader extends ClassLoader{
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JarfileClassLoader.class);

	/**
	 * Loaded Classes
	 */
	private final ConcurrentMap<String, Class<?>> classes = new ConcurrentHashMap<>();
	/**
	 * map from jar to list of class names
	 */
	private final ConcurrentMap<String, List<String>> jar2classes = new ConcurrentHashMap<>();
	/**
	 * map from classname to jarfile containing it
	 */
	private final ConcurrentMap<String, String> class2jar = new ConcurrentHashMap<>();
	/**
	 * List of known classes that hasnt been loaded
	 */
	private final Map<String, byte[]> knownFileClasses = new ConcurrentHashMap<>();

	/* 
	 *  We define JarfileClassLoader to be a singleton, to prevent multiple instantiations
	 * */
	private static JarfileClassLoader INSTANCE = null;

	public static JarfileClassLoader getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JarfileClassLoader();
		}
		return INSTANCE;
	}

	JarfileClassLoader(ClassLoader parent) {
		super(parent);
	}

	JarfileClassLoader() {
		super();
	}
	
	/*
	 * This method is used to replace the current instance of the class using a parent ClassLoader parent.
	 * This method should be used before loading any Jar files or classes and only if it is really necessary
	 * to use a particular ClassLoader as parent 
	 * */
    public void updateInstance(ClassLoader parent) {
        INSTANCE = new JarfileClassLoader(parent);
    }

	/**
	 * Returns a class contained in a previously loaded jar file via the addFile method
	 *
	 * @param className Fully Qualified Name of the class to be loaded
	 * @return the solicited class
	 * @throws ClassNotFoundException if the class className was not found
	 */
	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		try {
			return findSystemClass(className);
		} catch (ClassNotFoundException classNotFoundException) {
			try {
				return super.loadClass(className);
			} catch (ClassNotFoundException classNotFoundException1) {
				try {
					return findClass(className);
				} catch (ClassNotFoundException classNotFoundException2) {
					if (!knownFileClasses.containsKey(className)) {
						throw classNotFoundException2;
					}
					byte[] buff = knownFileClasses.get(className);
					Class<?> newClass = super.defineClass(className, buff, 0, buff.length);
					classes.putIfAbsent(className, newClass);
					knownFileClasses.remove(className);
					return newClass;
				}
			}
		}
	}

	private String binaryNameToFileName(String binaryName) {
		return binaryName.replaceAll("\\.", "/") + ".class";
	}

	/**
	 * help method for the method loadClass(String className).
	 *
	 * @param clazz fullyQualifiedName of the class to load
	 * @return the requested class.
	 * @throws ClassNotFoundException if the class was not found
	 */
	@Override
	protected Class<?> findClass(String clazz) throws ClassNotFoundException {
		log.trace("Loading class (" + clazz + ") from cache...");
		if (!classes.containsKey(clazz)) {
			throw new ClassNotFoundException("Class (" + clazz + ") not found in cache");
		}
		return classes.get(clazz);
	}

	/**
	 * help method for the method addFile(String jarPath).
	 *
	 * @param jarFile	the jar file containing the class className
	 * @param className the name of the class to load
	 * @throws ClassNotFoundException	
	 * @throws IOException if an error occurred while loading the class
	 */
	private void loadJarClass(JarFile jarFile, String className) throws IOException {
		log.trace("Loading class (" + className + ")...");
		JarEntry clazzEntry = jarFile.getJarEntry(binaryNameToFileName(className));
		if (clazzEntry != null) {
			try (InputStream is = jarFile.getInputStream(clazzEntry)) {
				int size = (int) clazzEntry.getSize();
				try (DataInputStream dis = new DataInputStream(is)) {
					byte buff[] = new byte[size];
					dis.readFully(buff);
					dis.close();
					knownFileClasses.put(className, buff);
					log.trace("Known class (" + className + ")");
				}
			}
		}
	}

	/**
	 * Load the content of a jarfile and prepares its classes to be loaded via the method loadClass 
	 *
	 * @param  jFile File object that we are attempting to load
	 * @throws JarNotFoundException if the file jFile was not found
	 * @throws NotAJarFileException if the file jFile has an extension other than .jar
	 * @throws UnableToReadJarExcepetion if it is not possible to load jFile due to permissions
	 * @throws JarAlreadyLoadedException if the jar jFile had been loaded before
	 * @throws ClassAlreadyLoadedException if a class in the jar file had been loaded before (perhaps from a different jar)
	 */
	public void addFile(File jFile) throws JarNotFoundException, NotAJarFileException, UnableToReadJarException, JarAlreadyLoadedException, ClassAlreadyLoadedException {
		if (!jFile.exists()) {
			log.error("The file (" + jFile.getAbsolutePath() + ") does not exist!");
			throw new JarNotFoundException("El archivo jar (" + jFile.getAbsolutePath() + ") no existe!");
		}
		if (!jFile.getName().matches(".*\\.jar$")) {
			log.error("The file (" + jFile.getAbsolutePath() + ") is not a jar file!");
			throw new NotAJarFileException("The file (" + jFile.getAbsolutePath() + ") is not a jar file!");
		}
		if (!jFile.canRead()) {
			log.error("Unable to read file (" + jFile.getAbsolutePath() + ")");
			throw new UnableToReadJarException("Unable to read file (" + jFile.getAbsolutePath() + ")");
		}
		
		if (jar2classes.containsKey(jFile.getAbsolutePath())) {
			log.error("Jar already loaded (" + jFile.getAbsolutePath() + ")");
			throw new JarAlreadyLoadedException("Jar already loaded (" + jFile.getAbsolutePath() + ")");
		}
		synchronized (jFile.getName().intern()) {
			try (JarFile jarFile = new JarFile(jFile)) {
				Enumeration<JarEntry> e = jarFile.entries();
				LinkedList<String> classList = new LinkedList<>();
				while (e.hasMoreElements()) {
					JarEntry je = (JarEntry) e.nextElement();
					if (je.isDirectory() || !je.getName().endsWith(".class")) {
						continue;
					}
					// -6 because of .class
					String className = je.getName().substring(0, je.getName().length() - 6);
					if (className.toLowerCase().startsWith("java.")) {
						log.error("This client is trying to modify the behaviour of JAVA with this " + className);
						continue;
					}

					className = className.replace('/', '.');
					classList.add(className);

					if (class2jar.containsKey(className)) {
						log.error("Class (" + className + ") already associated to jar (" + class2jar.get(className) + ")");
						throw new ClassAlreadyLoadedException("Class (" + className + ") already associated to jar (" + class2jar.get(className) + ")");
					}
				}

				if (classList.size() > 0) {
					Iterator<String> iter = classList.iterator();
					String className;
					while (iter.hasNext()) {
						className = iter.next();
						loadJarClass(jarFile, className);
						class2jar.put(className, jFile.getAbsolutePath());
						log.trace("Class (" + className + ") loaded");
					}

					jar2classes.put(jFile.getAbsolutePath(), classList);
					log.trace("Finished loading classes in jar (" + jFile.getAbsolutePath() + ")");
				}
			} catch (IOException e) {
				throw new NotAJarFileException(e.getMessage());
			}
		}
	}

	/**
	 * Obtains the list of jarfile that have been loaded via the method addFile
	 *
	 * @return the List<String> of jarfile names (absolute path)
	 */
	protected List<String> getJarNameList() {
		List<String> jarNameList = new LinkedList<>();

		for (String jarName : jar2classes.keySet()) {
			jarNameList.add(jarName);
		}
		return jarNameList;
	}
}
