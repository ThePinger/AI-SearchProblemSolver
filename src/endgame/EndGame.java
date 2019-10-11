package endgame;

import genericsearch.SearchProblem;

public class EndGame extends SearchProblem
{
	private static int m; // an integer representing the maximum length of the grid
	private static int n; // an integer representing the maximum width of the grid
	private static Position thanosLocation; // Thanos' position in the grid
	
	public static int getM()
	{
		return m;
	}
	
	public static int getN()
	{
		return n;
	}
	
	public static Position getThanosLocation()
	{
		return thanosLocation;
	}
}
