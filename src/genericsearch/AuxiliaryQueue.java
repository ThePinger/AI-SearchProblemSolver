package genericsearch;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AuxiliaryQueue
{
	/**
	 * The purpose of this class is to make the queuing process in the general search procedure easier. Upon creation, it checks on the QINGFunction passed to it in
	 * the constructor and decides what kind of data structure is going to be used. For DFS and IDS, it uses a stack. For BFS, it uses a queue. For UCS, it uses a priority queue.
	 * It stores the qingFunction as an instance variable to be able to decide how to store/remove nodes after it has been created.
	 */
	private Object auxQueue;
	private QINGFunction qingFunction;
	
	public AuxiliaryQueue(QINGFunction Q)
	{
		this.qingFunction = Q;
		
		switch(Q)
		{
		case DFS: this.auxQueue = new Stack<Node>(); break;
		case IDS: this.auxQueue = new Stack<Node>(); break;
		case BFS: this.auxQueue = new LinkedList<Node>(); break;
		case UCS: this.auxQueue = new PriorityQueue<Node>(); break;
		}
	}
	
	/**
	 * This method checks the qing function that this AuxiliaryQueue is following and casts its type accordingly then adds the newly created nodes.
	 * @param newNodes a Queue of the newly created nodes.
	 */
	@SuppressWarnings("unchecked")
	public void insertNewNodes(Queue<Node> newNodes)
	{
		switch(this.qingFunction)
		{
		case DFS: 	
			while(!newNodes.isEmpty())
				((Stack<Node>) this.auxQueue).push(newNodes.poll());
			break;
			
		case BFS:
			while(!newNodes.isEmpty())
				((Queue<Node>) this.auxQueue).add(newNodes.poll());
			break;
			
		case IDS:
			while(!newNodes.isEmpty())
				((Stack<Node>) this.auxQueue).push(newNodes.poll());
			break;
			
		case UCS:
			while(!newNodes.isEmpty())
				((PriorityQueue<Node>) this.auxQueue).add(newNodes.poll());
		}
	}
	
	/**
	 * This method checks if the data structure stored inside this aux queue is empty.
	 * @return true if it is empty, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean isEmpty()
	{
		switch(this.qingFunction)
		{
		case DFS: return ((Stack<Node>) this.auxQueue).isEmpty();
		case BFS: return ((Queue<Node>) this.auxQueue).isEmpty();
		case IDS: return ((Stack<Node>) this.auxQueue).isEmpty();
		case UCS: return ((PriorityQueue<Node>) this.auxQueue).isEmpty();
		default: return false;
		}
	}
	
	/**
	 * This method checks the qing function that this aux queue is following and type casts it accordingly then removes the node at the front. 
	 * @return the Node at the front of the aux queue
	 */
	@SuppressWarnings("unchecked")
	public Node removeFront()
	{
		switch(this.qingFunction)
		{
		case DFS: return ((Stack<Node>) this.auxQueue).pop();
		case IDS: return ((Stack<Node>) this.auxQueue).pop();
		case BFS: return ((Queue<Node>) this.auxQueue).poll();
		case UCS: return ((PriorityQueue<Node>) this.auxQueue).poll();
		default: return null;
		}
		
	}
}
