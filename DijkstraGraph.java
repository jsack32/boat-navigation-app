// --== CS400 File Header Information ==--
// Name: Max Schmidt
// Email: mrschmidt9@wisc.edu
// Group and Team: Group BT -- Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.PriorityQueue;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes.  This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
    extends BaseGraph<NodeType,EdgeType>
    implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph.  The final node in this path is stored in it's node
     * field.  The total cost of this path is stored in its cost field.  And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in it's node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations.  The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *         or when either start or end data do not correspond to a graph node
     */
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
        		visitedNodes.put(currNode.node,currNode);
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
        			possiblePaths.add(potentialPath);
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
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value.  This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path.  This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) {
    	// Creates a SearchNode to hold the shortest path computation
    	SearchNode shortestPath = computeShortestPath(start,end);
    	// Creates a LinkedList to return at the end of the method
        List<NodeType> pathData = new LinkedList<NodeType>();
        
        // Runs a while loop that adds the data of the current node on the path
        // to the first index (as it is reversed in SearchNode), then sets the
        // current SearchNode as its predecessor. The loop ends when there is no predecessor
        while(shortestPath != null) {
        	pathData.add(0,shortestPath.node.data);
        	shortestPath = shortestPath.predecessor;
        }
        
        // Returns the list of node data in the order it was followed on the shortest path
        return pathData;
    }

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data.  This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) {
        return (double)computeShortestPath(start,end).cost;
    }
    

}
