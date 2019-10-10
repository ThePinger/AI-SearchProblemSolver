package endgame;

import genericsearch.SearchProblem;

public class EndGame extends SearchProblem
{
	private static int m; // an integer representing the maximum length of the grid
	private static int n; // an integer representing the maximum width of the grid
	private static Position thanosLocation; // Thanos' position in the grid
	
	public int getM()
	{
		return m;
	}
	
	public void setM(int m)
	{
		EndGame.m = m;
	}
	
	public int getN()
	{
		return n;
	}
	
	public void setN(int n)
	{
		EndGame.n = n;
	}
	
	public Position getThanosLocation()
	{
		return thanosLocation;
	}
	
	public void setThanosLocation(Position thanosLocation)
	{
		EndGame.thanosLocation = thanosLocation;
	}
}
