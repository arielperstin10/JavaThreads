/**
 * Ariel Perstin
 * 324265164
 */

package game.competition;

import game.arena.WinterArena;
import game.entities.sportsman.*;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

public class SkiCompetition extends WinterCompetition {

	public SkiCompetition (WinterArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender) {
		super(arena, maxCompetitors, discipline, league, gender);
	}
	
	public boolean isValidCompetitor(ICompetitor competitor) {
		/**
		 * implement isValidCompetitor from WinterCompetitions class and checking if he is Skier. 
		 */
		return super.isValidCompetitor(competitor) && ((Skier)competitor) != null;
	}
	
	public String toString() {
		/**
		 * toString method overrides object's toString. 
		 */
		return "Ski Competition: {Arena: (" + this.getArena() + "), Maximum Competitors: " + this.getMaxCompetitors() + ", Discipline: "
				+ this.getDiscipline() + ", League: " + this.getLeague() + ", Gender: " + this.getGender() + "}";
	}
	
	public boolean equals(Object other) {
		/**
		 * equals method overrides object's equals. 
		 */
		if(other == null || !(other instanceof SkiCompetition))
			return false;
		if (this.getArena().equals(((SkiCompetition)other).getArena()) && this.getMaxCompetitors() == ((SkiCompetition)other).getMaxCompetitors()
				&& this.getDiscipline() == ((SkiCompetition)other).getDiscipline() && this.getLeague() == ((SkiCompetition)other).getLeague()
				&& this.getGender() == ((SkiCompetition)other).getGender())
			return true;
		return false;
	}
}
