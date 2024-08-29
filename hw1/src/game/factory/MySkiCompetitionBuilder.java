package game.factory;

import java.lang.reflect.InvocationTargetException;

import game.competition.SkiCompetition;
import game.entities.sportsman.Skier;
import game.enums.Discipline;
import game.enums.Gender;

/*
 * It provides specific methods to construct a SkiCompetition, including building the arena and competitors.
 * This builder uses a prototype skier to clone and create multiple competitors.
 */

public class MySkiCompetitionBuilder implements SkiCompetitionBuilder{

	private SkiCompetition skiCompetition;
	private Skier prototypeSkier;
	
	public MySkiCompetitionBuilder() {
		/*
		 * Constructs a new MySkiCompetitionBuilder instance with a default ski competition and a prototype skier.
		 */
		skiCompetition = new SkiCompetition();
		prototypeSkier = new Skier("Default Skier", 12, Gender.MALE,5, 20, Discipline.FREESTYLE);
	}
	
	public void buildArena() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, 
				IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		 * Builds the arena for the ski competition using an ArenaFactory (Factory Method).
		 */
		ArenaFactory AF = new ArenaFactory();
		skiCompetition.setArena(AF.createArena("winter"));
	}
	
	public void buildCompetitors(int N) throws CloneNotSupportedException {
		/*
		 * Builds the specified number of competitors for the ski competition by cloning the prototype skier.
		 */
		for(int i = 0; i<N; i++) {
			Skier skier = (Skier)prototypeSkier.clone();
			skier.setName("Skier " + (i+1));
			skiCompetition.addCompetitor(skier);
		}
	}
	
	public SkiCompetition getCompetition() {return skiCompetition;}
}
