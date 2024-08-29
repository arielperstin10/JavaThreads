package game.entities.sportsman;

import game.factory.WSDecorator;

public class SpeedySportsman extends WSDecorator{
	private IWinterSportsman originalSportsman;
	
	public SpeedySportsman(IWinterSportsman decoratedSportsman, double acceleration) {
		/*
		 * Constructs a new SpeedySportsman with the specified original sportsman and acceleration.
		 */
		super(decoratedSportsman);
		this.originalSportsman = decoratedSportsman;
		setAcceleration(acceleration);
	}
	
	public void setAcceleration(double acceleration) {
		/*
		 * Sets the acceleration for the decorated sportsman.
		 */
		decoratedSportsman.setAcceleration(decoratedSportsman.getAcceleration() + acceleration);
	}
	
	public IWinterSportsman getOriginalSportsman() {
		/*
		 * Returns the original sportsman before decoration.
		 */
		return originalSportsman;
	}
}
