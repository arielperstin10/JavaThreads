/**
 * Ariel Perstin
 * 324265164
 */


package game.entities.sportsman;
import game.entities.MobileEntity;
import game.enums.Gender;

public abstract class Sportsman extends MobileEntity {
	
	private String name;
	private double age;
	private Gender gender;
	
	Sportsman(double maxSpeed, double acceleration, String name, double age, Gender gender){
		super(maxSpeed, acceleration);
		setName(name);
		setAge(age);
		setGender(gender);
	}
	
	public void setName(String name) {
		/**
		 * set name for sportsman.
		 */
		if (name == null)
			throw new IllegalArgumentException("Name cannot be null");
		this.name = name;
	}
	
	public void setAge(double age) {
		/**
		 * set age for sportsman.
		 */
		if (age <= 0)
			throw new IllegalArgumentException("Age must be positive");
		this.age = age;
	}
	
	public void setGender(Gender gender) {
		/**
		 * set gender for sportsman. 
		 */
		if (gender == null)
			throw new IllegalArgumentException("Gender cannot be null");
		this.gender = gender;
	}
	
	public String getName() { return this.name; }
	public double getAge() { return this.age; }
	public Gender getGender() { return this.gender; }
	
	
	
	
	
	
	
	
}
