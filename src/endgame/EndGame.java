package endgame;

import java.util.ArrayList;
import java.util.TreeSet;

import endgame.operators.*;
import genericsearch.Node;
import genericsearch.Operator;
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
	public State createInitialState() {
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
}
