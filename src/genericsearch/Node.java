package genericsearch;

public class Node implements Comparable<Node>
{
	private State state;	// This variable represents the state of the given node
	private Node parentNode;	// This is a reference to the parent of this node
	private Operator appliedOperator;	// This is a reference to the operator applied to the parent node to get to this node
	private int depth;	// This is an integer representing the depth of this node in the search tree
	private int pathCost;	// This is an integer representing the cost of the sequence of actions required to reach this node from the root
	private double expectedCostToGoal; // This is a double representing the expected cost to goal, it is set to -1 if the Qing function used does not use a heuristic function
	private QINGFunction qingFunction; // This represents the qing function being used while this node was created 
	
	public Node(State state, Node parentNode, Operator appliedOperator, int depth, int pathCost, QINGFunction Q)
	{
		this.state = state;
		this.parentNode = parentNode;
		this.appliedOperator = appliedOperator;
		this.depth = depth;
		this.pathCost = pathCost;
		this.qingFunction = Q;
		this.expectedCostToGoal = -1;
	}
	
	public QINGFunction getQingFunction()
	{
		return this.qingFunction;
	}
	
	public double getExpectedCostToGoal()
	{
		return this.expectedCostToGoal;
	}
	
	public void setExpectedCostToGoal(double cost)
	{
		this.expectedCostToGoal = cost;
	}
	
	public int getPathCost()
	{
		return this.pathCost;
	}
	
	public int getDepth()
	{
		return this.depth;
	}
	
	public Operator getOperator()
	{
		return this.appliedOperator;
	}
	
	public State getState()
	{
		return this.state;
	}
	
	public Node getParentNode()
	{
		return this.parentNode;
	}

	/**
	 * This method returns a negative number if this node has lower cost than Node o, returns 0 if they have identical costs,
	 * and return a positive number if Node o has a lower cost.
	 */
	public int compareTo(Node o)
	{
		return this.pathCost - o.pathCost;
	}
}
