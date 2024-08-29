package game.state;

/*
 * The InjuredState class represents the "Injured" state of a competitor in the game.
 */

public class InjuredState implements MobileAlertState{
	
	public void updateStatus(AlertStateContext context) {
		/*
		 * Updates the status of the competitor in the context to "Injured".
		 */
		context.getSupport().firePropertyChange("state", null, "Injured");
	}
	
}
