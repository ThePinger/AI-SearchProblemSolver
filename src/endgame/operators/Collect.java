package endgame.operators;

import java.util.TreeSet;

import endgame.EndGameState;
import endgame.Position;
import genericsearch.State;

public class Collect extends EndGameOperator 
{
	/**
	 * This method applies the collect operator by checking first if Ironman is occupying the same cell with an infinity stone.
	 * If this is the case, it adds 3 damage to the total damage and removes the stone's position from the TreeSet of stones and
	 * returns the new state if the new total damage is less than 100. Otherwise it returns null.
	 */
	public EndGameState apply(State oldState)
	{
		// get all attributes from the old state
		Position ironManPosition = ((EndGameState) oldState).getIronManPosition();
		TreeSet<Position> uncollectedStones = ((EndGameState) oldState).getUncollectedStones();
		TreeSet<Position> aliveWarriors = ((EndGameState) oldState).getAliveWarriors();
		boolean isThanosAlive = ((EndGameState) oldState).isThanosAlive();
		
		int addedDamage = 0;
		for(Position stonePosition : uncollectedStones)
		{
			if(stonePosition.equals(ironManPosition))
			{
				// add 3 damage and remove the stone
				addedDamage = 3;
				uncollectedStones.remove(stonePosition);
			}
		}
		
		// check if Ironman occupies the same cell as an infinity stone. If so return a new state, else return null.
		if(addedDamage == 3)
		{
			int newDamage = addedDamage + this.calculateDamage(ironManPosition, aliveWarriors) + ((EndGameState) oldState).getDamage();
			
			// check that the damage is less than 100
			if(newDamage < 100)
				return new EndGameState(ironManPosition, newDamage, uncollectedStones, aliveWarriors, isThanosAlive);
			else
				return null;
		}
		return null;
	}
	
}
