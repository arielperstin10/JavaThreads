package game.state;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
/*
 * The AlertStateContext class maintains the state of a competitor and provides methods to update its status.
 * It uses the State design pattern to handle the state transitions of the competitor.
 */
public class AlertStateContext {

	private MobileAlertState currentState;
	private int competitorID;
	private PropertyChangeSupport support;
	
	public AlertStateContext(int ID) {
		this.currentState = new ActiveState();
		this.competitorID = ID;
		this.support = new PropertyChangeSupport(this);
	}
	
	public void setState(MobileAlertState newState) {this.currentState = newState;}
	public void updateStatus() {currentState.updateStatus(this);}
	
	public int getID() {return this.competitorID;}
	public PropertyChangeSupport getSupport() {return this.support;}
	public MobileAlertState getCurrentStatus() {return this.currentState;}
	public void addPropertyChangeListener(PropertyChangeListener pcl) {support.addPropertyChangeListener(pcl);}
	public void removePropertyChangeListener(PropertyChangeListener pcl) {support.removePropertyChangeListener(pcl);}
	
}
