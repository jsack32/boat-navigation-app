// --== CS400 File Header Information ==--
// Name: Alice Shelton
// Email: rashelton@wisc.edu
// Group and Team: BT Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

/**
* Takes a DOT file as input containing each port and their weighted departure distances to other
* ports and returns a complete graph of all ports and the travel distance between them.
*/
public class PortDotBD implements PortDotInterface {
    // public PortDotInterface();
    public List<String[]> getPortMap(String filename) throws FileNotFoundException {
        if(filename.equals("doesntExist.dot")) {
            throw new FileNotFoundException();
        }

        List<String[]> portList = new ArrayList<String[]>();

        portList.add(new String[] {"A", "B", "1"});
        portList.add(new String[] {"A", "H", "8"});
        portList.add(new String[] {"A", "M", "5"});
        portList.add(new String[] {"A", "D", "7"});
        portList.add(new String[] {"B", "M", "3"});
        portList.add(new String[] {"B", "H", "6"});
        portList.add(new String[] {"I", "H", "2"});
        portList.add(new String[] {"I", "D", "1"});
        portList.add(new String[] {"I", "L", "5"});
        portList.add(new String[] {"G", "L", "7"});
        portList.add(new String[] {"G", "D", "2"});
        portList.add(new String[] {"G", "F", "9"});
        portList.add(new String[] {"M", "E", "3"});

        int currentLength = portList.size();

        // Add reverse directions too
        for(int i = 0; i < currentLength; i++) {
            // Actually the most disgusting code written in my life
            portList.add(new String[] {portList.get(i)[1], portList.get(i)[0], portList.get(i)[2]});
        }

        return portList;
    }
}
