package game.factory;

import game.arena.IArena;
import game.enums.Discipline;
import game.enums.Gender;
import game.enums.League;

public interface SkiCompetitionPlan {
	public void setArena(IArena arena);
	public void setMaxCompetitors(int maxCompetitors);
	public void setDiscipline(Discipline discipline);
	public void setLeague(League league);
	public void setGender(Gender gender);
}
