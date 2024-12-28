// --== CS400 File Header Information ==--
// Name: Luke Steimle
// Email: lsteimle@wisc.edu
// Group and Team: BT Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: n/a

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class FrontendDeveloperTests {
	
	/**
	 * This JUnit test confirms that the port options list is displayed properly
	 */
	@Test
	void testPortListDisplyed() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphFD<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendFD(graph);
		
		// simulate selecting the first port in the list
		String data = "a\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		
		// set printstream output to a new stream and setup the frontend
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
		
		// prompt the user to select from the port list
		frontend.displayPortOptions();
		
		// confirm that the application returned one of the ports from the list
		if (!outputStreamCaptor.toString().contains("Miami")) {
			fail();
		}
	}
	
	/**
	 * This JUnit test confirms that the excluded port options list is displayed properly
	 */
	@Test
	void testExcludedPortListDisplyed() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphFD<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendFD(graph);
		
		// simulate selecting the first port in the list
		String data = "a\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		
		// set printstream output to a new stream and setup the frontend
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
		
		// prompt the user to select from the port list
		frontend.displayUnexcludePortOptions();
		
		// confirm that the application returned one of the ports from the list
		if (!outputStreamCaptor.toString().contains("Bahamas")) {
			fail();
		}
	}
	
	/**
	 * This JUnit test confirms that the lists reset after one use of the app so that a second user has a fresh start
	 */
	@Test
	void testRunAppTwice() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphFD<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendFD(graph);
		
		// simulate going through the whole app once, then selecting the first port from a fresh list
		String data = "1\na\na\n2\nn\n1\n3\na\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		
		// set printstream output to a new stream and setup the frontend
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
		
		// run through the app and select "Miami" as the starting port
		frontend.runCommandLoop();
		
		// clear the output and re-prompt the user to select from the port list
		outputStreamCaptor.reset();
		frontend.displayPortOptions();
		
		// confirm that the application returned a port list that still contains the previously selected port
		if (!outputStreamCaptor.toString().contains("Miami")) {
			fail();
		}
	}
	
	/**
	 * This JUnit test confirms that the available port list no longer contains a port after it has been selected
	 */
	@Test
	void testPortRemovedFromList() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphFD<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendFD(graph);
		
		// simulate running the whole app once, where the first ports in the list are selected for the starting and
		// destination ports
		String data = "1\na\na\n2\nn\n1\n3\na\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		
		// set printstream output to a new stream and setup the frontend
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
		
		// run through the app and select "Miami" as the starting port
		frontend.runCommandLoop();
		
		// confirm that the first port in the list changed from "Miami" to "NYC" after "Miami" was selected as
		// the starting port
		if (!outputStreamCaptor.toString().contains("a) Miami") || 
				!outputStreamCaptor.toString().contains("a) NYC")) {
			fail();
		}
	}
	
	/**
	 * This JUnit test confirms that the displayShortestPath method shows each step in the shortest path with 
	 * weights for each step
	 */
	@Test
	void testDisplayShortestPath() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphFD<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendFD(graph);
		
		// set printstream output to a new stream and setup the frontend
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
		
		// display the shortest path
		frontend.displayShortestPath();
		
		// confirm the output displays the ports in order with the weights
		if (!outputStreamCaptor.toString().contains("Miami --> Charleston (10.0)") ||
				!outputStreamCaptor.toString().contains("Charleston --> NYC (22.1)")) {
			fail();
		}
	}
	
	/**
	 * This JUnit test confirms that the displayShortestPath method shows each step in the shortest path with 
	 * weights for each step
	 */
	@Test
	void testDisplayTotalPathDistance() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphFD<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendFD(graph);
		
		// set printstream output to a new stream and setup the frontend
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
		
		// display the shortest path
		frontend.displayShortestPath();
		
		// confirm the output contains the total distance of the path
		if (!outputStreamCaptor.toString().contains("Total distance: 46")) {
			fail();
		}
	}
	
	/**
	 * This JUnit test confirms that the boat range is properly stored and updated
	 */
	@Test
	void testIntegrationChangeBoatRange() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphAE<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendBD(graph);
		
		// simulate entering a boat range of 2 then entering a new boat range of 4
		String data = "2\n4\n";
		System.setIn(new ByteArrayInputStream(data.getBytes()));
		
		// set printstream output to a new stream and setup the frontend
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
		
		// set the boat range to 2 and confirm the backend received that range
		frontend.displayCapacityPrompt();
		assertEquals(2.0,backend.getBoatRange(),"The boat range was not saved properly");
		
		// update the boat range to 4 and confirm the backend updated accordingly
		frontend.displayCapacityPrompt();
		assertEquals(4.0,backend.getBoatRange(),"The boat range was not saved properly");
	}
	
	/**
	 * This JUnit test confirms that displaying all of the edges in the graph works
	 */
	@Test
	void testIntegrationDisplayAllConnections() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphAE<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendBD(graph);
		
		// load the dot file
        try {
        	backend.loadGraphFromFile("seamap.dot");
        } catch (FileNotFoundException e) {}
		
		// set printstream output to a new stream and setup the frontend
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
		
		// display all edges in the graph
		frontend.displayAllEdges(backend.getGraphString());
		if (!outputStreamCaptor.toString().contains("Honolulu -> Sydney with cost: 4555.4")) {
			fail();
		}
	}
	
	/**
	 * This JUnit test confirms that setting the origin in the backend works properly
	 */
	@Test
	void testCodeReviewOfBackendDeveloperSetOrigin() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphAE<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendBD(graph);
		
		// load the dot file
        try {
        	backend.loadGraphFromFile("seamap.dot");
        } catch (FileNotFoundException e) {}
		
        // set the origin and then check if it was saved
		backend.setOrigin("Miami");
		assertEquals("Miami",backend.getOrigin(),"The origin port was not saved properly");
	}
	
	/**
	 * This JUnit test confirms that the boat range is properly stored and updated
	 */
	@Test
	void testCodeReviewOfBackendDeveloperGetPath() {
		
		// setup objects
		ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphAE<String,Double>();
		NavigationAppBackendInterface backend = new NavigationAppBackendBD(graph);
		
		// load the dot file
        try {
        	backend.loadGraphFromFile("seamap.dot");
        } catch (FileNotFoundException e) {}
		
        // attempt to compute the shortest path from Miami to Rome
        backend.setOrigin("Miami");
        backend.setDestination("Rome");
        backend.changeBoatRange(10000);
		assertEquals(true,backend.getPath().contains("Lisbon"),"The shortest path was not found correctly");
	}
}
