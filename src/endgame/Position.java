package endgame;

public class Position implements Comparable<Position>
{

	private int x;
	private int y;
	
	public Position(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY() 
	{
		return this.y;
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * Checks if two Position objects are identical
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if(!(obj instanceof Position)) return false;

		Position otherPosition = (Position) obj;
		
		return this.x == otherPosition.x && this.y == otherPosition.y;
	}
	
	/**
	 * This function compares two positions A, B. 
	 * IF position A equal to position B returns 0	
	 * ELSE compares according to X, Y (Not Used in the Project)
	 */
	@Override
	public int compareTo(Position p) 
	{
		if(this.x == p.x)
			return this.y - p.y;
		return this.x - p.x;
	}
	
	@Override
	public String toString() 
	{
		return "Position: X = " + this.x + ", Y = " + this.y;
	}
}
