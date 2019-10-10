package endgame;

import java.util.TreeSet;

import genericsearch.State;

public class EndGameState implements State
{
	private Position ironManPosition;
	private int damage;
	private TreeSet<Position> uncollectedStones;
	private TreeSet<Position> aliveWarriors;
	private boolean isThanosAlive;
	
	public EndGameState(Position ironManPosition, int damage, TreeSet<Position> uncollectedStones, TreeSet<Position> aliveWarriors, boolean isThanosAlive) 
	{
		this.ironManPosition = ironManPosition;
		this.damage = damage;
		this.uncollectedStones = uncollectedStones;
		this.aliveWarriors = aliveWarriors;
		this.isThanosAlive = isThanosAlive;
	}
	
	public Position getIronManPosition() 
	{
		return this.ironManPosition;
	}
	
	public int getDamage() 
	{
		return this.damage;
	}
	
	public TreeSet<Position> getUncollectedStones() 
	{
		return (TreeSet<Position>) this.uncollectedStones.clone();
	}
	
	public TreeSet<Position> getAliveWarriors() 
	{
		return (TreeSet<Position>) this.aliveWarriors.clone();
	}
	
	public boolean isThanosAlive() 
	{
		return this.isThanosAlive;
	}

	@Override
	public int compareTo(State s) 
	{
		return 0;
	}
}
