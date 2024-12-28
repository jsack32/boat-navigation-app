// --== CS400 File Header Information ==--
// Name: Alice Shelton
// Email: rashelton@wisc.edu
// Group and Team: BT Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: N/A

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
public class DijkstraGraphBD<NodeType, EdgeType extends Number>
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
        if(!containsNode(start) || !containsNode(end)) {
            throw new NoSuchElementException("Requested start or end nodes do not exist!");
        }

        PriorityQueue<SearchNode> visitQueue = new PriorityQueue<SearchNode>();
        Hashtable<NodeType, SearchNode> visitedNodes = new Hashtable<NodeType, SearchNode>();

        // Create inial node for the queue
        visitQueue.add(new SearchNode(nodes.get(start), 0, null));
        
        // Run through the queue until it's empty
        while(!visitQueue.isEmpty()) {
            SearchNode currentNode = visitQueue.remove();

            // Check if this node offers a cost improvement over what was previously searched
            if(!visitedNodes.containsKey(currentNode.node.data) ||
              visitedNodes.get(currentNode.node.data).cost > currentNode.cost) {
                visitedNodes.put(currentNode.node.data, currentNode);
            } else {
                continue; // Already explored an no improvements possible, skip to next item
            }

            // Add neighboring nodes to queue
            for(Edge e : currentNode.node.edgesLeaving) {
                visitQueue.add(new SearchNode(e.successor, currentNode.cost + e.data.doubleValue(), currentNode));
            }
        }

        if(!visitedNodes.containsKey(end)) {
            throw new NoSuchElementException("No way to reach end from start!");
        }

        return visitedNodes.get(end);
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
        SearchNode nodeStack = computeShortestPath(start, end);
        LinkedList<NodeType> dataList = new LinkedList<NodeType>();

        while(nodeStack != null) {
            dataList.addFirst(nodeStack.node.data);
            nodeStack = nodeStack.predecessor;
        }

        return dataList;
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
        return computeShortestPath(start, end).cost;
    }
}
