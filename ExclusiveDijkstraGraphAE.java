import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Map.Entry;
import java.util.Set;

public class ExclusiveDijkstraGraphAE<NodeType, EdgeType extends Number> extends DijkstraGraph<NodeType,EdgeType>
implements ExclusiveDijkstraGraphInterface<NodeType,EdgeType>{
	
	private double edgeLimit;
	private List<NodeType> excludedNodes;
	
	public ExclusiveDijkstraGraphAE() {
		excludedNodes = new ArrayList<NodeType>();
		edgeLimit = Double.MAX_VALUE;
	}

	/**
	 * This method is nearly identical to the computeShortestPath method of the original 
	 * DijkstraGraph, however it checks for excluded Nodes or edgeLimits that would change
	 * the outcome of the shortest path by hindering (at times) the shortest route.
	 *
	 * @param start, the data of the Node that the shortest path should start at
	 * @param end, the data of the Node at which the shortest path should end
	 * @return SearchNode containing the desired end data which contains the cost
	 * to reach it from the starting node, as well as the prior nodes in the path
	 * @throws NoSuchElementException if the starting or ending node do not exist in the graph
	 * or if the desired path is impossible
	 */
	@Override
	protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    	// Checks if the starting and ending node exist within the graph,
    	// throwing an exception if they do not
        if (!containsNode(start) || !containsNode(end)) {
        	throw new NoSuchElementException("The starting or ending node do not exist!");
        }
        // Creates a hashtable to hold visited nodes, mapping a the ending node to a search Node
        Hashtable<Node,SearchNode> visitedNodes = new Hashtable<Node,SearchNode>();
        
        
        // Creates a PriorityQueue to hold the all of the search nodes with lowest costs
        PriorityQueue<SearchNode> possiblePaths = new PriorityQueue<SearchNode>();
        // Inserts the starting Node into the priorityQueue as a source to start the algorithm
        possiblePaths.add(new SearchNode(nodes.get(start),0,null));
        // Adds the starting node to the Hashtable of visited nodes with a path cost of 0 to itself
        visitedNodes.put(nodes.get(start), new SearchNode(nodes.get(start),0,null));
        
        // Runs a while loop that ends if there are no more possible paths to check through
        while(!possiblePaths.isEmpty()) {
        	// Creates a SearchNode copy of the lowest-cost path possibility for ease of
        	// calling throughout the loop
        	SearchNode currNode = possiblePaths.remove();
        	
        	// Checks if the currentNode is in the visitedNodes table and adds it if not
        	if(!visitedNodes.containsKey(currNode.node)) {
        		if(currNode.cost<edgeLimit && excludedNodes.contains(currNode.node.data)) {
        			
        		}else {
        		visitedNodes.put(currNode.node,currNode);
        		}
        	}
        	
        	// Creates a list of edges that lead to neighboring nodes
        	List<Edge> neighbors = currNode.node.edgesLeaving;
        	
        	// Iterates through the list above and creates a potential path 
        	// "SearchNode" out of each edge
        	for(Edge edge : neighbors) {
        		SearchNode potentialPath = new SearchNode(edge.successor,edge.data.doubleValue()+currNode.cost,currNode);
        		// If the neighboring node the edge leads to 
        		// has not yet been given a finalized path in visitedNodes, 
        		// adds the path to the priority queue
        		if(!visitedNodes.containsKey(edge.successor)) {
        			if(edge.data.doubleValue()<edgeLimit && !excludedNodes.contains(potentialPath.node.data)) {
        				possiblePaths.add(potentialPath);
        			}
        		}
        	}
        }
        
        // If the end node was never visited during the search, it is not connected
        // in any way to the starting node, so an exception is thrown
        if(!visitedNodes.containsKey(nodes.get(end))) {
        	throw new NoSuchElementException("No path was found!");
        }
    	
        // If a path was found, then the method returns the ends SearchNode
        // which contains the total cost of the path and all prior nodes
        return visitedNodes.get(nodes.get(end));

	}

	/**
	 * This method allows the user to add a node into the list of currentky excluded nodes, which 
	 * prevents it from being traversed upon when creating the minimum spanning tree
	 *
	 * @return true, if the node was added to the list of currently excluded nodes,
	 * false if it was not found in the graph or already in said list
	 */
	@Override
	public boolean excludeNode(NodeType excluded) {
		// Checks if the desired node to be excluded is in the list of all nodes
		// If not, does nothing and returns false
		if(!containsNode(excluded)) {
			return false;
		}
		// Checks if the desired node to be excluded is in the current list of
		// excluded nodes. If it is, returns false
		if(excludedNodes.contains(excluded)) {
			return false;
		}
		
		// Adds the desired NodeType to the list of excluded nodes and returns true
		excludedNodes.add(excluded);
		return true;
	}

	/**
	 * This method allows the user to remove a node from the list of currently excluded nodes
	 * and allow it to be traversed upon again
	 *
	 * @return true if the node was removed from excludeNodes, false if th node
	 * does not exist in the graph or is not in excluded nodes
	 */
	@Override
	public boolean includeNode(NodeType include) {
		// Checks if the desired node to be included is in the hashtable of all nodes
		// If not, does nothing and returns false
		if(!containsNode(include)) {
			return false;
		}
		// Checks if the desired node to be included is in the current list of
		// excluded nodes. If it is not, returns false
		if(!excludedNodes.contains(include)) {
			return false;
		}
		
		// Removes the desired NodeType from the list of excluded nodes and returns true
		excludedNodes.remove(include);
		return true;
	}

	/**
	 * This method is an accessor method that returns the list of currently excluded nodes
	 * from the graph
	 *
	 * @return excludedNodes, the list of nodes that are currently being excluded from
	 * being traversed upon
	 */
	@Override
	public List<NodeType> getExcludedNodes() {
		return excludedNodes;
	}

	/**
	 * This method modifies the private field variable edgeLimit to whatever double is provided
	 *
	 * @param maximumCost, a double that represents the cost at which edges should not
	 * be traversed upon
	 */
	@Override
	public void setEdgeLimit(double maximumCost) {
		edgeLimit = maximumCost;
	}
	
	/**
	 * Accessor method that returns the private field variable edgeLimit for access
	 * in other classes
	 *
	 * @return edgeLimit, the current maximum cost of an edge 
	 * (exclusive) that can be traversed upon
	 */
	@Override
	public double getEdgeLimit() {
		return edgeLimit;
	}

	/**
	 * This toString method overrides the provided one and prints each node in the graph as well
	 * as a list of the connections that they have to other nodes within th graph. 
	 *
	 * @return graphContents, a string that contains the nodes in the graph 
	 * and their connections to other nodes
	 */
	@Override
	public String toString() {
		// Creates a String to hold the contents of the graph and be returned
		// Initializes string with a line and header stating "Graph"
		String graphContents = "------------------------------------------\n\n";
		graphContents +="Graph:\n\n";

		// Creates a set of all of the entries in the Hashtable of nodes, allowing
		// easier access to the contents of the graph
		Set<Entry<NodeType, BaseGraph<NodeType, EdgeType>.Node>> e = nodes.entrySet();
		// Transfers the graph data into an array, where each object in the array
		// contains an entry within the original hashtable.
		Object[] node = e.toArray();

		// Loops through the contents of the Array, adding the node as a header and
		// describing what other nodes it is connected to as well as their costs
		for(int x = 0; x<nodes.size();x++) {
			// Adds header of what the current node is
			graphContents+= ((Entry<NodeType, BaseGraph<NodeType, EdgeType>.Node>)
			// Describes the connections it has to other nodes in the graph
			node[x]).getKey().toString()+" with connections:\n";
			// Creates a list to hold all of its neighboring nodes for easy access
			List<Edge> edgesLeaving = ((Entry<NodeType, BaseGraph<NodeType, EdgeType>.Node>)
			node[x]).getValue().edgesLeaving;
			// Loops through the list of neighbors and prints the connection to the current node
			// as well as the cost between them
			for(int y = 0; y<edgesLeaving.size();y++) {
				Edge currEdge = edgesLeaving.get(y);
				String start = currEdge.predecessor.data.toString();
				String end = currEdge.successor.data.toString();
				graphContents += start+" -> "+end+" with cost: "+currEdge.data.toString()+"\n";
			}
			// Adds a line of space between this node and the next
			graphContents +="\n";

		}
		// Signals the end of the graph contents with a line then returns the finished String
		graphContents += "------------------------------------------";
		return graphContents;
	}

}

