package game.factory;

import java.lang.reflect.InvocationTargetException;

import game.competition.SkiCompetition;

public interface SkiCompetitionBuilder {
	public void buildArena() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public void buildCompetitors(int N) throws CloneNotSupportedException;
	public SkiCompetition getCompetition();
}
