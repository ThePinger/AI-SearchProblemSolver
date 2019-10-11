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
	
	/*
	 * (non-Javadoc)
	 * @see genericsearch.State#generateStateID()
	 * 
	 * Generates a String ID for the state to be stored in the StateSpace
	 */
	@Override
	public String generateStateID() 
	{
		StringBuilder sb = new StringBuilder();
		
		// Append IronMan Position
		sb.append(this.ironManPosition.getX());
		sb.append(this.ironManPosition.getY());
		
		// Append Damage
		sb.append(this.damage);
	
		// Append Uncollected Stones Positions
		for(Position p: this.uncollectedStones)
		{
			sb.append(p.getX());
			sb.append(p.getY());
		}
		
		// Append Alive Warriors Positions
		for(Position p: this.aliveWarriors)
		{
			sb.append(p.getX());
			sb.append(p.getY());
		}
		
		// Append isThanosAlive
		sb.append(this.isThanosAlive);	
		
		return sb.toString();
	}

	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * Compares two EndGame States and check if they are identical.
	 * Checks ironManPosition, damage, isThanosAlive and compares aliveWarriors and uncollectedStones Sets 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) 
	{
		if(!(obj instanceof EndGameState)) return false;
		
		EndGameState otherObject = (EndGameState) obj;
		if(!this.ironManPosition.equals(otherObject.ironManPosition)) return false;
		if(this.damage != otherObject.damage) return false;
		if(!this.identicalTreeSet((TreeSet<Position>) this.uncollectedStones.clone(), (TreeSet<Position>) otherObject.uncollectedStones.clone()))
			return false;
		if(!this.identicalTreeSet((TreeSet<Position>) this.aliveWarriors.clone(), (TreeSet<Position>) otherObject.aliveWarriors.clone()))
			return false;
		if(this.isThanosAlive != otherObject.isThanosAlive) return false;
		
		return true;
	}
	
	/*
	 * Private function that checks that both TreeSets are identical
	 */
	private boolean identicalTreeSet(TreeSet<Position> a, TreeSet<Position> b)
	{
		if(a.size() != b.size()) return false;
		
		for(Position p: a)
			if(!b.contains(p))
				return false;
		
		return true;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		
		// IronMan Position
		sb.append("IronMan Position :");
		sb.append(this.ironManPosition.toString());
		sb.append("\n");
		
		// Damage
		sb.append("Damage : ");
		sb.append(this.damage);
		sb.append("\n");
		
		// Uncollected Stones
		sb.append("Uncollected Stones Positions :");
		for(Position p : this.uncollectedStones)
		{
			sb.append(p.toString());
			sb.append("\n");
		}
		
		// Alive Warriors
		sb.append("Alive Warriors Positions :");
		for(Position p : this.aliveWarriors)
		{
			sb.append(p.toString());
			sb.append("\n");
		}
		
		// isThanosAlive
		sb.append("isThanosAlive : ");
		sb.append(this.isThanosAlive);
		
		return sb.toString();
	}
}
