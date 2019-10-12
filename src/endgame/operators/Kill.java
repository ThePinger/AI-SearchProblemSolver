package endgame.operators;

import java.util.ArrayList;
import java.util.TreeSet;

import endgame.EndGameState;
import endgame.Position;
import genericsearch.State;

public class Kill extends EndGameOperator 
{
	/**
	 * This method applies the Kill operator which kills all warriors in adjacent cells
	 * where IronMan damage increases by 2 for each killed warrior
	 * @param oldState
	 * @return New State if a new state is valid otherwise returns null
	 */
	@Override
	public State apply(State oldState) 
	{
		EndGameState curState = (EndGameState) oldState;
		Position ironManPosition = curState.getIronManPosition();
		TreeSet<Position> aliveWarriors = curState.getAliveWarriors();
		
		ArrayList<Position> adjacentCells = super.getAdjacentCells(ironManPosition);
		int newDamage = curState.getDamage();
		for(Position p : adjacentCells)
		{
			if(aliveWarriors.contains(p))
			{
				newDamage += 2;
				aliveWarriors.remove(p);
			}
		}
		
		newDamage += super.calculateDamage(ironManPosition, aliveWarriors);
		
		EndGameState newState = new EndGameState(ironManPosition, newDamage, curState.getUncollectedStones(), aliveWarriors, curState.isThanosAlive());
		
		if(super.isValidMove(newState) && newDamage < 100)
			return newState;
		return null;
	}
}
