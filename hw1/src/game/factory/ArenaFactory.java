package game.factory;

import java.lang.reflect.InvocationTargetException;

import game.arena.IArena;
import game.enums.SnowSurface;
import game.enums.WeatherCondition;
/*
 * The ArenaFactory class is responsible for creating instances of IArena.
 */
public class ArenaFactory {
	
	public IArena createArena(String arenaType, double length, SnowSurface surface, WeatherCondition condition) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		CompetitionBuilder builder = CompetitionBuilder.getInstance();
		/*
		 *  Creates an arena of the specified type with the given parameters.
		 */
		IArena arena = null;
		if (arenaType.equalsIgnoreCase("winter"))
			arena = builder.buildArena("game.arena.WinterArena", length, surface, condition);
		else if (arenaType.equalsIgnoreCase("summer"))
			throw new UnsupportedOperationException("Summer arena is not available");
		else
			throw new IllegalArgumentException("Unknown arena type: " + arenaType);
		return arena;
	}
	
	public IArena createArena(String arenaType) throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		CompetitionBuilder builder = CompetitionBuilder.getInstance();
		/*
		 * Creates an arena of the specified type with no parameters.
		 */
		IArena arena = null;
		if (arenaType.equalsIgnoreCase("winter"))
			arena = builder.buildArena("game.arena.WinterArena");
		else if (arenaType.equalsIgnoreCase("summer"))
			throw new UnsupportedOperationException("Summer arena is not available");
		else
			throw new IllegalArgumentException("Unknown arena type: " + arenaType);
		return arena;
	}
	
}
