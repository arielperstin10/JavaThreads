package game.entities.sportsman;

import javax.swing.JLabel;

import game.competition.ICompetitor;
import game.enums.Discipline;
import game.enums.Gender;

public interface IWinterSportsman extends ICompetitor{
	String getName();
	double getAge();
	Gender getGender();
	Discipline getDiscipline();
	double getAcceleration();
	double getMaxSpeed();
	JLabel getIcon();
	void setIcon(JLabel icon);
	void setAcceleration(double acceleration);
	void setColor(String color);
	String getColor();
}
