package game.state;

/*
 * The DisabledState class represents the "Disabled" state of a competitor in the game.
 */

public class DisabledState implements MobileAlertState{

	public void updateStatus(AlertStateContext context) {
		/*
		 * Updates the status of the competitor in the context to "Disabled".
		 */
		context.getSupport().firePropertyChange("state", null, "Disabled");
	}
}
