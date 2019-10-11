package endgame.operators;

import java.util.ArrayList;
import java.util.TreeSet;

import endgame.EndGame;
import endgame.EndGameState;
import endgame.Position;
import genericsearch.Operator;

public abstract class EndGameOperator implements Operator
{
	/**
	 * This helper method checks to see if x,y are inside the grid
	 * @param1 x
	 * @param2 y
	 * @return true if position is inside the grid
	 */
	private boolean isWithinGrid(int x, int y)
	{
		return x < EndGame.getM() && y < EndGame.getN() && x > -1 && y > -1;
	}
	
	/**
	 * This helper method checks to see if the position given is a warrior cell
	 * @param1 ironManPosition
	 * @param2 aliveWarriors
	 * @return true if (IronMan position) == (warrior position)
	 */
	private boolean isWarriorCell(Position ironManPosition, TreeSet <Position> aliveWarriors)
	{
		return aliveWarriors.contains(ironManPosition);
	}
	
	/**
	 * This helper method checks to see if the position given is thanos cell
	 * @param ironManPosition
	 * @return true if (IronMan position) == (Thanos position)
	 */
	private boolean isThanosCell(Position ironManPosition)
	{
		if(ironManPosition.compareTo(EndGame.getThanosLocation()) == 0)
			return true;
		return false;
	}
	
	/**
	 * This method checks to see if a move done by any operator is valid or not
	 * @param newState The state that was returned from an operator.apply()
	 * @return true if the move is valid
	 */
	public boolean isValidMove(EndGameState newState)
	{	
		if (!this.isWithinGrid(newState.getIronManPosition().getX(), newState.getIronManPosition().getY())
				|| this.isWarriorCell(newState.getIronManPosition(), newState.getAliveWarriors())
				|| this.isThanosCell(newState.getIronManPosition()))
		{
			return false;
		}
		return true;
	}
	
	/**
	 * This helper method generates the adjacent cells for the position given
	 * @param ironManPosition
	 * @return ArrayList that contains the adjacent cells
	 */
	private ArrayList <Position> getAdjacentCells(Position ironManPosition)
	{
		int x = ironManPosition.getX();
		int y = ironManPosition.getY();
		ArrayList <Position> a = new ArrayList<Position>();
		if (this.isWithinGrid(x+1, y))
			a.add(new Position(x+1,y));
		if (this.isWithinGrid(x-1, y))
			a.add(new Position(x-1,y));
		if (this.isWithinGrid(x, y+1))
			a.add(new Position(x,y+1));
		if (this.isWithinGrid(x, y-1))
			a.add(new Position(x,y-1));
		return a;
		
	}
	
	/**
	 * This method will calculate the damage taken from the position given
	 * @param1 ironManPosition
	 * @param2 aliveWarriors
	 * @return Damage done in the current state
	 */
	public int calculateDamage(Position ironManPosition, TreeSet<Position> aliveWarriors)
	{
		int damage = 0;
		ArrayList <Position> adjacentCells = this.getAdjacentCells(ironManPosition);
		for (int i = 0; i < adjacentCells.size(); i++)
		{
			if (aliveWarriors.contains(adjacentCells.get(i)))
				damage += 1;
			else if (aliveWarriors.contains(EndGame.getThanosLocation()))
				damage += 5;
		}
		return damage;
	}
}
