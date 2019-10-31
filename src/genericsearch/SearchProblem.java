package genericsearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SearchProblem 
{
	private ArrayList <Operator> operators;
	private State initialState;
	private HashSet <String> stateSpace;
	private long expandedNodes;
	
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

	public HashSet <String> getStateSpace()
	{
		return this.stateSpace;
	}
	
	/**
	 * The method performs goalTest on a certain Node to determine if it is a Goal Node
	 * 
	 * @param node
	 * @return true if it is a Goal Node otherwise false
	 */
	public abstract boolean goalTest(Node node);
	
	/**
	 * This method calculates path cost from root to this State
	 * 
	 * @param state
	 * @return cost from root to state
	 */
	public abstract int pathCost(State state);
	
	/**
	 * This method creates initial state
	 * 
	 * @return
	 */
	public abstract State createInitialState();
	
	/**
	 * This method calculates expected cost to goal from a certain node and assigns that value to expectedCostToGoal variable in Node
	 * 
	 * @param node
	 */
	public abstract void calculateExpectedCostToGoal(Node node);
	
	/**
	 * 
	 * @param q
	 * @param maximumDepth
	 * @return
	 */
	public Node generalSearch(QINGFunction q, int maximumDepth)
	{
		this.expandedNodes = 0;
		this.stateSpace = new HashSet<>();
		this.initialState = this.createInitialState();
		this.stateSpace.add(this.initialState.generateStateID());
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
			this.expandedNodes++;
		}
		return null;
	}
	
	/**
	 * This method is regular A-star search, it takes an integer as input
	 * and determines which heuristic will be used. It then calls the generalSearch procedure.
	 * @param heuristicFunction, the int indicating the heuristic function that should be used.
	 * @return The result of calling generalSearch.
	 */
	public Node AStar(int heuristicFunction)
	{
		if(heuristicFunction == 1)
			return this.generalSearch(QINGFunction.A_STAR_1, -1);
		
		if(heuristicFunction == 2)
			return this.generalSearch(QINGFunction.A_STAR_2, -1);
		
		return null;
	}
	
	/**
	 * This method performs Depth First Search.
	 * @return goal node if found, null otherwise.
	 */
	public Node dfs()
	{
		return this.generalSearch(QINGFunction.DFS, -1);
	}
	
	/**
	 * This method performs Iterative Deepening Search.
	 * @return goal node if found, null otherwise.
	 */
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
	 * This method performs BreadthFirstSearch.
	 * 
	 * @return Goal Node.
	 */
	public Node bfs()
	{
		return this.generalSearch(QINGFunction.BFS, -1);
	}
	
	/**
	 * This method performs UniformCostSearch.
	 * 
	 * @return Goal Node.
	 */
	public Node ucs()
	{
		return this.generalSearch(QINGFunction.UCS, -1);
	}
	
	/**
	 * This method performs GreedySearch based on the input heuristic function.
	 * 
	 * @param heuristicFunction an integer indicating the heuristic function that should be used.
	 * @return Goal Node.
	 */
	public Node greedySearch(int heuristicFunction)
	{
		if(heuristicFunction == 1)
			return this.generalSearch(QINGFunction.GREEDY_1, -1);
		if(heuristicFunction == 2)
			return this.generalSearch(QINGFunction.GREEDY_2, -1);
		return null;
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
				String stateString = childNodeState.generateStateID();
				if( !(this.stateSpace.contains(stateString)) )
				{
					Node childNode = new Node(childNodeState, node, operators.get(i), (node.getParentNode().getDepth() + 1), this.pathCost(childNodeState), node.getQingFunction());
					this.calculateExpectedCostToGoal(childNode);
					this.stateSpace.add(stateString);
					q.add(childNode);
				}
			}
		}
		return q;
	}
	
	public String generateSolutionString(Node goalNode)
	{
		StringBuilder sb = new StringBuilder();
		Node curNode = goalNode;
		while(curNode != null)
		{
			sb.append(curNode.getOperator().getOperatorName());
			curNode = curNode.getParentNode();
			if(curNode != null) sb.append(',');
		}
		
		sb = sb.reverse();
		sb.append(';');
		sb.append(goalNode.getPathCost());
		sb.append(';');
		sb.append(this.expandedNodes);
		
		return sb.toString();
	}
}
