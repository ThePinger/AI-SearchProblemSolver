import java.util.TreeSet;

import endgame.Position;

public class Main 
{

	public static String solve(String grid, String strategy, boolean visualize)
	{
		String[] splitted = grid.split(";");
		
		String[] ironMan = splitted[0].split(",");
		Position ironManPosition = new Position(Integer.parseInt(ironMan[0]), Integer.parseInt(ironMan[1]));
		
		String[] thanos = splitted[1].split(",");
		Position thanosPosition = new Position(Integer.parseInt(thanos[0]), Integer.parseInt(thanos[1]));
		
		String[] stones = splitted[2].split(",");
		TreeSet<Position> stonesPositions = new TreeSet<>();
		for(int i = 0; i < 12; i += 2)
			stonesPositions.add(new Position(Integer.parseInt(stones[i]), Integer.parseInt(stones[i + 1])));
		
		String[] warriors = splitted[3].split(",");
		TreeSet<Position> warriorsPositions = new TreeSet<>();
		for(int i = 0; i < warriors.length; i += 2)
			warriorsPositions.add(new Position(Integer.parseInt(warriors[i]), Integer.parseInt(warriors[i + 1])));
		
		// Pass strategy, visualize and return value
		
		return null;
	}
	
	public static void main(String[] args) 
	{
		
	}

}
