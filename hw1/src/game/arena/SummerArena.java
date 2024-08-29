package game.arena;

import game.entities.IMobileEntity;

public class SummerArena implements IArena{
	/*
	 * Summer Arena defined empty since we don't have any use with this class in our competition.
	 */

	public double getFriction() {
		return 0;
	}
	
	public boolean isFinished(IMobileEntity me) {
		return false;
	}

	
}
