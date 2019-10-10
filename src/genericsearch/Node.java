package genericsearch;

public class Node 
{
	private State state;		// This variable represents the state of the given node
	private Node parent;		// This is a reference to the parent of this node
	private Operator operator;	// This is a reference to the operator applied to the parent node to get to this node
	private int depth;			// This is an integer representing the depth of this node in the search tree
	private int pathCost;		// This is an integer representing the cost of the sequence of actions required to reach this node from the root
	
	public Node(State state, Node parent, Operator operator, int depth, int pathCost)
	{
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
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
		return this.operator;
	}
	
	public State getState()
	{
		return this.state;
	}
	
	public Node getParent()
	{
		return this.parent;
	}
}
