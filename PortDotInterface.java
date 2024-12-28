import java.io.FileNotFoundException;
import java.util.List;

/**
 * Takes a DOT file as input containing each port and their weighted departure distances to other
 * ports and returns a linked list of String arrays of each port and the travel distance from each
 * port to their destination as edge weights. Element 0 of each String array contains the source
 * port, element 1 contains the destination port, and element 2 contains a string representation of
 * the double edge weight.
 */
public interface PortDotInterface {
    // public PortDotInterface();
    public List<String[]> getPortMap(String filename) throws FileNotFoundException;
}
