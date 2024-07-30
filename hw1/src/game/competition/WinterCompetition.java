/**
 * Ariel Perstin
 * 324265164
 */

package game.competition;
import game.enums.*;
import game.arena.*;
import game.entities.sportsman.*;

public abstract class WinterCompetition extends Competition{

	private Discipline discipline;
	private League league;
	private Gender gender;
	
	public WinterCompetition(WinterArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender) {
		super(arena, maxCompetitors);
		setDiscipline(discipline);
		setLeague(league);
		setGender(gender);
	}
	
	public void setDiscipline(Discipline discipline) {
		/**
		 * set discipline of winter competition.
		 */
		if (discipline == null)
			throw new IllegalArgumentException("Discipline cannot be null");
		this.discipline = discipline;
	}
	
	public void setLeague(League league) {
		/**
		 * set league of winter competition. 
		 */
		if (league == null)
			throw new IllegalArgumentException("League cannot be null");
		this.league = league;
	}
	
	public void setGender(Gender gender) {
		/**
		 * set gender of winter competition. 
		 */
		if (gender == null)
			throw new IllegalArgumentException("Gender cannot be null");
		this.gender = gender;
	}
	
	public Discipline getDiscipline() { return this.discipline; }
	public Gender getGender() { return this.gender; }
	public League getLeague() { return this.league; }
	
	public boolean isValidCompetitor(ICompetitor competitor) {
		/**
		 * implementing abstract method isValidCompetitor to check if competitor is valid for the competition. 
		 */
		boolean isSportsman = ((WinterSportsman)competitor) != null;
		boolean inLeague = this.league.isInLeague(((Sportsman)competitor).getAge());
		boolean isGender = ((Sportsman)competitor).getGender() == this.gender;
		boolean isDiscipline = ((WinterSportsman)competitor).getDiscipline() == this.discipline;
		return isSportsman && inLeague && isGender && isDiscipline;
	}
	
	
}
