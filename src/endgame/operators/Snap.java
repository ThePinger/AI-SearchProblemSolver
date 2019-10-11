package endgame.operators;

import java.util.TreeSet;

import endgame.EndGame;
import endgame.EndGameState;
import endgame.Position;
import genericsearch.State;

public class Snap extends EndGameOperator 
{
	/**
	 * This method applies the snap operator by checking first if Ironman has all six stones and is occupying the same cell as Thanos.
	 * If this is the case it returns a new state specifying that Thanos is now dead. Otherwise it returns null.
	 */
	public EndGameState apply(State oldState)
	{
		Position ironManPosition = ((EndGameState) oldState).getIronManPosition();
		TreeSet<Position> uncollectedStones = ((EndGameState) oldState).getUncollectedStones();
		
		if(uncollectedStones.isEmpty() && ironManPosition.equals(EndGame.getThanosLocation()))
		{
			TreeSet<Position> aliveWarriors = ((EndGameState) oldState).getAliveWarriors();
			int damage = ((EndGameState) oldState).getDamage();
			return new EndGameState(ironManPosition, damage, uncollectedStones, aliveWarriors, false);
		}
		else
			return null;
	}
}
