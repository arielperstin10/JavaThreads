/**
 * Ariel Perstin
 * 324265164
 */


package game.entities;
import utilities.Point;

public interface IMobileEntity {
	public void move(double friction);
	public Point getLocation();
}
