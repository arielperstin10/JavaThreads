package game.entities.sportsman;


import game.factory.WSDecorator;

public class ColoredSportsman extends WSDecorator{
	private IWinterSportsman originalSportsman;
	
	public ColoredSportsman(IWinterSportsman decoratedSportsman, String color) {
		/*
		 * Constructs a new ColoredSportsman with the specified original sportsman and color.
		 */
		super(decoratedSportsman);
		this.originalSportsman = decoratedSportsman;
		setColor(color);
	}
	
	public void setColor(String color) {
		/*
		 * Sets the color for the decorated sportsman.
		 */
		decoratedSportsman.setColor(color);
	}
	
	public IWinterSportsman getOriginalSportsman() {
		/*
		 * Returns the original sportsman before decoration.
		 */
		return originalSportsman;
	}
}
