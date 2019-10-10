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
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	/*
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
	
}
