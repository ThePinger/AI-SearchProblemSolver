package endgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import endgame.operators.*;
import genericsearch.Node;
import genericsearch.Operator;
import genericsearch.QINGFunction;
import genericsearch.SearchProblem;
import genericsearch.State;

public class EndGame extends SearchProblem
{
	private static int m; // an integer representing the maximum number of rows in the grid.
	private static int n; // an integer representing the maximum number of columns in the grid.
	private static Position thanosLocation; // Thanos' position in the grid.
	private Position ironmanStartingPosition; // This variable represents the starting position for Ironman. It will be used to create the initial state.
	private TreeSet<Position> uncollectedStones; // This variable represents the number and positions of infinity stones. It will be used to create the initial state.
	private TreeSet<Position> aliveWarriors; // This variable represents the number and positions of warriors. It will be used to create the initial state.
	
	public EndGame(int m, int n, Position thanosLocation, Position ironmanStartingPosition, TreeSet<Position> uncollectedStones, TreeSet<Position> aliveWarriors)
	{
		// initialize the static variables
		EndGame.m = m;
		EndGame.n = n;
		EndGame.thanosLocation = thanosLocation;
		
		// initialize instance variables
		this.ironmanStartingPosition = ironmanStartingPosition;
		this.uncollectedStones = uncollectedStones;
		this.aliveWarriors = aliveWarriors;
		
		// initialize the list of operators that will be used to solve the problem
		ArrayList<Operator> operators = new ArrayList<Operator>();
		operators.add(new Up());
		operators.add(new Right());
		operators.add(new Down());
		operators.add(new Left());
		operators.add(new Snap());
		operators.add(new Kill());
		operators.add(new Collect());
		super.setOperators(operators);
	}
	
	/**
	 * This method applies the goal test to the passed node.
	 * A node passes the goal test if all the following conditions are met:
	 * 	1) Ironman is located at the same cell as Thanos.
	 * 	2) All infinity stones have been collected (uncollectedStones is empty).
	 * 	3) Ironman did snap (isThanosAlive is false).
	 * 	4) The total damage received is less than 100.
	 * The method returns true if the passed node passes the goal test and false otherwise.
	 */
	@Override
	public boolean goalTest(Node node)
	{
		EndGameState nodeState = (EndGameState) node.getState();
		int damage = nodeState.getDamage();
		TreeSet<Position> uncollectedStones = nodeState.getUncollectedStones();
		Position ironmanPosition = nodeState.getIronManPosition();
		boolean isThanosAlive = nodeState.isThanosAlive();
		
		if( ironmanPosition.equals(EndGame.getThanosLocation()) &&
			damage < 100 && uncollectedStones.isEmpty() && !isThanosAlive)
			return true;
		else
			return false;
		
	}
	
	/**
	 * This method returns the cost of reaching this state from the root.
	 * @param state
	 * @return the path cost to get to this state.
	 */
	@Override
	public int pathCost(State state) 
	{
		return ((EndGameState) state).getDamage();
	}

	/**
	 * This method uses the variables defined on creation to create the initial state for the problem.
	 * @return the initial state of the problem.
	 */
	@Override
	public State createInitialState() 
	{
		return new EndGameState(this.ironmanStartingPosition, 0, this.uncollectedStones, this.aliveWarriors, true);
	}
	
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

	@Override
	public void calculateExpectedCostToGoal(Node node)
	{
		if(node.getQingFunction() == QINGFunction.A_STAR_1)
			node.setExpectedCostToGoal(this.aStarHeuristic1(node));
		if(node.getQingFunction() == QINGFunction.A_STAR_2)
			node.setExpectedCostToGoal(this.aStarHeuristic2(node));
		if(node.getQingFunction() == QINGFunction.GREEDY_1)
			node.setExpectedCostToGoal(this.greedyHeuristic1(node));
		if(node.getQingFunction() == QINGFunction.GREEDY_2)
			node.setExpectedCostToGoal(this.greedyHeuristic2(node));
	}
	
	/**
	 * This is one of the heuristic functions used for Greedy Search.
	 * It calculates the expected cost to the goal as follows: <br><br>
	 *                 <b> f(n) = h(n) </b> <br><br>
	 * where h(n) is heuristic function 1 for Greedy search.
	 * h(n) is calculated as follows: <br><br>
	 *                 <b> h(n) = (3 * s) + (2 * w) + 5 </b> <br><br>
	 * where s is the number of uncollected stones and w is the number of warriors adjacent to all uncollected stones.
	 * @param the node that the expected for is being calculated.
	 * @return the expected cost to the goal ( f(n) )
	 */
	private double greedyHeuristic1(Node node)
	{
		EndGameState state = (EndGameState) node.getState();
		TreeSet<Position> stones = state.getUncollectedStones();
		TreeSet<Position> warriors = state.getAliveWarriors();
		int uncollectedStones = stones.size();
		
		int warriorsAdjacentToStonesCount = 0;
		for(Position stonePosition: stones)
		{
			ArrayList<Position> adjacentCells = this.getAdjacentCells(stonePosition);
			for(Position adj: adjacentCells)
				if(warriors.contains(adj))
					warriorsAdjacentToStonesCount++;
		}
		
		return (3 * uncollectedStones) + (2 * warriorsAdjacentToStonesCount) + (state.isThanosAlive() ? 5 : 0);
	}
	
	/**
	 * This is one of the heuristic functions used for Greedy Search.
	 * It calculates the expected cost to the goal as follows: <br><br>
	 *                 <b> f(n) = h(n) </b> <br><br>
	 * where h(n) is heuristic function 2 for Greedy search.
	 * h(n) is calculated as follows: <br><br>
	 *                 <b> h(n) = (3 * s) + (0.75 * w) + (5 * (t + 1)) </b> <br><br>
	 * where s is the number of uncollected stones, 
	 * w is the number of warriors adjacent to all uncollected stones and t is the number of uncollected stones adjacent to thanos position.
	 * @param the node that the expected for is being calculated.
	 * @return the expected cost to the goal ( f(n) )
	 */
	private double greedyHeuristic2(Node node)
	{
		EndGameState state = (EndGameState) node.getState();
		TreeSet<Position> stones = state.getUncollectedStones();
		TreeSet<Position> warriors = state.getAliveWarriors();
		int uncollectedStones = stones.size();
		
		int warriorsAdjacentToStonesCount = 0;
		int thanosAdjacentToStonesCount = 0;
		for(Position stonePosition: stones)
		{
			ArrayList<Position> adjacentCells = this.getAdjacentCells(stonePosition);
			for(Position adj: adjacentCells)
			{
				if(warriors.contains(adj))
					warriorsAdjacentToStonesCount++;
				if(EndGame.thanosLocation.equals(adj))
					thanosAdjacentToStonesCount++;
			}
		}
		
		return (3 * uncollectedStones) + (0.75 * warriorsAdjacentToStonesCount) + (5 * (thanosAdjacentToStonesCount + (state.isThanosAlive() ? 1 : 0)));
	}
	
	/**
	 * This is one of the heuristic functions used for A-Star Search.
	 * It calculates the expected cost to the goal as follows: <br><br>
	 *                 <b> f(n) = g(n) + h(n) </b> <br><br>
	 * where g(n) is the path cost from the root to the node and h(n) is heuristic function 1 for A-Star search.
	 * h(n) is calculated as follows: <br><br>
	 *                 <b> h(n) = (3 * s) + 5 </b> <br><br>
	 * where s is the number of uncollected stones.
	 * @param the node that the expected for is being calculated.
	 * @return the expected cost to the goal ( f(n) )
	 */
	private double aStarHeuristic1(Node node)
	{
		int pathCost = node.getPathCost();
		int stonesRemaining = ((EndGameState) node.getState()).getUncollectedStones().size();
		
		double h = (stonesRemaining * 3) + (((EndGameState) node.getState()).isThanosAlive() ? 5 : 0);
		
		return pathCost + h;
	}
	
	/**
	 * This is one of the heuristic functions used for A-Star Search.
	 * It calculates the expected cost to the goal as follows: <br><br>
	 *                 <b> f(n) = g(n) + h(n) </b> <br><br>
	 * where g(n) is the path cost from the root to the node and h(n) is heuristic function 1 for A-Star search.
	 * h(n) is calculated as follows: <br><br>
	 *                 <b> h(n) = (3 * s) + (0.75 * w) + 5 </b> <br><br>
	 * where s is the number of uncollected stones and w is the number of warriors that occupy a cell that is adjacent to any of the stones.
	 * @param the node that the expected for is being calculated.
	 * @return the expected cost to the goal ( f(n) )
	 */
	private double aStarHeuristic2(Node node)
	{
		int pathCost = node.getPathCost();
		int stonesRemaining = ((EndGameState) node.getState()).getUncollectedStones().size();
		TreeSet<Position> stones = ((EndGameState) node.getState()).getUncollectedStones();
		TreeSet<Position> warriors = ((EndGameState) node.getState()).getAliveWarriors();
		
		int warriorsAdjacentToStones = 0;
		while(!stones.isEmpty())
		{
			Position currentStone = stones.pollFirst();
	
			ArrayList<Position> adjacentCells = this.getAdjacentCells(currentStone);
			
			while(!warriors.isEmpty())
			{
				Position currentWarrior = warriors.pollFirst();
				if(adjacentCells.contains(currentWarrior))
					warriorsAdjacentToStones++;
			}
			
			// get a new clone of the warrior positions for next iteration
			warriors = ((EndGameState) node.getState()).getAliveWarriors();
		}
		
		double h = (stonesRemaining * 3) + (0.75 * warriorsAdjacentToStones) + (((EndGameState) node.getState()).isThanosAlive() ? 5 : 0);
		
		return pathCost + h;
	}
	
	/**
	 * This method is only meant to be used as a helper for the calculation of the expected cost
	 * using AStarHueristic2. It returns all positions adjacent to a given position. Here a position
	 * is considered adjacent if it is either up, down, left, or right. diagonals are not considered adjacent.
	 * @param position
	 * @return all adjacent positions
	 */
	private ArrayList <Position> getAdjacentCells(Position position)
	{
		int x = position.getX();
		int y = position.getY();
		
		ArrayList <Position> a = new ArrayList<Position>();
		
		a.add(new Position(x+1,y));
		a.add(new Position(x-1,y));
		a.add(new Position(x,y+1));
		a.add(new Position(x,y-1));
			
		return a;
		
	}
	
	/**
	 * This function prints the grid at each state in the path from root to GoalNode.
	 * 
	 * @param cur is the goalnode as parameter to the function
	 */
	public void visualize(Node cur)
	{
		if(cur == null) return;
		visualize(cur.getParentNode());
		generateAndPrint2DGrid((EndGameState) cur.getState(), cur.getDepth());
	}
	
	/**
	 * This is a helper function that generates and prints the grid
	 * 
	 * @param state the current endGame state
	 * @param depth the state node depth in search tree to be printed as a state number
	 */
	private void generateAndPrint2DGrid(EndGameState state, int depth)
	{
		StringBuilder[][] grid = new StringBuilder[EndGame.m][EndGame.n];
		
		for(int i = 0; i < EndGame.m; i++)
			for(int j = 0; j < EndGame.n; j++)
				grid[i][j] = new StringBuilder();
			
		
		Position ironMan = state.getIronManPosition();
		grid[ironMan.getX()][ironMan.getY()].append('I');
		
		TreeSet<Position> warriors = state.getAliveWarriors();
		for(Position p: warriors)
			grid[p.getX()][p.getY()].append('W');
		
		TreeSet<Position> stones = state.getUncollectedStones();
		for(Position p: stones)
			grid[p.getX()][p.getY()].append('S');
		
		if(state.isThanosAlive())
			grid[EndGame.thanosLocation.getX()][EndGame.getThanosLocation().getY()].append('T');
		
		for(int i = 0; i < EndGame.m; i++)
			for(int j = 0; j < EndGame.n; j++)
				if(grid[i][j].length() == 0)
					grid[i][j].append('_');
		
		System.out.println("State: " + depth);
		for(StringBuilder[] sBuilders : grid)
			System.out.println(Arrays.toString(sBuilders));
		System.out.println();
	}
}
