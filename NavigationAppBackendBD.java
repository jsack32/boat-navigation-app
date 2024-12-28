// --== CS400 File Header Information ==--
// Name: Alice Shelton
// Email: rashelton@wisc.edu
// Group and Team: BT Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class NavigationAppBackendBD implements NavigationAppBackendInterface {
    private ExclusiveDijkstraGraphInterface<String, Double> portGraph;
    private String originPort;
    private String destinationPort;

    // Not an ideal system, but there's no way to actually get a list of nodes from the graph itself
    private List<String> portList;

    /**
     * Constructs NavigationAppBackend
     * 
     * @param graph The graph to be used as the port map
     */
    public NavigationAppBackendBD(ExclusiveDijkstraGraphInterface<String, Double> graph) {
        this.portGraph = graph;
        this.portList = new ArrayList<String>();
    }

    /**
     * Takes a String filename pointing to a file in DOT format and adds all listed nodes
     * and edges to the port map used by the backend.
     * 
     * @throws FileNotFoundException If given non-existent or inaccessible  filename
     * @param filename The filename to load points from
     */
    public void loadGraphFromFile(String filename) throws FileNotFoundException {
        for(String[] dotEntry : (new PortDotDW()).getPortMap(filename)) {
            // Keep track of new nodes, since there is no way to get them later from the graph
            if(portGraph.insertNode(dotEntry[0])) {
                portList.add(dotEntry[0]);
            }

            if(portGraph.insertNode(dotEntry[1])) {
                portList.add(dotEntry[1]);
            }

            portGraph.insertEdge(dotEntry[0], dotEntry[1], Double.parseDouble(dotEntry[2]));
        }
    }

    /**
     * Changes the maximum range that the boat can travel between nodes
     * 
     * @throws IllegalArgumentException If newrange is less than 1
     * @param newRange Integer to set the updated maximum range to
     */
    public void changeBoatRange(int newRange) throws IllegalArgumentException {
        if(newRange < 1) {
            throw new IllegalArgumentException("Attempted to set boat range below one!");
        }

        portGraph.setEdgeLimit(newRange);
    }

    /**
     * Returns the maximum boat travel range
     */
    public int getBoatRange() {
        return ((int) Math.round(portGraph.getEdgeLimit()));
    }

    /**
     * Sets the origin port to use for the path
     * 
     * @throws IllegalArgumentException If provided origin port does not exist on the map
     * @param origin String name of the port to use as an origin
     */
    public void setOrigin(String origin) throws IllegalArgumentException {
        if(!portGraph.containsNode(origin)) {
            throw new IllegalArgumentException("Given origin port does not exist!");
        }

        this.originPort = origin;
    }

    /**
     * Returns the origin port for the path.
     */
    public String getOrigin() {
        return originPort;
    }

    /**
     * Sets the destination port to use for the path
     * 
     * @throws IllegalArgumentException If provided destination port does not exist on the map
     * @param destination String name of the port to use as a destination
     */
    public void setDestination(String destination) throws IllegalArgumentException {
        if(!portGraph.containsNode(destination)) {
            throw new IllegalArgumentException("Given destination port does not exist!");
        }

        this.destinationPort = destination;
    }

    /**
     * Returns the destination port for the path
     */
    public String getDestination() {
        return destinationPort;
    }

    /**
     * Excludes a given port from consideration for the purposes of finding a path
     * 
     * @throws IllegalArgumentException If given port to exclude doesn't exist
     * @param port String name of the port to exclude
     */
    public void excludePort(String port) throws IllegalArgumentException {
        if(!portGraph.containsNode(port)) {
            throw new IllegalArgumentException("Requested port to exclude doesn't exist!");
        }

        portGraph.excludeNode(port);
    }

    /**
     * Includes a formerly excluded port, reenabling it for consideration for pathfinding
     * 
     * @throws IllegalArgumentException If given port to re-include doesn't exist
     * @param port String name of port to re-include
     */
    public void includePort(String port) throws IllegalArgumentException {
        if(!portGraph.containsNode(port)) {
            throw new IllegalArgumentException("Requested port to include doesn't exist!");
        }

        portGraph.includeNode(port);
    }

    /**
     * Returns a list of String names of all ports in the graph, or an empty list if there are none
     */
    public List<String> getPortList() {
        // There is no way to query the graph for a list of nodes, hence the instance variable
        return this.portList;
    }

    /**
     * Returns a list of all excluded ports, or an empty list if there are none
     */
    public List<String> getExcludedPortList() {
        return portGraph.getExcludedNodes();
    }

    /**
     * Returns a text String representation of the map of ports
     */
    public String getGraphString() {
        return portGraph.toString();
    }

    /**
     * Returns a List of ports on the way through the shortest path
     * 
     * @throws IllegalArgumentException If there is no valid path between the origin and the
     * destination
     * @return A List containing the String names of ports along the way of the shortest path,
     * in order from start to finish
     */
    public List<String> getPath() throws IllegalArgumentException {
        if(originPort == null || destinationPort == null) {
            throw new IllegalArgumentException("Origin or destination ports are unset!");
        }
        
        try {
            return portGraph.shortestPathData(originPort, destinationPort);
        } catch(NoSuchElementException e) {
            throw new IllegalArgumentException("No valid path between origin and destination!");
        }
    }

    /**
     * Returns a list of Doubles representing weights of each entry in the shortest path list.
     * The first Double represents the distance between the 1st and 2nd port, the second Double
     * the 2nd and 3rd, and so on.
     * 
     * @throws IllegalArgumentException If there is no valid path between the origin and the
     * destination
     * @returns A List containing the weights of ports on the path from origin to destination,
     * in order.
     */
    public List<Double> getWeights() throws IllegalArgumentException {
        List<String> shortestPortList = this.getPath();
        List<Double> weightList = new ArrayList<Double>();
        
        for(int i = 0; i < shortestPortList.size() - 1; i++) {
            weightList.add(portGraph.getEdge(shortestPortList.get(i), shortestPortList.get(i + 1)));
        }

        return weightList;
    }

    /**
     * Returns the total distance on the shortest path between the origin and destination
     * 
     * @throws IllegalArgumentException If there is no valid path between the origin and the
     * destination
     * @returns Integer representation of the shortest possible path, rounded up if neccessary
     */
    public int getDistance() throws IllegalArgumentException {
        if(originPort == null || destinationPort == null) {
            throw new IllegalArgumentException("Origin or destination ports are unset!");
        }
        
        try {
            return (int) Math.ceil(portGraph.shortestPathCost(originPort, destinationPort));
        } catch(NoSuchElementException e) {
            throw new IllegalArgumentException("No valid path between origin and destination!");
        }
    }
}
