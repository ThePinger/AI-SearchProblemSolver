package genericsearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SearchProblem 
{
	private ArrayList <Operator> operators;
	private State initialState;
	private HashSet <State> stateSpace;
	
	public ArrayList <Operator> getoperators()
	{
		return this.operators;
	}
	
	public void setOperators(ArrayList <Operator> operators)
	{
		this.operators = operators;
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
	
	public abstract void calculateExpectedCostToGoal(Node node);
	
	public Node generalSearch(QINGFunction q, int maximumDepth)
	{
		this.initialState = this.createInitialState();
		Node rootNode = new Node(this.initialState, null, null, 0, 0, q);
		Queue <Node> queue = new LinkedList<Node>();
		queue.add(rootNode);
		AuxiliaryQueue auxQ = new AuxiliaryQueue(q);
		auxQ.insertNewNodes(queue);
		while( !(auxQ.isEmpty()) )
		{
			Node node = auxQ.removeFront();
			if(this.goalTest(node))
				return node;
			if (node.getDepth() == maximumDepth)
				continue;
			auxQ.insertNewNodes(this.expand(node));
		}
		return null;
	}
	
	public Node dfs()
	{
		return this.generalSearch(QINGFunction.DFS, -1);
	}
	
	public Node ids()
	{
		int maximumDepth = 0;
		while(true)
		{
			Node goalNode = this.generalSearch(QINGFunction.IDS, maximumDepth);
			
			if(goalNode != null)
				return goalNode;
			else
				maximumDepth++;
		}
	}
	
	/**
	 * This method expands the current node applying all operators on it
	 * @param node The node that is going to be expanded
	 * @return A Queue that contains the child nodes
	 */
	public Queue <Node> expand(Node node)
	{
		Queue <Node> q = new LinkedList<Node>();
		for(int i = 0; i < this.operators.size(); i++)
		{
			State childNodeState = operators.get(i).apply(node.getState());
			if(childNodeState != null)
			{
				if( !(this.stateSpace.contains(childNodeState)) )
				{
					Node childNode = new Node(childNodeState, node, operators.get(i), (node.getParentNode().getDepth() + 1), this.pathCost(childNodeState), node.getQingFunction());
					this.calculateExpectedCostToGoal(childNode);
					this.stateSpace.add(childNodeState);
					q.add(childNode);
				}
			}
		}
		return q;
	}
}
