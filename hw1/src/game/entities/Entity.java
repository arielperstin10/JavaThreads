/**
 * Ariel Perstin
 * 324265164
 */


package game.entities;
import utilities.Point;

public abstract class Entity {

	private Point location;
	
	public Entity() { this.location = new Point(0,0); }
	
	public Entity(Point location) { setLocation(location); }
	
	public Point getLocation() { return this.location; }
	
	public void setLocation(Point location) {
		/**
		 * set location for entity. 
		 */
		if (location == null)
			throw new IllegalArgumentException("Location cannot be null");
		this.location = new Point(location);
	}
	
}
