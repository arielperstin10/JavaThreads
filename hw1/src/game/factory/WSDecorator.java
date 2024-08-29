package game.factory;

import javax.swing.JLabel;

import game.entities.sportsman.IWinterSportsman;
import game.enums.Discipline;
import game.enums.Gender;
import utilities.Point;

/*
 * The WSDecorator class is a decorator class that implements the IWinterSportsman interface.
 * It is designed to add additional functionality or modify the behavior of an existing IWinterSportsman object.
 * This class delegates all method calls to the decorated IWinterSportsman instance.
 */

public class WSDecorator implements IWinterSportsman{
	protected IWinterSportsman decoratedSportsman;
	
	public WSDecorator(IWinterSportsman decoratedSportsman) {
		this.decoratedSportsman = decoratedSportsman;
	}
	
	@Override
	public void move(double friction) {
		decoratedSportsman.move(friction);
	}

	@Override
	public Point getLocation() {
		return decoratedSportsman.getLocation();
	}

	@Override
	public String getName() {
		return decoratedSportsman.getName();
	}

	@Override
	public double getAge() {
		return decoratedSportsman.getAge();
	}

	@Override
	public Gender getGender() {
		return decoratedSportsman.getGender();
	}

	@Override
	public Discipline getDiscipline() {
		return decoratedSportsman.getDiscipline();
	}

	@Override
	public double getAcceleration() {
		return decoratedSportsman.getAcceleration();
	}

	@Override
	public double getMaxSpeed() {
		return decoratedSportsman.getMaxSpeed();
	}

	@Override
	public JLabel getIcon() {
		return decoratedSportsman.getIcon();
	}

	@Override
	public void setIcon(JLabel icon) {
		decoratedSportsman.setIcon(icon);
	}

	@Override
	public void setAcceleration(double acceleration) {
		decoratedSportsman.setAcceleration(acceleration);
	}

	public void setColor(String color) {
		decoratedSportsman.setColor(color);
	}
	
	public String getColor() {
		return decoratedSportsman.getColor();
	}

	@Override
	public void initRace() {
		decoratedSportsman.initRace();
	}
}
