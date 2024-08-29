package game.competition;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;


import game.arena.*;
import game.entities.sportsman.WinterSportsman;

public abstract class Competition implements PropertyChangeListener {
	
	private IArena arena;
	private int maxCompetitors;
	protected ArrayList<ICompetitor> activeCompetitors;
	protected ArrayList<ICompetitor> finishedCompetitors;
	
	public Competition() {
		arena = null;
		maxCompetitors = 20;
		activeCompetitors = new ArrayList<>();
		finishedCompetitors = new ArrayList<>();
	}
	
	public Competition(IArena arena, int maxCompetitors) {
		setArena(arena);
		setMaxCompetitors(maxCompetitors);
		this.activeCompetitors = new ArrayList<>();
		this.finishedCompetitors = new ArrayList<>();
	}
	
	public abstract boolean isValidCompetitor(ICompetitor competitor);
	
	public void addCompetitor(ICompetitor competitor) {
		/**
		 * adding competitor to competition by checking if his details match to Competition's requirements.
		 */
		if (activeCompetitors.size() == maxCompetitors)
			throw new IllegalStateException("The competiton is full");
		if (!isValidCompetitor(competitor))
			throw new IllegalArgumentException("The competitor is not valid to compete");
		competitor.initRace();
		activeCompetitors.add(competitor);
		((WinterSportsman) competitor).addPropertyChangeListener(this);
	}
	
	public void playTurn() {
		/**
		 * Playing one single turn for the competition 
		 */
		ArrayList<ICompetitor> tmp = new ArrayList<>(activeCompetitors);
        for(ICompetitor competitor: tmp){
            if(!arena.isFinished(competitor)){
                competitor.move(arena.getFriction());
                if(arena.isFinished(competitor)){
                    finishedCompetitors.add(competitor);
                    activeCompetitors.remove(competitor);
                }
            }
        }
	}
	
	public boolean hasActiveCompetitors() { return !activeCompetitors.isEmpty(); }
	
	public void setArena(IArena arena) {
		/**
		 * set competiton's arena
		 */
		if (arena == null)
			throw new IllegalArgumentException("Arena cannot be null");
		this.arena = arena;
	}
	
	public void setMaxCompetitors(int maxCompetitors) {
		/**
		 * set competitons's maximum competitors.
		 */
		if (maxCompetitors <= 0)
			throw new IllegalArgumentException("Maximum competitors must be a positive number");
		this.maxCompetitors = maxCompetitors;
	}
	
	public ArrayList<ICompetitor> getFinishedCompetitors() { return this.finishedCompetitors; }
	
	public IArena getArena() { return this.arena; }
	
	public int getMaxCompetitors() { return this.maxCompetitors; }
	
	public ArrayList<ICompetitor> getActiveCompetitors() { return this.activeCompetitors; }
	
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    	/*
    	 * Adding property change to get a notification when competitor has finished. 
    	 */
        System.out.println("+++++++++++++++++++++");
        System.out.println("COMPETITION got a notification: " + ((WinterSportsman)evt.getSource()).getName() + " new value " + evt.getNewValue());
        finishedCompetitors.add((WinterSportsman)evt.getSource());
        activeCompetitors.remove((WinterSportsman)evt.getSource());
    }

	
	
}
