package endgame.operators;

import java.util.TreeSet;

import endgame.EndGameState;
import endgame.Position;
import genericsearch.State;

public class Right extends EndGameOperator 
{
	/**
	 * This method applies the Right operator by incrementing the Y value in the position of the old state.
	 * If the new state is a valid one and the new damage is less than 100, the method returns the new state.
	 * Otherwise it returns null.
	 */
	public EndGameState apply(State oldState) {
		// apply the operator by changing the needed variables in the old state. Keep the other ones unchanged but clone them.
		int newX = ((EndGameState) oldState).getIronManPosition().getY() + 1;
		Position newIronManPosition = new Position(newX, ((EndGameState) oldState).getIronManPosition().getY());
		TreeSet<Position> newUncollectedStones = ((EndGameState) oldState).getUncollectedStones();
		TreeSet<Position> newAliveWarriors = ((EndGameState) oldState).getAliveWarriors();
		boolean isThanosAlive = ((EndGameState) oldState).isThanosAlive();
		int newDamage = ((EndGameState) oldState).getDamage() + this.calculateDamage(newIronManPosition, newAliveWarriors);
		
		// create new state using new damage and position
		EndGameState newState = new EndGameState(newIronManPosition, newDamage, newUncollectedStones, newAliveWarriors, isThanosAlive);
		
		// if the new state is valid and the damage is less than 100, return the new state. Otherwise return null
		if(this.isValidMove(newState) && newDamage < 100)
			return newState;
		return null;
	}
}
