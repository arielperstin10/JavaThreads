package game.arena;
import game.enums.*;
import game.state.AlertStateContext;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import game.entities.IMobileEntity;


public class WinterArena implements IArena, PropertyChangeListener{
	
	private double length;
	private SnowSurface surface;
	private WeatherCondition condition;
	
	public WinterArena() {
		setLength(850);
		setSurface(SnowSurface.ICE);
		setWeatherCondition(WeatherCondition.SUNNY);
	}
	
	public WinterArena(double length, SnowSurface surface, WeatherCondition condition) {
		setLength(length);
		setSurface(surface);
		setWeatherCondition(condition);
	}
	
	public double getFriction() {
		/**
		 * implement getFriction of IArena interface 
		 */
		return surface.getFriction();
	}
	
	public boolean isFinished(IMobileEntity me) {
		/**
		 * implement isFinished of IArena interface to check if competitor finished the race. 
		 */
		return me.getLocation().getX() >= this.length;
	}
	
	public void setLength(double length) {
		/**
		 * set length of the arena. 
		 */
		if (length < 0)
			throw new IllegalArgumentException("Arena length must be positive.");
		this.length = length;
	}
	
	public void setSurface(SnowSurface surface) {
		/**
		 * set surface of the arena. 
		 */
		if (surface == null)
			throw new IllegalArgumentException("Surface cannot be null");
		this.surface = surface;
	}
	
	public void setWeatherCondition(WeatherCondition condition) {
		/**
		 * set weather condition of the arena. 
		 */
		if (condition == null)
			throw new IllegalArgumentException("Condition cannot be null");
		this.condition = condition;
	}
	
	public double getLength() { return this.length; }
	public SnowSurface getSurface() { return this.surface; }
	public WeatherCondition getWeatherCondition() { return this.condition; }
	
	public String toString() {
		/**
		 * toString method overrides object's toString 
		 */
		return "Winter Arena: {Length: " + this.getLength() + ", Surface: " + this.getSurface() + ", Weather Condition: " + 
				this.getWeatherCondition() + "}";
	}
	
	public boolean equals(Object other) {
		/**
		 * equals method overrides object's equals. 
		 */
		if (other == null || !(other instanceof WinterArena))
			return false;
		if (((WinterArena)other).getLength() == this.getLength() && ((WinterArena)other).getSurface() == this.getSurface() 
				&& ((WinterArena)other).getFriction() == this.getFriction())
			return true;
		return false;
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
    	/*
    	 * Adding property change to get a notification on competitor status. 
    	 */
		if ("state".equals(evt.getPropertyName())) {
			String newState = (String) evt.getNewValue();
			int competitorID = ((AlertStateContext) evt.getSource()).getID();
			
			switch (newState) {
			case "Active":
				System.out.println("Competitor " + competitorID + " is now active.");
				break;
			case "Injured":
				System.out.println("Competitor " + competitorID + " is now injured.");
				break;
			case "Disabled":
				System.out.println("Competitor " + competitorID + " is now disabled.");
				break;
			case "Completed":
				System.out.println("Competitor " + competitorID + " has completed the race.");
				break;
			}
			
			
			
		}
        
    }
	
	
}
