package game.entities.sportsman;
import game.enums.*;
import game.state.ActiveState;
import game.state.AlertStateContext;
import game.state.CompletedState;
import game.state.DisabledState;
import game.state.InjuredState;
import utilities.Point;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


import javax.swing.JLabel;

import game.arena.WinterArena;
import game.competition.*;


public abstract class WinterSportsman extends Sportsman implements ICompetitor, Runnable, Cloneable, IWinterSportsman {
	
	private static int serial = 1;
	private int serialNumber;
	private Discipline discipline;
	private WinterArena arena; 
    protected PropertyChangeSupport support;
    private JLabel icon;
    private String color;
	private AlertStateContext state;
	
	private long injuredTime;
	private double exitedLoc;
    
	public WinterSportsman(String name, double age, Gender gender, double acceleration, double maxSpeed, Discipline discipline) {
		super(maxSpeed, acceleration, name, age, gender);
		setSerialNumber(getSerial());
		this.getLocation().setX(0);
		this.setAcceleration(this.getAcceleration() + League.calcAccelerationBonus(getAge()));
		setDiscipline(discipline);
		this.support = new PropertyChangeSupport(this);
		this.state = new AlertStateContext(serialNumber);
		this.injuredTime = 0;
		this.exitedLoc = 0;
	}
	
	public WinterSportsman clone() throws CloneNotSupportedException {
		/*
		 * Define cloned competitor constructor
		 */
		WinterSportsman cloned = (WinterSportsman) super.clone();
		cloned.setLocation(new Point(this.getLocation().getX(), this.getLocation().getY()));
		cloned.setSerialNumber(getSerial());
		cloned.support = new PropertyChangeSupport(cloned);
		cloned.state = new AlertStateContext(cloned.getSerialNumber());
		return cloned;
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
	
	public void setSerialNumber(int serialNumber) {this.serialNumber = serialNumber;}
	
	public JLabel getIcon() {return this.icon;}
	
	public int getSerialNumber() {return this.serialNumber;}
	
	public static synchronized int getSerial() {return serial++;}
	
	public static void increaseSerial() {serial++;}
	
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
		long startTime = System.currentTimeMillis();
		while (this.getLocation().getX() < arena.getLength()) {
			destiny();
			if (state.getCurrentStatus() instanceof InjuredState)
				this.injuredTime = System.currentTimeMillis() - startTime;
			move(arena.getFriction());
			if (this.getLocation().getX() >= arena.getLength() && state.getCurrentStatus() instanceof ActiveState)
				this.state.setState(new CompletedState());
			state.updateStatus();
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
	
	public AlertStateContext getState() {return this.state;}
	
	public void setColor(String color) {this.color = color;}
	
	public String getColor() {return this.color;} 
	
	public void addPropertyChangeListener (PropertyChangeListener pcl) {support.addPropertyChangeListener(pcl);}
	
	public void removePropertyChangeListener (PropertyChangeListener pcl) {support.removePropertyChangeListener(pcl);} 
	
	private void destiny() {
		/*
		 * Function to randomly choose the different states of the competitors which called in the run() function
		 */
		double chance = Math.random();
		double injuryProb = 0.02;
		double disabledProb = 0.01;
		double backToRaceAfterInjuryProb = 0.15;
		double raceLength = arena.getLength();
		double progressRatio = this.getLocation().getX() / raceLength;
		
		if (progressRatio < 0.5) { 
	        injuryProb *= 0.3; 
	        disabledProb *= 0.3;  
	    } else if (progressRatio > 0.8) { 
	        injuryProb *= 1.2; 
	        disabledProb *= 1.2; 
	    }
		
		if (state.getCurrentStatus() instanceof ActiveState) {
			if (chance <= disabledProb) {
				state.setState(new DisabledState());
				this.exitedLoc = this.getLocation().getX();
			}
			else if (chance <= injuryProb)
				state.setState(new InjuredState());
		}
		else if (state.getCurrentStatus() instanceof InjuredState) {
			if (chance <= backToRaceAfterInjuryProb)
				state.setState(new ActiveState());
			else {
				state.setState(new DisabledState());
				this.exitedLoc = this.getLocation().getX();
			}
		}
	}
	
	public void addState() {
		/*
		 * Function to add for competitors that created in the GUI the property listener based on the arena they competing
		 */
		this.state.addPropertyChangeListener(this.arena);
	}
	
	public double getExitedLoc() {return this.exitedLoc;}
	public long getInjuredTime() {return this.injuredTime;}
}
