// --== CS400 File Header Information ==--
// Name: Jesse Sack
// Email: jrsack@wisc.edu
// Group and Team: BT Red
// Group TA: Sam Church
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

public class DataWranglerTests {
    /**
     * Tests that the first edge in the file is correctly placed into the LinkedList
     * returned by getPortMap.
     */
    @Test
    public void test1() throws FileNotFoundException {
        PortDotInterface dataWrangler = new PortDotDW();
        List<String[]> edgeList = dataWrangler.getPortMap("seamap.dot");

        Assertions.assertEquals(edgeList.get(0)[0], "Miami");
        Assertions.assertEquals(edgeList.get(0)[1], "New York City");
        Assertions.assertEquals(Double.parseDouble(edgeList.get(0)[2]), 956.4);
    }

    /**
     * Tests that the second edge in the file is correctly placed into the LinkedList
     * returned by getPortMap.
     */
    @Test
    public void test2() throws FileNotFoundException {
        PortDotInterface dataWrangler = new PortDotDW();
        List<String[]> edgeList = dataWrangler.getPortMap("seamap.dot");

        Assertions.assertEquals(edgeList.get(1)[0], "New York City");
        Assertions.assertEquals(edgeList.get(1)[1], "Lisbon");
        Assertions.assertEquals(Double.parseDouble(edgeList.get(1)[2]), 2932.5);
    }

    /**
     * Tests that the last edge in the file is correctly placed into the LinkedList
     * returned by getPortMap.
     */
    @Test
    public void test3() throws FileNotFoundException {
        PortDotInterface dataWrangler = new PortDotDW();
        List<String[]> edgeList = dataWrangler.getPortMap("seamap.dot");

        Assertions.assertEquals(edgeList.get(28)[0], "Sydney");
        Assertions.assertEquals(edgeList.get(28)[1], "Tokyo");
        Assertions.assertEquals(Double.parseDouble(edgeList.get(28)[2]), 4892.0);
    }

    /**
     * Tests that the LinkedList returned by getPortMap is the correct size (contains
     * the correct number of edges).
     */
    @Test
    public void test4() throws FileNotFoundException {
        PortDotInterface dataWrangler = new PortDotDW();
        List<String[]> edgeList = dataWrangler.getPortMap("seamap.dot");

        Assertions.assertEquals(edgeList.size(), 29);
    }

    /**
     * Tests that getPortMap throws a FileNotFoundException when passed a filename that
     * doesn't exist in the project folder.
     */
    @Test
    public void test5() {
        PortDotInterface dataWrangler = new PortDotDW();
        boolean passTest = false;   // only set to true if the exception is caught
        try {
            List<String[]> edgeList = dataWrangler.getPortMap("falsename.txt");
        } catch(FileNotFoundException ex) {
            passTest = true;
        }

        Assertions.assertTrue(passTest);
    }

    /**
     * Tests excludeNode, includeNode, and getExcludedNodes works as intended.
     */
    @Test
    public void codeReviewOfAlgorithmEngineerTest1() {
        ExclusiveDijkstraGraphInterface<Character, Integer> graph = new ExclusiveDijkstraGraphAE<Character, Integer>();

        // insert nodes
        for (char c = 'A'; c <= 'M'; c++) {
            // nodes to skip
            if (c == 'C' || c == 'J' || c == 'K') {
                continue;
            }
            graph.insertNode(c);
        }

        // insert edges
        graph.insertEdge('A', 'B', 1);
        graph.insertEdge('A', 'H', 8);
        graph.insertEdge('A', 'M', 5);
        graph.insertEdge('B', 'M', 3);
        graph.insertEdge('D', 'A', 7);
        graph.insertEdge('D', 'G', 2);
        graph.insertEdge('F', 'G', 9);
        graph.insertEdge('G', 'L', 7);
        graph.insertEdge('H', 'B', 6);
        graph.insertEdge('H', 'I', 2);
        graph.insertEdge('I', 'D', 1);
        graph.insertEdge('I', 'H', 2);
        graph.insertEdge('I', 'L', 5);
        graph.insertEdge('M', 'E', 3);
        graph.insertEdge('M', 'F', 4);

        Assertions.assertTrue(!graph.includeNode('A')); // can't include non-excluded node returns false
        Assertions.assertTrue(graph.excludeNode('A'));  // exclude A should return true
        Assertions.assertTrue(!graph.excludeNode('A')); // can't exclude an excluded node
        Assertions.assertTrue(graph.includeNode('A'));      // can include an excluded node

        // tests that excluding a node adds it to the exclude node list
        graph.excludeNode('F');
        graph.excludeNode('M');
        List<Character> excluded = graph.getExcludedNodes();    // check that the list is otherwise empty (no A)
        boolean pass = excluded.contains('F') && excluded.contains('M') && excluded.size() == 2;
        Assertions.assertTrue(pass);
    }

    /**
     * Tests shortestPathData with excluded nodes.
     */
    @Test
    public void codeReviewOfAlgorithmEngineerTest2() {
        ExclusiveDijkstraGraphInterface<Character, Integer> graph = new ExclusiveDijkstraGraphAE<Character, Integer>();

        // insert nodes
        for (char c = 'A'; c <= 'M'; c++) {
            // nodes to skip
            if (c == 'C' || c == 'J' || c == 'K') {
                continue;
            }
            graph.insertNode(c);
        }

        // insert edges
        graph.insertEdge('A', 'B', 1);
        graph.insertEdge('A', 'H', 8);
        graph.insertEdge('A', 'M', 5);
        graph.insertEdge('B', 'M', 3);
        graph.insertEdge('D', 'A', 7);
        graph.insertEdge('D', 'G', 2);
        graph.insertEdge('F', 'G', 9);
        graph.insertEdge('G', 'L', 7);
        graph.insertEdge('H', 'B', 6);
        graph.insertEdge('H', 'I', 2);
        graph.insertEdge('I', 'D', 1);
        graph.insertEdge('I', 'H', 2);
        graph.insertEdge('I', 'L', 5);
        graph.insertEdge('M', 'E', 3);
        graph.insertEdge('M', 'F', 4);

        // excluding I should block off all paths
        graph.excludeNode('I');
        boolean pass = false;
        try {
            graph.shortestPathData('D', 'I');
        } catch (NoSuchElementException ex) {
            pass = true;
        }
        Assertions.assertTrue(pass);

        // path from D to M should be D->A->B->M, but should be D->A->M when excluding B
        graph.excludeNode('B');
        Assertions.assertEquals(graph.shortestPathData('D', 'M').toString(), "[D, A, M]");
        Assertions.assertEquals(graph.shortestPathCost('D', 'M'), 12.0);
        graph.includeNode('B'); // try re-including B
        Assertions.assertEquals(graph.shortestPathData('D', 'M').toString(), "[D, A, B, M]");
        Assertions.assertEquals(graph.shortestPathCost('D', 'M'), 11.0);
    }

    /**
     * Tests that loading the "seamap.dot" file and calling getPortList() in the backend returns a list
     * of size 13 (13 ports).
     */
    @Test
    public void integrationTest1() {
        ExclusiveDijkstraGraphInterface<String, Double> graph = new ExclusiveDijkstraGraphAE<String, Double>();
        NavigationAppBackendBD backend = new NavigationAppBackendBD(graph);
        boolean pass = true;
        // load the file
        try {
            backend.loadGraphFromFile("seamap.dot");
        }
        catch (Exception ex) {
            pass = false;
        }
        Assertions.assertTrue(pass);    // test that no exception thrown

        Assertions.assertEquals(13, backend.getPortList().size());
    }

    /**
     * Tests that the backend correctly computes the shortest path from Dubai to NYC
     */
    @Test
    public void integrationTest2() {
        ExclusiveDijkstraGraphInterface<String, Double> graph = new ExclusiveDijkstraGraphAE<String, Double>();
        NavigationAppBackendBD backend = new NavigationAppBackendBD(graph);
        // load the file
        try {
            backend.loadGraphFromFile("seamap.dot");
        }
        catch (Exception ex) {
        }

        // set origin and destination
        backend.setDestination("New York City");
        backend.setOrigin("Dubai");
        backend.changeBoatRange(10000);

        // test that getDistance returns the expected value
        Assertions.assertEquals(10422, backend.getDistance());
        Assertions.assertEquals("[Dubai, Rome, Lisbon, Miami, New York City]", backend.getPath().toString());
    }
}
