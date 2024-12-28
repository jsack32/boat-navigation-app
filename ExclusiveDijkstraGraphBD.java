// --== CS400 File Header Information ==--
// Name: Alice Shelton
// Email: rashelton@wisc.edu
// Group and Team: BT Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.util.List;
import java.util.ArrayList;

public class ExclusiveDijkstraGraphBD<NodeType,EdgeType extends Number> extends DijkstraGraphBD<NodeType,EdgeType> 
    implements ExclusiveDijkstraGraphInterface<NodeType, EdgeType> {

boolean isLimited = false;
List<NodeType> excludedList;

/* Constructor for the graph is below */ 
public ExclusiveDijkstraGraphBD() {
    excludedList = new ArrayList<NodeType>();
}

/* Overwrites the computeShortestPath method with the inclusion of the edgeLimit and excludedNodes*/
// protected SearchNode computeShortestPath(NodeType start, NodeType end);

/* Calls excludeNode method and setEdgeLimit method before running rest of method so that the proper path can be made. If the end node is not reachable due to these constraints, throws IllegalArgumentException */
public List<NodeType> shortestPathData(NodeType start, NodeType end) throws IllegalArgumentException {
    List<String> pathList = new ArrayList<String>();

    if(excludedList.size() != 0) {
        pathList.add("M");
        pathList.add("B");
        pathList.add("A");
        pathList.add("D");
        pathList.add("G");
    } else {
        pathList.add("A");

        if(isLimited) {
            pathList.add("B");
        }

        pathList.add("M");
    }

    @SuppressWarnings("unchecked") List<NodeType> returnList = (ArrayList<NodeType>) pathList;
    return returnList;
}

/* Calls excludeNode method and setEdgeLimit method before starting cost calculation so that the proper path can be made. If the end node is not reachable due to these constraints, throws IllegalArgumentException */
public double shortestPathCost(NodeType start, NodeType end) throws IllegalArgumentException {
    return 0;
}
/* Excludes the specified node from the tree without removing the edges connecting the excluded node to the rest of the graph */ 
public boolean excludeNode(NodeType excluded) {
    excludedList.add(excluded);
    return false;
}
/* Method that removes a node from the excluded node list if it is found in the list */
public boolean includeNode(NodeType include) {
    return false;
}
/* Accessor method for the list of nodes currently excluded in the tree */
public List<NodeType> getExcludedNodes() {
    return this.excludedList;
}
/* A method that sets a limit on edge cost (exclusive) so that edges over a certain cost will not be traveled upon. Could set a private field variable that can be called within other methods*/
public void setEdgeLimit(double maximumCost) {
    isLimited = true;
}
/* An accessor method for the edgeLimit field variable */
public double getEdgeLimit() {
    return 17;
}
/* Prints out a toString of the contents of the graph. See chat for more details */
public String toString() {
    return null;
}
}
