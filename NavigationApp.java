// --== CS400 File Header Information ==--
// Name: Luke Steimle
// Email: lsteimle@wisc.edu
// Group and Team: BT Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: n/a
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NavigationApp {
    public static void main(String[] args) {
    	
    	// initialize the graph and backend objects
    	ExclusiveDijkstraGraphInterface<String,Double> graph = new ExclusiveDijkstraGraphAE<String,Double>();
        NavigationAppBackendInterface backend = new NavigationAppBackendBD(graph);

        // load the .dot file
        try {
            backend.loadGraphFromFile("seamap.dot");
        } catch(FileNotFoundException e) {
            System.out.println("File not found!");
        }
	    
        // initialize the frontend and begin the application
        NavigationAppFrontendInterface frontend = new NavigationAppFrontendFD(new Scanner(System.in), backend);
        frontend.runCommandLoop();
    }
}
