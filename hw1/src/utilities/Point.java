package utilities;

public class Point {
	
	public static final double MIN_X = 0;
	public static final double MAX_X = 1000000;
	public static final double MIN_Y = 0;
	public static final double MAX_Y = 800;
	
	private double x;
	private double y;
	
	public Point() { this(0.0, 0.0); }

	public Point(double x, double y) {
		setX(x);
		setY(y);
	}
	
	public Point(Point other) { this(other.x, other.y); }
	
	public void setX(double x) {
		/**
		 * set x value of point. 
		 */
		if (x < MIN_X || x > MAX_X)
			throw new IllegalArgumentException("X value is out of range. Must be between " + MIN_X + " and " + MAX_X + ".");
		this.x = x;
	}
	
	public void setY(double y) {
		/**
		 * set y value of point.
		 */
		if (y < MIN_Y || y > MAX_Y)
			throw new IllegalArgumentException("Y value is out of range. Must be between " + MIN_Y + " and " + MAX_Y + ".");
	}
	
	public double getX() { return this.x; }
	
	public double getY() { return this.y; }
	
	public String toString() {
		/**
		 * toString method overrides object's toString. 
		 */
		return "(" + this.x + "," + this.y + ")"; 
	}
	
	public boolean equals(Object other) {
		/**
		 * equals method overrides object's equals. 
		 */
		boolean result = false;
		if (other != null && other instanceof Point)
			result = (x==((Point)other).x && y==((Point)other).y);
		return result;
	}
	
	
}
