/**
 * Ariel Perstin
 * 324265164
 */


package game.entities.sportsman;

import game.enums.Discipline;
import game.enums.Gender;


public class Snowboarder extends WinterSportsman{
	
	public Snowboarder(String name, double age, Gender gender, double acceleration, double maxSpeed, Discipline discipline) {
		super(name, age, gender, acceleration, maxSpeed, discipline);
	}
	
	public String toString() {
		/**
		 * toString method, overrides object's toString.
		 */
		return "Snowboarder: " + "{Location: " + this.getLocation() + ", Name: " + this.getName() + ", Age: " + this.getAge() + ", Gender: " + 
				this.getGender() + ", Discipline: " + this.getDiscipline() + ", Speed: " + this.getSpeed() + ", Acceleration: " + this.getAcceleration() + 
				", Maximum Speed: " + this.getMaxSpeed() + "}";
	}
	
	public boolean equals(Object other) {
		/**
		 * equals method overrides object's equals.
		 */
		if (other == null || !(other instanceof Snowboarder))
			return false;
		if (((Snowboarder)other).getLocation().equals(getLocation()) && this.getSpeed() ==  ((Snowboarder)other).getSpeed() && this.getAcceleration() == 
				((Snowboarder)other).getAcceleration() && this.getMaxSpeed() == ((Snowboarder)other).getMaxSpeed() && this.getName().equals(((Snowboarder)other).getName())
				 && this.getAge() == ((Snowboarder)other).getAge() && this.getGender() == ((Snowboarder)other).getGender() && this.getDiscipline() == ((Snowboarder)other).getDiscipline())
			return true;
		return false;
	}
	
}
