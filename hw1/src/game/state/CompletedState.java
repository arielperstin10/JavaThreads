package game.state;

/*
 * The CompletedState class represents the "Completed" state of a competitor in the game.
 */

public class CompletedState implements MobileAlertState{

	public void updateStatus(AlertStateContext context) {
		/*
		 * Updates the status of the competitor in the context to "Completed".
		 */
		context.getSupport().firePropertyChange("state", null, "Completed");
	}
}
