package game.arena;
import game.entities.IMobileEntity;

public interface IArena {
	public double getFriction();
	public boolean isFinished(IMobileEntity me);
}
