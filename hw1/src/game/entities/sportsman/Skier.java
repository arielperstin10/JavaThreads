/**
 * Ariel Perstin
 * 324265164
 */


package game.entities.sportsman;

import game.enums.Discipline;
import game.enums.Gender;


public class Skier extends WinterSportsman{
	
	public Skier(String name, double age, Gender gender, double acceleration, double maxSpeed, Discipline discipline) {
		super(name, age, gender, acceleration, maxSpeed, discipline);
	}
	
	public String toString() {
		/**
		 * toString method, overrides object's toString.
		 */
		return "Skier: " + "{Location: " + this.getLocation() + ", Name: " + this.getName() + ", Age: " + this.getAge() + ", Gender: " + 
				this.getGender() + ", Discipline: " + this.getDiscipline() + ", Speed: " + this.getSpeed() + ", Acceleration: " + this.getAcceleration() + 
				", Maximum Speed: " + this.getMaxSpeed() + "}";
	}
	
	public boolean equals(Object other) {
		/**
		 * equals method overrides object's equals.
		 */
		if (other == null || !(other instanceof Skier))
			return false;
		if (((Skier)other).getLocation().equals(getLocation()) && this.getSpeed() ==  ((Skier)other).getSpeed() && this.getAcceleration() == 
				((Skier)other).getAcceleration() && this.getMaxSpeed() == ((Skier)other).getMaxSpeed() && this.getName().equals(((Skier)other).getName())
				 && this.getAge() == ((Skier)other).getAge() && this.getGender() == ((Skier)other).getGender() && this.getDiscipline() == ((Skier)other).getDiscipline())
			return true;
		return false;
	}
}
