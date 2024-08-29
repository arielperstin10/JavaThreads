package game.factory;

import java.lang.reflect.InvocationTargetException;

import game.competition.SkiCompetition;

/*
 * The Engineer class is responsible for constructing a SkiCompetition using a given SkiCompetitionBuilder.
 * It provides methods to construct the competition and retrieve the constructed competition.
 */

public class Engineer {
	private SkiCompetitionBuilder builder;
	
	public Engineer(SkiCompetitionBuilder builder) {this.builder = builder;}
	
	public SkiCompetition getCompetition() {return builder.getCompetition();}
	
	public void constructSkiCompetition(int N) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CloneNotSupportedException {
		/*
		 * Constructs the SkiCompetition by building the arena and competitors.
		 */
		builder.buildArena();
		builder.buildCompetitors(N);
	}
}
