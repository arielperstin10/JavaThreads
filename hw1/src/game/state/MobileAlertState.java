package game.state;

/*
 * The MobileAlertState interface represents the state of a competitor in the game.
 * It defines a method to update the status of the entity based on the current state.
 */
public interface MobileAlertState {
	void updateStatus(AlertStateContext context);
}
