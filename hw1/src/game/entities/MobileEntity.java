/**
 * Ariel Perstin
 * 324265164
 */


package game.entities;

public abstract class MobileEntity extends Entity implements IMobileEntity {

	private double maxSpeed;
	private double acceleration;
	private double speed;
	
	public MobileEntity(double maxSpeed, double acceleration) {
		super();
		setMaxSpeed(maxSpeed);
		setAcceleration(acceleration);
		this.speed = 0;
	}
	
	public void move(double friction) {
		/**
		 * implement move method from IMobileEntity interface. 
		 */
		if (this.speed < this.maxSpeed)
			setSpeed(this.speed + this.acceleration * friction);
		if (this.speed > this.maxSpeed)
			setSpeed(this.maxSpeed);
		this.getLocation().setX(this.getLocation().getX() + this.speed);
	}
	
	public double getMaxSpeed() { return this.maxSpeed; }
	public double getAcceleration() { return this.acceleration; }
	public double getSpeed() { return this.speed; }
	
	public void setMaxSpeed(double maxSpeed) { 
		/**
		 * set maximum speed for entity. 
		 */
		if (maxSpeed < 0)
			throw new IllegalArgumentException("Maximum speed must be positive");
		this.maxSpeed = maxSpeed; 
	}
	
	public void setAcceleration(double acceleration) {
		/**
		 * set acceleration for entity. 
		 */
		if (acceleration < 0)
			throw new IllegalArgumentException("Acceleration must be positive");
		this.acceleration = acceleration; 
	}
	
	public void setSpeed(double speed) {
		/**
		 * set current speed for entity. 
		 */
		if (speed < 0)
			throw new IllegalArgumentException("Speed must be positive");
		this.speed = speed;
	}
	
	
	
}
