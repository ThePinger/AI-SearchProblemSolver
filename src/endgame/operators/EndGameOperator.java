package endgame.operators;

import java.util.TreeSet;

import endgame.Position;
import genericsearch.Operator;
import genericsearch.State;

public abstract class EndGameOperator implements Operator
{
	public boolean isValidMove(State newState)
	{
		// TODO Shady
		return false;
	}
	
	public int calculateDamage(Position ironManPosition, TreeSet<Position> aliveWarriors)
	{
		// TODO Shady
		return 0;
	}
}
