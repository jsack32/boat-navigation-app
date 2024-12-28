import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

public class AlgorithmEngineerTests {

    /**
     * This test examines the constructor of an ExclusiveDijkstraGraph object,
     * as well as the new accessor methods, getEdgeLimit and getExcludedNodes,
     * ensuring that the edgeLimit value is set to a maximum double and that
     * the excluded nodes list is empty upon construction
     */
    @Test
    public void test1(){
	// Creates an ExclusiveDijkstraGraph object to use in testing
	ExclusiveDijkstraGraphAE testGraph = new ExclusiveDijkstraGraphAE();

	// No nodes are added to the constructed graph, asserts that size is 0
	assertTrue(testGraph.getNodeCount() == 0, "Graph was created with non-zero nodes!");

	// Asserts that the graph's edgeLimit variable was properly
	// initialized to the maximum value of a double
    	assertEquals(testGraph.getEdgeLimit(),Double.MAX_VALUE,"Edge Limit was not properly initialized");

	// Asserts that the graph's excluded nodes list was
	// initialized without any nodes in it
	assertTrue(testGraph.getExcludedNodes().size() == 0, "Excluded Nodes list was not initialized to a size of 0!");
    }
    
    /**
     * This test ensures that the setter methods, setEdgeLimit and excludeNode/includeNode
     * methods, all function properly by checking them against the accessor methods 
     */
    @Test
    public void test2(){
        // Creates an ExclusiveDijkstraGraaph object to use in testing
	ExclusiveDijkstraGraphAE<String,Integer> testGraph =
	new ExclusiveDijkstraGraphAE<String,Integer>();
	// Adds nodes and edges into the graph to be used in further testing
	testGraph.insertNode("A");
	testGraph.insertNode("B");
	testGraph.insertNode("D");
	testGraph.insertNode("E");
	testGraph.insertNode("F");
	testGraph.insertNode("G");
	testGraph.insertNode("H");
	testGraph.insertNode("I");
	testGraph.insertNode("L");
	testGraph.insertNode("M");
	testGraph.insertEdge("A","H",8);
	testGraph.insertEdge("A","B",1);
	testGraph.insertEdge("A","M",5);
	testGraph.insertEdge("B","M",3);
	testGraph.insertEdge("D","A",7);
	testGraph.insertEdge("D","G",2);
	testGraph.insertEdge("F","G",9);
	testGraph.insertEdge("G","L",7);
	testGraph.insertEdge("H","B",6);
	testGraph.insertEdge("H","I",2);
	testGraph.insertEdge("I","H",2);
	testGraph.insertEdge("I","D",1);
	testGraph.insertEdge("I","L",5);
	testGraph.insertEdge("M","E",3);
	testGraph.insertEdge("M","F",4);
    
        // Sets the edgeLimit variable to 7
	testGraph.setEdgeLimit(7);
	// Asserts that the edgeLimit was actually changed
	assertTrue(testGraph.getEdgeLimit() == 7,
	"Edge limit does not equal 7 after being set!");
    
	// Excludes multiple nodes from the graph
	testGraph.excludeNode("L");
	testGraph.excludeNode("M");
	testGraph.excludeNode("H");
        // Creates a string of the list of expected Nodes that 
	// will be in the excludedNodes list
	String expectedExclude = "[L, M, H]";
	// Asserts that expectedExclude is equal to the actual toString of getExcludedNodes
	assertEquals(expectedExclude,testGraph.getExcludedNodes().toString(),
	"The expected String did not match the return result!");
	// Asserts that the size of the list of excluded nodes is 3
	assertTrue(testGraph.getExcludedNodes().size() == 3,
	"The size of the excludedNodes list was not 3 after exclusions were made!");
   
	// Re-includes the M node into the graph
	testGraph.includeNode("M");
        // Creates a string of the list of expected Nodes that
        // will be in the excludedNodes list after reinclusion of M
        expectedExclude = "[L, H]";
        // Asserts that expectedExclude is equal to the actual toString of getExcludedNodes
        assertEquals(expectedExclude,testGraph.getExcludedNodes().toString(),
        "The expected String did not match the return result!");
        // Asserts that the size of the list of excluded nodes is 2
        assertTrue(testGraph.getExcludedNodes().size() == 2,
        "The size of the excludedNodes list was not 2 after a reinclusion was made!");


    }

    /**
     * This test ensures that exclude Nodes method functions properly by checking the
     * shortestPathCost and shortestPathData calculations against hand calculations
     * of the graph before and after exclusions are made. Additionally tests that
     * the pathing throws an exception when exclusion of enough nodes prevents
     * any path from being found. 
     */
    @Test
    public void test3(){
        // Creates an ExclusiveDijkstraGraaph object to use in testing
	ExclusiveDijkstraGraphAE<String,Integer> testGraph = 
	new ExclusiveDijkstraGraphAE<String,Integer>();
        // Adds nodes and edges into the graph to be used in further testing
        testGraph.insertNode("A");
        testGraph.insertNode("B");
        testGraph.insertNode("D");
        testGraph.insertNode("E");
        testGraph.insertNode("F");
        testGraph.insertNode("G");
        testGraph.insertNode("H");
        testGraph.insertNode("I");
        testGraph.insertNode("L");
        testGraph.insertNode("M");
        testGraph.insertEdge("A","H",8);
        testGraph.insertEdge("A","B",1);
        testGraph.insertEdge("A","M",5);
        testGraph.insertEdge("B","M",3);
        testGraph.insertEdge("D","A",7);
        testGraph.insertEdge("D","G",2);
        testGraph.insertEdge("F","G",9);
        testGraph.insertEdge("G","L",7);
        testGraph.insertEdge("H","B",6);
        testGraph.insertEdge("H","I",2);
        testGraph.insertEdge("I","H",2);
        testGraph.insertEdge("I","D",1);
        testGraph.insertEdge("I","L",5);
        testGraph.insertEdge("M","E",3);
        testGraph.insertEdge("M","F",4);
    
    	// Creates a string to hold the expected result of the pathing from I to G
	// before exclusions are made
	String expectedPath = "[I, D, G]";
	// Asserts that the shortestPathData toString matches what is expected
	assertEquals(testGraph.shortestPathData("I","G").toString(),expectedPath,
	"The expected path data for basic I to G pathing did not match actual!");
    	// Asserts that the shortestPathCost matches the expected path cost
	assertTrue(testGraph.shortestPathCost("I","G") == 3.0,
	"The expected shortest path cost of basic I to G pathing did not match actual!");

	// Excludes D from the graph
	testGraph.excludeNode("D");

        // Creates a string to hold the expected result of the pathing from I to G
        // after exclusions are made
        expectedPath = "[I, H, B, M, F, G]";
        // Asserts that the shortestPathData toString matches what is expected
        assertEquals(testGraph.shortestPathData("I","G").toString(),expectedPath,
        "The expected path data for exclusion I to G pathing did not match actual!");
        // Asserts that the shortestPathCost matches the expected path cost
        assertEquals(testGraph.shortestPathCost("I","G"), 24,
        "The expected shortest path cost of exclusion I to G pathing did not match actual!");

        // Excludes H from the graph
        testGraph.excludeNode("H");

        // Asserts that the shortestPathData method throws Exception after excluding D and G
        assertThrows(NoSuchElementException.class, () -> testGraph.computeShortestPath("I","G"),
        "The I to G pathing did not throw any exception when supposed to!");
    }

    /**
     * Tests the functionality of the setEdgeLimits method by checking 
     * hand calculated values against the computed values before and after
     * setting edge limits. Additionally checks that when edge limit is set lower
     * than all edges in a given path, an exception is thrown
     */
    @Test
    public void test4(){
        // Creates an ExclusiveDijkstraGraaph object to use in testing
        ExclusiveDijkstraGraphAE<String,Integer> testGraph =
        new ExclusiveDijkstraGraphAE<String,Integer>();
        // Adds nodes and edges into the graph to be used in further testing
        testGraph.insertNode("A");
        testGraph.insertNode("B");
        testGraph.insertNode("D");
        testGraph.insertNode("E");
        testGraph.insertNode("F");
        testGraph.insertNode("G");
        testGraph.insertNode("H");
        testGraph.insertNode("I");
        testGraph.insertNode("L");
        testGraph.insertNode("M");
        testGraph.insertEdge("A","H",8);
        testGraph.insertEdge("A","B",1);
        testGraph.insertEdge("A","M",5);
        testGraph.insertEdge("B","M",3);
        testGraph.insertEdge("D","A",7);
        testGraph.insertEdge("D","G",2);
        testGraph.insertEdge("F","G",9);
        testGraph.insertEdge("G","L",7);
        testGraph.insertEdge("H","B",6);
        testGraph.insertEdge("H","I",2);
        // Changed line below from prior testers for purposes of this test
	testGraph.insertEdge("I","H",9);
        testGraph.insertEdge("I","D",1);
        testGraph.insertEdge("I","L",5);
        testGraph.insertEdge("M","E",3);
        testGraph.insertEdge("M","F",4);
    
        // Creates a string to hold the expected result of the pathing from I to H
        // before edge limit is set
        String expectedPath = "[I, H]";
        // Asserts that the shortestPathData toString matches what is expected
        assertEquals(testGraph.shortestPathData("I","H").toString(),expectedPath,
        "The expected path data for basic I to H pathing did not match actual!");
        // Asserts that the shortestPathCost matches the expected path cost
        assertTrue(testGraph.shortestPathCost("I","H") == 9.0,
        "The expected shortest path cost of basic I to H pathing did not match actual!");

        // Sets edge limmit to 9
        testGraph.setEdgeLimit(9);

        // Creates a string to hold the expected result of the pathing from I to H
        // after edgeLimit is set
        expectedPath = "[I, D, A, H]";
        // Asserts that the shortestPathData toString matches what is expected
        assertEquals(testGraph.shortestPathData("I","H").toString(),expectedPath,
        "The expected path data for limited I to H pathing did not match actual!");
        // Asserts that the shortestPathCost matches the expected path cost
        assertEquals(testGraph.shortestPathCost("I","H"), 16,
        "The expected shortest path cost of limited I to H pathing did not match actual!");

        // Sets edge limit down to 1
        testGraph.setEdgeLimit(1);

        // Asserts that the shortestPathData method throws Exception after setting edge limit to 1
        assertThrows(NoSuchElementException.class, () -> testGraph.computeShortestPath("I","H"),
        "The I to H pathing did not throw any exception when supposed to!");

    }

    /**
     * Tests the functionality of combining both the setEdgeLimits method 
     * and the excludeNode method by checking
     * hand calculated values against the computed values before and after
     * excluding nodes and setting edge limit values
     */
    @Test
    public void test5(){
        // Creates an ExclusiveDijkstraGraaph object to use in testing
        ExclusiveDijkstraGraphAE<String,Integer> testGraph =
        new ExclusiveDijkstraGraphAE<String,Integer>();
        // Adds nodes and edges into the graph to be used in further testing
        testGraph.insertNode("A");
        testGraph.insertNode("B");
        testGraph.insertNode("D");
        testGraph.insertNode("E");
        testGraph.insertNode("F");
        testGraph.insertNode("G");
        testGraph.insertNode("H");
        testGraph.insertNode("I");
        testGraph.insertNode("L");
        testGraph.insertNode("M");
        testGraph.insertEdge("A","H",8);
        testGraph.insertEdge("A","B",1);
        testGraph.insertEdge("A","M",5);
        testGraph.insertEdge("B","M",3);
        testGraph.insertEdge("D","A",7);
        testGraph.insertEdge("D","G",2);
        testGraph.insertEdge("F","G",9);
        // Changed line below from tester 1-4 for purposes of this test
	testGraph.insertEdge("G","L",10);
        testGraph.insertEdge("H","B",6);
        testGraph.insertEdge("H","I",2);
        // Changed line below from testers 1-3 for purposes of this test
        testGraph.insertEdge("I","H",9);
        testGraph.insertEdge("I","D",1);
        testGraph.insertEdge("I","L",5);
        testGraph.insertEdge("M","E",3);
        testGraph.insertEdge("M","F",4);
	// Edges below is added for testing purposes to this method only
	testGraph.insertEdge("D","B",8);
	testGraph.insertEdge("B","H",8);

        // Creates a string to hold the expected result of the pathing from I to H
        // before edge limit is set or node is excluded
        String expectedPath = "[I, H]";
        // Asserts that the shortestPathData toString matches what is expected
        assertEquals(testGraph.shortestPathData("I","H").toString(),expectedPath,
        "The expected path data for basic I to H pathing did not match actual!");
        // Asserts that the shortestPathCost matches the expected path cost
        assertTrue(testGraph.shortestPathCost("I","H") == 9.0,
        "The expected shortest path cost of basic I to H pathing did not match actual!");

        // Sets edge limmit to 9
        testGraph.setEdgeLimit(9);

        // Creates a string to hold the expected result of the pathing from I to H
        // after edgeLimit is set
        expectedPath = "[I, D, A, H]";
        // Asserts that the shortestPathData toString matches what is expected
        assertEquals(testGraph.shortestPathData("I","H").toString(),expectedPath,
        "The expected path data for limited I to H pathing did not match actual!");
        // Asserts that the shortestPathCost matches the expected path cost
        assertEquals(testGraph.shortestPathCost("I","H"), 16,
        "The expected shortest path cost of limited I to H pathing did not match actual!");

        // Excludes node A to force pathing through B
        testGraph.excludeNode("A");

        // Creates a string to hold the expected result of the pathing from I to H
        // after edgeLimit is set and A is excluded
        expectedPath = "[I, D, B, H]";
        // Asserts that the shortestPathData toString matches what is expected
        assertEquals(testGraph.shortestPathData("I","H").toString(),expectedPath,
        "The expected path data for limited I to H pathing did not match actual!");
        // Asserts that the shortestPathCost matches the expected path cost
        assertEquals(testGraph.shortestPathCost("I","H"), 17,
        "The expected shortest path cost of limited I to H pathing did not match actual!");


	// Excludes B as well
	testGraph.excludeNode("B");

        // Asserts that the shortestPathData method throws Exception after setting edge limit to 1
        assertThrows(NoSuchElementException.class, () -> testGraph.computeShortestPath("I","H"),
        "The I to H pathing did not throw any exception when supposed to!");
    }	

    /**
     * This test ensures that the Backend method loadGraphFromFile is compatible
     * with ExclusiveDijkstraGraph in that all nodes and edges are properly loaded.
     * It does so by comparing the expected number of nodes in the graph, and the toString
     * of the graph to the actual. This demonstrates integration between the DW's formatting,
     * BD's load method, and AE's ability to insert edges and nodes properly.
     */
    @Test
    public void integrationTest1(){
	// Creates a Backend object for use in testing compatibility between AE and BD
    	NavigationAppBackendBD testBackend = new NavigationAppBackendBD
		(new ExclusiveDijkstraGraphAE<String,Double>());

	// Attempts to load  the seampa.dot file into the graph
	try{
	    testBackend.loadGraphFromFile("seamap.dot");
	} catch (Exception e){
	}
	
	// Creates an int of the expected size of the graph (in nodes) if the file was properly
	// loaded into the graph
	int expectedSize = 13;

	// Asserts that the Backend's list of Nodes in the graph is equal to the expected amount
	assertTrue(expectedSize == testBackend.getPortList().size(),
			"The expected size of the nodes in the graph did not match actual size");	

	// Creates a String that represents the expected toString of the graph if it
	// was properly loaded from the seamap.dot file
	String expectedToString = 
	"------------------------------------------\n\n"

	+"Graph:\n\n"

	+"Tokyo with connections:\n"
	+"Tokyo -> Hong Kong with cost: 1867.1\n"
	+"Tokyo -> Sydney with cost: 5000.1\n"
	+"Tokyo -> Los Angeles with cost: 4979.1\n\n"

	+"New York City with connections:\n"
	+"New York City -> Lisbon with cost: 2932.5\n"
	+"New York City -> Miami with cost: 972.9\n"
	+"New York City -> Dublin with cost: 3197.0\n\n"

	+"Dublin with connections:\n"
	+"Dublin -> Lisbon with cost: 932.7\n"
	+"Dublin -> New York City with cost: 3042.1\n\n"

	+"Tel Aviv with connections:\n"
	+"Tel Aviv -> Athens with cost: 1501.0\n\n"

	+"Lisbon with connections:\n"
	+"Lisbon -> Rome with cost: 1347.4\n"
	+"Lisbon -> Miami with cost: 3587.4\n\n"

	+"Athens with connections:\n"
	+"Athens -> Rome with cost: 998.4\n"
	+"Athens -> Dubai with cost: 4150.7\n"
	+"Athens -> Hong Kong with cost: 10176.9\n\n"

	+"Sydney with connections:\n"
	+"Sydney -> Dubai with cost: 7904.0\n"
	+"Sydney -> Tokyo with cost: 4892.0\n\n"

	+"Rome with connections:\n"
	+"Rome -> Lisbon with cost: 1353.0\n"
	+"Rome -> Athens with cost: 1010.2\n"
	+"Rome -> Tel Aviv with cost: 1480.1\n\n"

	+"Miami with connections:\n"
	+"Miami -> New York City with cost: 956.4\n"
	+"Miami -> Los Angeles with cost: 4523.9\n\n"

	+"Hong Kong with connections:\n"
	+"Hong Kong -> Dubai with cost: 7854.2\n"
	+"Hong Kong -> Tokyo with cost: 1810.5\n\n"

	+"Dubai with connections:\n"
	+"Dubai -> Hong Kong with cost: 7777.7\n"
	+"Dubai -> Rome with cost: 4524.8\n\n"
	
	+"Los Angeles with connections:\n"
	+"Los Angeles -> Tokyo with cost: 5012.0\n"
	+"Los Angeles -> Honolulu with cost: 2620.1\n"
	+"Los Angeles -> Miami with cost: 4413.4\n\n"

	+"Honolulu with connections:\n"
	+"Honolulu -> Sydney with cost: 4555.4\n\n"

	+"------------------------------------------";
	
	// Asserts that the nodes and their edges were properly loaded by checking the toString against
	// an expected String of what it should be
	assertEquals(expectedToString,testBackend.getGraphString(),testBackend.getGraphString());
    
    }

    /**
     * Tests the functionality of the excludeNode feature of the ExclusiveDijkstraGraph
     * when nodes are being excluded through backend methods. It further tests the
     * accessor methods from the Backend class that reference accessor methods in
     * the AE class
     */
    @Test
    public void integrationTest2(){
        // Creates a Backend object for use in testing compatibility between AE and BD
        NavigationAppBackendBD testBackend = new NavigationAppBackendBD
                (new ExclusiveDijkstraGraphAE<String,Double>());

        // Attempts to load  the seampa.dot file into the graph
        try{
            testBackend.loadGraphFromFile("seamap.dot");
        } catch (Exception e){
        }
	
	// Sets the origin of the backend to Los Angeles
	testBackend.setOrigin("Los Angeles");

	// Sets the destination to Sydney
	testBackend.setDestination("Sydney");

	// Creates a String to represent the expected toString of the getPath method in the backend
	String expectedPath = "[Los Angeles, Honolulu, Sydney]";

	// Asserts that the expected toString matches the actual toString of the getPath method
	assertEquals(testBackend.getPath().toString(),expectedPath,
			"The expected (non-exclusionary) path did not match the actual!");

	// Excludes Honolulu from the graph
	testBackend.excludePort("Honolulu");

	// Updates expected path with a path to Sydney not including Honolulu
	expectedPath = "[Los Angeles, Tokyo, Sydney]";

	// Asserts that the updated expected toString of getPath matches the actual after exclusion
	 assertEquals(testBackend.getPath().toString(),expectedPath,
                        "The expected (exclusionary) path did not match the actual!");
	
    }

    /**
     * Tests the ability of the Data Wrangler's class to skip over lines that
     * are formatted incorrectly (i.e. do not contain an arrow) 
     */
    @Test
    public void CodeReviewOfDataWrangler1(){
	// Creates a PortDW object for use in testing
    	PortDotDW testDW = new PortDotDW();
	
	// Creates a list of String arrays to hold the output from the getPortMap method
	List<String[]> portList = new ArrayList<String[]>();

	// Attempts to load the DWTest.dot file as a list into the created portList variable
	try{
	    portList = testDW.getPortMap("DWTest.dot");	
	}catch(FileNotFoundException e){
	
	}

	// If properly loaded, the file with three lines should have only put two into the portList
	// Asserts that the size of the list is 2
	assertTrue(portList.size() == 2);
	
	// Asserts that the first item in the list is the first item in the dot file
	assertEquals(portList.get(0)[0].toString(),"Miami");	

	// Asserts that the second item in the list is the third item in the dot file
	// meaning that the second was skipped due to improper formattig
	assertEquals(portList.get(1)[0].toString(),"Sydney");

    }

    /**
     * Tests the PortDotDW class when the format of string inputs are switched
     * to an invlad integer input. This should throw an IndexOutOfBounds exception
     * according to the behavior of the DW's class
     */
    @Test
    public void CodeReviewOfDataWrangler2(){
        // Creates a PortDW object for use in testing
        PortDotDW testDW = new PortDotDW();

        // Creates a list of String arrays to hold the output from the getPortMap method
        List<String[]> portList = new ArrayList<String[]>();

	// Creates a boolean to be triggered if the proper exception is thrown
	boolean testPassed = false;

        // Attempts to load the invalidFormat.dot file as a list into the created portList variable
	// Expects an OutOfBoundsException to be thrown
        try{
	    try{
                portList = testDW.getPortMap("invalidFormat.dot");
       	    } catch(FileNotFoundException i){
	    
	    }
	}catch(IndexOutOfBoundsException e){
	    testPassed = true;
        }

	// Asserts that the exception above was actually thrown
	assertTrue(testPassed,
		   "An IndexOutOfBoundsException was not thrown when invalid formatting is given!");
	
    }


}

