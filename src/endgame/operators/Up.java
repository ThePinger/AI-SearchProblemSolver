package endgame.operators;

import java.util.TreeSet;

import endgame.EndGameState;
import endgame.Position;
import genericsearch.State;

public class Up extends EndGameOperator 
{

	/**
	 * This method applies the UP operator which moves IronMan up the grid
	 * by decrementing X position by 1
	 * @param oldState
	 * @return New State if a new state is valid otherwise returns null
	 */
	@Override
	public State apply(State oldState) 
	{
		EndGameState curState = (EndGameState) oldState;
		Position newIronManPosition = new Position(curState.getIronManPosition().getX() - 1, curState.getIronManPosition().getY());
		TreeSet<Position> aliveWarriors = curState.getAliveWarriors();
		int newDamage = curState.getDamage() + super.calculateDamage(newIronManPosition, aliveWarriors);
		
		EndGameState newState = new EndGameState(newIronManPosition, newDamage, curState.getUncollectedStones(), aliveWarriors, curState.isThanosAlive());
		
		if(super.isValidMove(newState) && newDamage < 100)
			return newState;
		return null;
	}
	
}
