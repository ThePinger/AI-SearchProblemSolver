import java.util.TreeSet;

import endgame.EndGame;
import endgame.Position;
import genericsearch.Node;
import genericsearch.QINGFunction;

public class Main 
{

	public static String solve(String grid, String strategy, boolean visualize)
	{
		String[] splitted = grid.split(";");
		
		String[] gridDimensions = splitted[0].split(",");
		int m = Integer.parseInt(gridDimensions[0]);
		int n = Integer.parseInt(gridDimensions[1]);
		
		String[] ironMan = splitted[1].split(",");
		Position ironManPosition = new Position(Integer.parseInt(ironMan[0]), Integer.parseInt(ironMan[1]));
		
		String[] thanos = splitted[2].split(",");
		Position thanosPosition = new Position(Integer.parseInt(thanos[0]), Integer.parseInt(thanos[1]));
		
		String[] stones = splitted[3].split(",");
		TreeSet<Position> stonesPositions = new TreeSet<>();
		for(int i = 0; i < 12; i += 2)
			stonesPositions.add(new Position(Integer.parseInt(stones[i]), Integer.parseInt(stones[i + 1])));
		
		String[] warriors = splitted[4].split(",");
		TreeSet<Position> warriorsPositions = new TreeSet<>();
		for(int i = 0; i < warriors.length; i += 2)
			warriorsPositions.add(new Position(Integer.parseInt(warriors[i]), Integer.parseInt(warriors[i + 1])));
		
		// Pass strategy, visualize and return value
		EndGame endGame = new EndGame(m, n, thanosPosition, ironManPosition, stonesPositions, warriorsPositions);
		
		Node goalNode = null;
		if(strategy.equals("BF")) goalNode = endGame.bfs();
		else if(strategy.equals("DF")) goalNode = endGame.dfs();
		else if(strategy.equals("ID")) goalNode = endGame.ids();
		else if(strategy.equals("UC")) goalNode = endGame.ucs();
		else if(strategy.equals("GR1")) goalNode = endGame.greedySearch(1);
		else if(strategy.equals("GR2")) goalNode = endGame.greedySearch(2);
		else if(strategy.equals("AS1")) goalNode = endGame.AStar(1);
		else goalNode = endGame.AStar(2);
		
		return endGame.generateSolutionString(goalNode);
	}
	
	public static void main(String[] args) 
	{
		solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3", "BF", false);
	}

}
