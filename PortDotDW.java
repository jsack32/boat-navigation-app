// --== CS400 File Header Information ==--
// Name: Jesse Sack
// Email: jrsack@wisc.edu
// Group and Team: BT Red
// Group TA: Sam Church
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.List;
import java.util.LinkedList;

/**
 * Takes a DOT file as input containing each port and their weighted departure distances to other
 * ports and returns a complete graph of all ports and the travel distance between them.
 */

public class PortDotDW implements PortDotInterface {
    /**
     * Converts a DOT file to a list of String arrays. Element 0 of each array is the
     * source port, element 1 is the destination port, and element 2 is the String
     * representation of the Double edge weight (must be parsed to a double).
     *
     * @param filename - the file to import the graph attributes from
     * @return a List of String arrays containing information for each graph edge
     * @throws FileNotFoundException when the filename parameter doesn't map to a dot file
     */
    @Override
    public List<String[]> getPortMap(String filename) throws FileNotFoundException {
        // create a scanner to scan the given file; throws exception when file isn't found
        Scanner reader = new Scanner(new File(filename));

        // create the dijkstra graph
        List<String[]> seaMap = new LinkedList<String[]>();

        // loop through each line in the file
        while(reader.hasNext()) {
            String line = reader.nextLine();    // grab line

            // if the line doesn't contain "->" it is not an edge, so ignore line
            if (!line.contains("->")) continue;

            // split the String into its components
            String[] edgeData = line.split("\"");   // split by quote
            String[] currentEdge = new String[3];

            // extract nodes and edge weight
            // edgeData[0] is an empty string
            currentEdge[0] = edgeData[1].trim();
            // edgeData[2] is " -> "
            currentEdge[1] = edgeData[3].trim();
            // edgeData[4] is " ["
            currentEdge[2] = edgeData[5].trim(); // parse weight to double
            // the rest of the elements in edgeData are not relevant

            seaMap.add(currentEdge);    // add the edge to the list of edges to return
        }

        return seaMap;
    }
}
