/**
 * Ariel Perstin
 * 324265164
 */


package game.entities.sportsman;
import game.enums.*;
import utilities.Point;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JLabel;

import game.arena.WinterArena;
import game.competition.*;


public abstract class WinterSportsman extends Sportsman implements ICompetitor, Runnable {

	private Discipline discipline;
	
	private WinterArena arena; 
    private PropertyChangeSupport support;
    private JLabel icon;
	
	public WinterSportsman(String name, double age, Gender gender, double acceleration, double maxSpeed, Discipline discipline) {
		super(maxSpeed, acceleration, name, age, gender);
		this.getLocation().setX(0);
		this.setAcceleration(this.getAcceleration() + League.calcAccelerationBonus(getAge()));
		setDiscipline(discipline);
		this.support = new PropertyChangeSupport(this);
	}
	
	public void setDiscipline(Discipline discipline) {
		/**
		 * set discipline method.
		 * raise exception for any wrong data.
		 */

		if (discipline == null)
			throw new IllegalArgumentException("Discipline cannot be null");
		this.discipline = discipline;
	}

	public Discipline getDiscipline() { return this.discipline; }
	
	public void setArena(WinterArena arena) {this.arena = arena;}
	
	public void setIcon(JLabel icon) {this.icon = icon;}
	
	public JLabel getIcon() {return this.icon;}
	
	public void move (double friction) {
		/*
		 * overriding move function form MobileEntitiy to add fireProperyChange
		 */
		Point oldLocation = this.getLocation();
		super.move(friction);
		if (this.getLocation().getX() >= arena.getLength())
			this.setLocation(new Point(arena.getLength(), this.getLocation().getY()));
		support.firePropertyChange("location", oldLocation, this.getLocation());
	}
	
	public void run() {
		/*
		 * run function for thread
		 */
		while (this.getLocation().getX() < arena.getLength()) {
			move(arena.getFriction());
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initRace() {
		/**
		 * Implementation of init race method from ICompetitor interface.
		 */
		this.getLocation().setX(0);
		this.getLocation().setY(0);
	}

	public void addPropertyChangeListener (PropertyChangeListener pcl) {support.addPropertyChangeListener(pcl);}
	
	public void removePropertyChangeListener (PropertyChangeListener pcl) {support.removePropertyChangeListener(pcl);} 
	
}
