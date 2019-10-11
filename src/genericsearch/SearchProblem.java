package genericsearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SearchProblem 
{
	private ArrayList <Operator> genericOperators;
	private State initialState;
	private HashSet <State> stateSpace;
	
	public ArrayList <Operator> getGenericOperators()
	{
		return this.genericOperators;
	}
	
	public void setGenericOperators(ArrayList <Operator> genericOperators)
	{
		this.genericOperators = genericOperators;
	}

	public State getInitialState()
	{
		return this.initialState;
	}

	public void setInitialState(State initialState)
	{
		this.initialState = initialState;
	}

	public HashSet <State> getStateSpace()
	{
		return this.stateSpace;
	}
	
	public abstract boolean goalTest(Node node);
	
	public abstract int pathCost(State state);
	
	public abstract State createInitialState();

}
