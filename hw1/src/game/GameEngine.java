/**
 * Ariel Perstin
 * 324265164
 */

package game;
import game.competition.Competition;
import game.competition.ICompetitor;


public class GameEngine {
	private static GameEngine instance;
	
	protected GameEngine() {}
	
	public static GameEngine getInstance() {
		/**
		 * getInstance method of "Singleton Class". 
		 */
		if (instance == null)
			instance = new GameEngine();
		return instance;
	}
	
	public void startRace(Competition comp) {
		/**
		 * Starting the race and prints results. 
		 */
		int step;
		for(step = 0 ; comp.hasActiveCompetitors() ; step++){
			comp.playTurn();
		}
		System.out.println("race finished in " + step + " steps");
		printResults(comp);
		
	}
	
	public void printResults(Competition comp) {
		/**
		 * Iterating over the finished competitors array and printing them. 
		 */
		System.out.println("Race results:");
		int place = 1;
		for(ICompetitor skier : comp.getFinishedCompetitors()){
			System.out.println(place + ". " + skier);
			place++;
		}
	}
}
