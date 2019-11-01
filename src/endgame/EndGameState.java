package endgame;

import java.util.Arrays;
import java.util.TreeSet;

import genericsearch.State;


public class EndGameState implements State
{
	// This variable represents IronMan Position
	private String ironManPosition; 
	// This variable represents the damage received by IronMan
	private int damage; 
	// This variable represents a set of uncollected stones
	private String uncollectedStones;
	// This variable represents a set of alive warriors
	private String aliveWarriors; 
	// This variable represents if thanos is alive or not
	private boolean isThanosAlive;
	
	public EndGameState(Position ironManPosition, int damage, TreeSet<Position> uncollectedStones, TreeSet<Position> aliveWarriors, boolean isThanosAlive) 
	{
		this.ironManPosition = ironManPosition.toString();
		this.damage = damage;
		
		this.uncollectedStones = "";
		for(Position current : uncollectedStones)
		{
			this.uncollectedStones += current.toString() + " ";
		}
		if(this.uncollectedStones.length() != 0)
			this.uncollectedStones = this.uncollectedStones.substring(0, this.uncollectedStones.length() - 1);
		
		this.aliveWarriors = "";
		for(Position current : aliveWarriors)
		{
			this.aliveWarriors += current.toString() + " ";
		}
		if(this.aliveWarriors.length() != 0)
			this.aliveWarriors = this.aliveWarriors.substring(0, this.aliveWarriors.length() - 1);
		
		this.isThanosAlive = isThanosAlive;
		
	}
	
	public Position getIronManPosition() 
	{
		String[] position = this.ironManPosition.split(",");
		return  new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
	}
	
	public int getDamage() 
	{
		return this.damage;
	}
	
	public TreeSet<Position> getUncollectedStones() 
	{
		String[] stones = this.uncollectedStones.split(" ");
		TreeSet<Position> stonesPositions = new TreeSet<>();
		if(this.uncollectedStones.length() == 0) return stonesPositions;
		for(int i = 0; i < stones.length; i++)
		{
			String[] position = stones[i].split(",");
			stonesPositions.add(new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1])));
		}
		
		return stonesPositions;
	}
	
	public TreeSet<Position> getAliveWarriors() 
	{
		String[] warriors = this.aliveWarriors.split(" ");
		TreeSet<Position> warriorsPositions = new TreeSet<>();
		if(this.aliveWarriors.length() == 0) return warriorsPositions;
		for(int i = 0; i < warriors.length; i++)
		{
			String[] position = warriors[i].split(",");
			warriorsPositions.add(new Position(Integer.parseInt(position[0]), Integer.parseInt(position[1])));
		}
		
		return warriorsPositions;
	}
	
	public boolean isThanosAlive() 
	{
		return this.isThanosAlive;
	}
	
	@Override
	public String generateStateID() 
	{
		StringBuilder sb = new StringBuilder();
		
		// Append IronMan Position
		sb.append(this.ironManPosition);
		
		// Append Damage
		sb.append(this.damage);
	
		// Append Uncollected Stones Positions
		sb.append(this.uncollectedStones);
		
		// Append Alive Warriors Positions
		sb.append(this.aliveWarriors);
		
		// Append isThanosAlive
		sb.append(this.isThanosAlive);	
		
		return sb.toString();
	}

	
	/**
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
		/*if(!this.ironManPosition.equals(otherObject.ironManPosition)) return false;
		if(this.damage != otherObject.damage) return false;
		if(!this.identicalTreeSet((TreeSet<Position>) this.uncollectedStones.clone(), (TreeSet<Position>) otherObject.uncollectedStones.clone()))
			return false;
		if(!this.identicalTreeSet((TreeSet<Position>) this.aliveWarriors.clone(), (TreeSet<Position>) otherObject.aliveWarriors.clone()))
			return false;
		if(this.isThanosAlive != otherObject.isThanosAlive) return false;
		
		return true;*/
		return this.generateStateID().equals(otherObject.generateStateID());
	}
	
	/**
	 * Private function that checks that both TreeSets are identical
	 * @param a
	 * @param b
	 * @return true if TreeSet a is identical to TreeSet b
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
		/*sb.append("Uncollected Stones Positions :");
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
		}*/
		
		// isThanosAlive
		sb.append("isThanosAlive : ");
		sb.append(this.isThanosAlive);
		
		return sb.toString();
	}
}
