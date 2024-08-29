package game.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.arena.IArena;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;

/*
 * The CompetitionBuilder class is responsible for dynamically loading and constructing arena instances.
 * It uses reflection to load classes and create instances of IArena.
 * This class follows the Singleton pattern to ensure only one instance is created.
 */
public class CompetitionBuilder {

	private static CompetitionBuilder instance = null;
	private ClassLoader classLoader;
	private Class<?> classObject;
	private Constructor<?> constructor;
	
	private CompetitionBuilder() {
		/*
		 * Private constructor to prevent instantiation.
		 * Initializes the class loader to the system class loader.
		 */
		classLoader = ClassLoader.getSystemClassLoader();
	}
	
	public static CompetitionBuilder getInstance() {
		/*
		 * Returns the singleton instance of CompetitionBuilder.
		 */
		if(instance == null)
			instance = new CompetitionBuilder();
		return instance;
	}
	
	public IArena buildArena(String arenaType, double length, SnowSurface surface, WeatherCondition condition) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		 * Builds an arena of the specified type with the given parameters.
		 */
		this.classObject = classLoader.loadClass(arenaType);
		this.constructor = classObject.getConstructor(double.class, SnowSurface.class, WeatherCondition.class);
		return (IArena) this.constructor.newInstance(length, surface, condition);
	}
	
	public IArena buildArena(String arenaType) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		 * Builds an arena of the specified type with no parameters.
		 */
		this.classObject = classLoader.loadClass(arenaType);
		this.constructor = classObject.getConstructor();
		return (IArena) this.constructor.newInstance();
	}
}
