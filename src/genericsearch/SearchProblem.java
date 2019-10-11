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
	
	/**
	 * This method expands the current node applying all operators on it
	 * @param node The node that is going to be expanded
	 * @return A Queue that contains the child nodes
	 */
	public Queue <Node> expand(Node node)
	{
		Queue <Node> q = new LinkedList<Node>();
		for(int i = 0; i < this.genericOperators.size(); i++)
		{
			State childNodeState = genericOperators.get(i).apply(node.getState());
			if(childNodeState != null)
			{
				if( !(this.stateSpace.contains(childNodeState)) )
				{
					Node childNode = new Node(childNodeState, node, genericOperators.get(i), (node.getParentNode().getDepth() + 1), this.pathCost(childNodeState));
					this.stateSpace.add(childNodeState);
					q.add(childNode);
				}
			}
		}
		return q;
	}
}
