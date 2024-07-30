/**
 * Ariel Perstin
 * 324265164
 */


package game.arena;
import game.enums.*;
import game.entities.IMobileEntity;

public class WinterArena implements IArena{
	
	private double length;
	private SnowSurface surface;
	private WeatherCondition condition;
	
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
	
	
	
	
}
