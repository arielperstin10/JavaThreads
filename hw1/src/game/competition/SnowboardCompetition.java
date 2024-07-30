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

public class SnowboardCompetition extends WinterCompetition {
	
	public SnowboardCompetition (WinterArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender) {
		super(arena, maxCompetitors, discipline, league, gender);
	}
	
	public boolean isValidCompetitor(ICompetitor competitor) {
		/**
		 * implement isValidCompetitor from WinterCompetitions class and checking if he is Snowboarder. 
		 */
		return super.isValidCompetitor(competitor) && ((Snowboarder)competitor) != null;
	}
	
	public String toString() {
		/**
		 * toString method overrides object's toString. 
		 */
		return "Snowboard Competition: {Arena: " + this.getArena() + ", Maximum Competitors: " + this.getMaxCompetitors() + ", Discipline: "
				+ this.getDiscipline() + ", League: " + this.getLeague() + ", Gender: " + this.getGender() + "}";
	}
	
	public boolean equals(Object other) {
		/**
		 * equals method overrides object's equals. 
		 */
		if(other == null || !(other instanceof SnowboardCompetition))
			return false;
		if (this.getArena().equals(((SnowboardCompetition)other).getArena()) && this.getMaxCompetitors() == ((SnowboardCompetition)other).getMaxCompetitors()
				&& this.getDiscipline() == ((SnowboardCompetition)other).getDiscipline() && this.getLeague() == ((SnowboardCompetition)other).getLeague()
				&& this.getGender() == ((SnowboardCompetition)other).getGender())
			return true;
		return false;
	}
}
