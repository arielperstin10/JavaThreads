package game.state;

/*
 * The ActiveState class represents the "Active" state of a competitor in the game.
 */

public class ActiveState implements MobileAlertState{
	
	public void updateStatus(AlertStateContext context) {
		/*
		 * Updates the status of the competitor in the context to "Active".
		 */
		context.getSupport().firePropertyChange("state", null, "Active");
	}
}
