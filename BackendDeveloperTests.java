// --== CS400 File Header Information ==--
// Name: Alice Shelton
// Email: rashelton@wisc.edu
// Group and Team: BT Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class BackendDeveloperTests {
    /**
     * Tests simple getter / setter methods within backend methods to ensure they work as intended
     */
    @Test
    public void testGettersSetters() {
        NavigationAppBackendBD testBackend = 
            new NavigationAppBackendBD(new ExclusiveDijkstraGraphBD<String, Double>());

        try {
            testBackend.loadGraphFromFile("realFile.txt");
        } catch(Exception e) {
            Assertions.fail();
        }

        Assertions.assertEquals(null, testBackend.getOrigin());
        Assertions.assertEquals(null, testBackend.getDestination());

        testBackend.setOrigin("B");
        Assertions.assertEquals("B", testBackend.getOrigin());
        
        testBackend.setDestination("F");
        Assertions.assertEquals("F", testBackend.getDestination());

        testBackend.changeBoatRange(17);
        Assertions.assertEquals(17, testBackend.getBoatRange());

        List<String> portList = testBackend.getPortList();
        Assertions.assertEquals(10, portList.size());

        boolean testNodeFound = false;
        for(String s : portList) {
            if(s.equals("E")) {
                testNodeFound = true;
            }
        }

        Assertions.assertEquals(true, testNodeFound);
    }

    /**
     * Tests that getPath returns the the expected path when not excluding ports
     */
    @Test
    public void testGetPath() {
        NavigationAppBackendBD testBackend = 
            new NavigationAppBackendBD(new ExclusiveDijkstraGraphBD<String, Double>());

        try {
            testBackend.loadGraphFromFile("realFile.txt");
        } catch(Exception e) {
            Assertions.fail();
        }

        testBackend.setOrigin("A");
        testBackend.setDestination("M");

        List<String> portPath = testBackend.getPath();

        Assertions.assertEquals(2, portPath.size());
        Assertions.assertEquals("A", portPath.get(0));
        Assertions.assertEquals("M", portPath.get(1));

        // Test where a change in range would affect results
        testBackend.changeBoatRange(4);

        portPath = testBackend.getPath();
        Assertions.assertEquals(3, portPath.size());
        Assertions.assertEquals("A", portPath.get(0));
        Assertions.assertEquals("B", portPath.get(1));
        Assertions.assertEquals("M", portPath.get(2));
    }

    /**
     * Tests that getWeights returns the expected path when not excluding ports
     */
    @Test
    public void testGetWeights() {
        NavigationAppBackendBD testBackend = 
            new NavigationAppBackendBD(new ExclusiveDijkstraGraphBD<String, Double>());

        try {
            testBackend.loadGraphFromFile("realFile.txt");
        } catch(Exception e) {
            Assertions.fail();
        }

        testBackend.setOrigin("A");
        testBackend.setDestination("M");

        List<Double> portWeights = testBackend.getWeights();

        Assertions.assertEquals(1, portWeights.size());
        Assertions.assertEquals(5, portWeights.get(0));

        // Test where a change in range would affect results
        testBackend.changeBoatRange(4);

        portWeights = testBackend.getWeights();
        Assertions.assertEquals(2, portWeights.size());
        Assertions.assertEquals(1, portWeights.get(0));
        Assertions.assertEquals(3, portWeights.get(1));
    }

    /**
     * Tests that path and weight methods return correctly when excluding ports
     */
    @Test
    public void testExcludePorts() {
        NavigationAppBackendBD testBackend = 
            new NavigationAppBackendBD(new ExclusiveDijkstraGraphBD<String, Double>());

        try {
            testBackend.loadGraphFromFile("realFile.txt");
        } catch(Exception e) {
            Assertions.fail();
        }

        List<String> excludedList = testBackend.getExcludedPortList();
        Assertions.assertEquals(0, excludedList.size());

        // Check a path that would be modified by port exclusion
        testBackend.excludePort("F");
        excludedList = testBackend.getExcludedPortList();

        Assertions.assertEquals(1, excludedList.size());
        Assertions.assertEquals("F", excludedList.get(0));

        testBackend.setOrigin("M");
        testBackend.setDestination("G");

        List<Double> weightList = testBackend.getWeights();
        Assertions.assertEquals(4, weightList.size());
        Assertions.assertEquals(2, weightList.get(3));
    }

    /**
     * Tests backend methods that are supposed to throw exceptions to ensure they do throw
     * exceptions as specified.
     */
    @Test
    public void testExceptions() {
        ExclusiveDijkstraGraphBD<String, Double> testGraph = 
            new ExclusiveDijkstraGraphBD<String, Double>();

        NavigationAppBackendBD testBackend = new NavigationAppBackendBD(testGraph);

        try {
            testBackend.loadGraphFromFile("doesntExist.dot");
            Assertions.fail();
        } catch(FileNotFoundException e) {
            // Expected
        } catch(Exception e) {
            Assertions.fail();
        }

        try {
            testBackend.changeBoatRange(-5);
            Assertions.fail();
        } catch(IllegalArgumentException e) {
            // Expected
        } catch(Exception e) {
            Assertions.fail();
        }

        try {
            testBackend.setOrigin("A");
            Assertions.fail();
        } catch(IllegalArgumentException e) {
            // Expected
        } catch(Exception e) {
            Assertions.fail();
        }

        try {
            testBackend.setDestination("A");;
            Assertions.fail();
        } catch(IllegalArgumentException e) {
            // Expected
        } catch(Exception e) {
            Assertions.fail();
        }

        try {
            testBackend.excludePort("A");
            Assertions.fail();
        } catch(IllegalArgumentException e) {
            // Expected
        } catch(Exception e) {
            Assertions.fail();
        }

        try {
            testBackend.getPath();
            Assertions.fail();
        } catch(IllegalArgumentException e) {
            // Expected
        } catch(Exception e) {
            Assertions.fail();
        }
    }
}
