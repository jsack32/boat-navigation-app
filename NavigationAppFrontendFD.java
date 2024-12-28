// --== CS400 File Header Information ==--
// Name: Luke Steimle
// Email: lsteimle@wisc.edu
// Group and Team: BT Red
// Group TA: Samuel Church
// Lecturer: Gary Dahl
// Notes to Grader: n/a

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class NavigationAppFrontendFD implements NavigationAppFrontendInterface {

	private Scanner userInput; // scanner for reading user input
	private NavigationAppBackendInterface backend; // backend object of the navigation app
	private List<String> portOptions; // list of ports that the user should be able to select from
	
	/**
	 * Instantiates the frontend of the app which will interact with the user. 
	 * Includes a scanner for gathering user input and a NavigationAppBackendInterface object 
	 * to communicate with the backend.
	 * 
	 * @param userInput - the scanner reading user input
	 * @param backend - backend object of the navigation app
	 */
	public NavigationAppFrontendFD(Scanner userInput, NavigationAppBackendInterface backend) {
		this.userInput = userInput; // assign private instance variables
		this.backend = backend;
		this.portOptions = backend.getPortList();
	}
	
	/**
	 * This is the method that is called to start the application user interface. It handles
	 * all interaction with the user and displays the outputs.
	 */
	@Override
	public void runCommandLoop() {
		
		// display a welcome message
		hr();
		System.out.println("Welcome to the Navigation Wayfinder App! Please select an option to begin.");
		
		// initialize menu choice variable
		int menuChoice = 0;

		// display the start menu and read the user's menu option choice
		// this while loop repeats until the user selects option 3 to exit
		while (menuChoice != 3) {
			menuChoice = displayStartMenu();
			
			// if the user wants to find a shortest path
			if (menuChoice == 1) {
				
				// asks the user to select a starting port
				hr();
				System.out.println("Please select a starting port to begin.\n");
				
				// handles starting port selection
				char startChar = displayPortOptions(); // gets identifier for port
				String startPort = convertCharToPortString(startChar); // converts identifier to name of port
				backend.setOrigin(startPort); // send the starting port to the backend
				portOptions.remove(startPort); // remove the selected port from the list of selectable ports
				
				// asks the user to select a destination port
				hr();
				System.out.println("Thank you. Next, please select your destination.\n");
				
				// handles destination port selection
				char endChar = displayPortOptions(); // gets identifier for port
				String endPort = convertCharToPortString(endChar); // converts identifier to name of port
				backend.setDestination(endPort); // send the destination port to the backend
				portOptions.remove(endPort); // remove the selected port from the list of selectable ports
				
				// asks the user to select a range for their vessel
				hr();
				displayCapacityPrompt();
				
				// asks the user to pick which ports to exclude
				hr();
				displayExclusionPrompt();
				
				// asks the user to confirm or change selections
				hr();
				int confirmMenuChoice = 0;
				while (confirmMenuChoice != 1) {
					
					// prints current voyage details
					System.out.println("Do the details of your voyage look correct?");
					System.out.println("Starting port: " + startPort);
					System.out.println("Destination port: " + endPort);
					System.out.println("Excluded ports: " + backend.getExcludedPortList());
					System.out.println("Boat range: " + backend.getBoatRange() + "\n");
					
					// display the confirmation menu and read the user's menu option choice
					confirmMenuChoice = displayConfirmationMenu();
					
					// if the user wants to change the starting port
					if (confirmMenuChoice == 2) {
						
						// display available ports
						hr();
						portOptions.add(startPort);
						
						// handles new starting port selection
						startChar = displayPortOptions();
						startPort = convertCharToPortString(startChar);
						backend.setOrigin(startPort);
						portOptions.remove(startPort);
					}
					
					// if the user wants to change the destination port
					else if (confirmMenuChoice == 3) {
						
						// display available ports
						hr();
						portOptions.add(endPort);
						
						// handles new destination port selection
						endChar = displayPortOptions();
						endPort = convertCharToPortString(endChar);
						backend.setOrigin(endPort);
						portOptions.remove(endPort);
					}
					
					// if the user wants to exclude more ports
					else if (confirmMenuChoice == 4) {
						hr();
						displayExclusionPrompt();
					}
					
					// if the user wants to unexclude ports
					else if (confirmMenuChoice == 5) {
						
						// display the list of currently excluded ports and get the user selection
						hr();
						char unexcludeChar = displayUnexcludePortOptions();
						
						// add the port back to the list of available ports
						String unexcludePort = convertCharToExcludedPortString(unexcludeChar);
						portOptions.add(unexcludePort);
						backend.includePort(unexcludePort);
					}
					
					// if the user wants to change the vessel's range
					else if (confirmMenuChoice == 6) {
						hr();
						displayCapacityPrompt();
					}
					
					// if the user did not enter a valid menu option
					else {
						System.out.println("Invalid selection. Please choose again.");
					}
				}
				
				// display the shortest path calculated using Dijkstras
				hr();
				displayShortestPath();
				
				// re-include all of the excluded ports
				List<String> excludedPorts = backend.getExcludedPortList();
				for (int i = 0; i < excludedPorts.size(); i++) {
					portOptions.add(excludedPorts.get(i));
					backend.includePort(excludedPorts.get(i));
				}
				
				// add start and end ports back to list of ports to display
				portOptions.add(startPort);
				portOptions.add(endPort);
			}
			
			// if the user wants to see a list of all port connections and their weights
			else if (menuChoice == 2) {
				hr();
				displayAllEdges(backend.getGraphString());
			}
			
			// if the user provides an entry that is not one of the menu options
			else if  (menuChoice != 3) {
				System.out.println("Invalid selection. Please choose again.");
			}
		}
		
		// display a closing message
		System.out.println("Safe travels!");
	}
	
	/**
	 * Helper function to display the confirmation menu. The options are printed and then the integer
	 * selection of the user is returned.
	 * @return integer corresponding to the menu selection of the user
	 */
	private int displayConfirmationMenu() {
		
		// print the menu
		System.out.println("1) Correct! Find my shortest route!");
		System.out.println("2) Change the starting port");
		System.out.println("3) Change the destination port");
		System.out.println("4) Exclude more ports");
		System.out.println("5) Unexclude ports");
		System.out.println("6) Change my boat range");
		System.out.print("Selection: ");
		
		// collect and return the user input
		int menuChoice = userInput.nextInt();
		userInput.nextLine();
		return menuChoice;
	}
	
	/**
	 * Helper function to display the starting menu. The options are printed and then the integer
	 * selection of the user is returned.
	 * @return integer corresponding to the menu selection of the user
	 */
	private int displayStartMenu() {
		
		// print the menu
		hr();
		System.out.println("1) Find the shortest path for a voyage");
		System.out.println("2) See a list of all connected ports");
		System.out.println("3) Exit");
		System.out.print("Selection: ");
		
		// collect and return the user input
		int menuChoice = 0;
		try { menuChoice = userInput.nextInt(); } 
		catch(InputMismatchException e) { }
		userInput.nextLine();
		return menuChoice;
	}
	
	/**
	 * Helper function to prompt the user for ports that should be excluded from the voyage
	 */
	private void displayExclusionPrompt() {
		
		// ask if any ports should be excluded and collect the yes or no response
		System.out.println("Should any ports be excluded from your voyage (y/n)?");
		char avoidPorts = getAvoidPortsResponse();
		
		// if the user wants to exclude ports
		while (avoidPorts == 'y') {
			
			// get the user selection of which port to exclude
			char excludeChar = displayPortOptions();
			String portToExclude = convertCharToPortString(excludeChar);
		
			// exclude the port on the backend and remove the port from the list of available ports
			backend.excludePort(portToExclude);
			portOptions.remove(portToExclude);
			
			// ask if the user would like to continue excluding
			System.out.println("Would you like to exclude another port (y/n)?");
			avoidPorts = getAvoidPortsResponse();
		}
	}
	
	/**
	 * Helper function to make sure that the user is providing a valid yes or no response
	 * when asked if the user wants to exclude ports
	 * @return 'y' or 'n' depending on whether the user wants to exclude ports
	 */
	private char getAvoidPortsResponse() {
		
		// retrieve the first character of the user response
		char avoidPorts = userInput.next().toLowerCase().charAt(0);
		userInput.nextLine();
		
		// check if the character is 'y' (yes) or 'n' (no)
		while (!(avoidPorts == 'y' || avoidPorts == 'n')) {
			
			// re-prompt the user if the response is not 'y' or 'n' 
			System.out.println("Invalid selection. Please try again.");
			System.out.println("Should any ports be excluded from your voyage (y/n)?");
			avoidPorts = userInput.next().toLowerCase().charAt(0);
			userInput.nextLine();
		}
		
		return avoidPorts;
	}

	/**
	 * Helper function to convert the menu character selection to the corresponding port string name in 
	 * the available ports list
	 * @param charToConvert - menu option corresponding to a specific port
	 * @return string of the port name
	 */
	private String convertCharToPortString(char charToConvert) {
		
		// converts the character numeric value to a list index value
		int index = Character.getNumericValue(charToConvert) - 10;
		
		// return the port at the calculated index
		return portOptions.get(index);
	}
	
	/**
	 * Helper function to convert the menu character selection to the corresponding port string name in 
	 * the excluded ports list
	 * @param charToConvert - menu option corresponding to a specific port
	 * @return string of the port name
	 */
	private String convertCharToExcludedPortString(char charToConvert) {
		
		// converts the character numeric value to a list index value
		int index = Character.getNumericValue(charToConvert) - 10;
		
		// return the port at the calculated index
		return backend.getExcludedPortList().get(index);
	}
	
	/**
	 * Helper method that displays a horizontal row of dashes
	 */
	private void hr() {
		System.out.println("-------------------------------------------------------------------------------");
	}
	
	/**
	 * Displays the list of ports that have not yet been selected
	 */
	@Override
	public char displayPortOptions() {
		
		// returns the output of the helper function
		// input of false represents using the full list of ports rather than the list of excluded ports
		return displayPortOptionsHelper(false);
	}
	
	/**
	 * Displays the list of ports that have been placed on the exclude list
	 */
	@Override
	public char displayUnexcludePortOptions() {
		// returns the output of the helper function
		// input of true represents using the list of excluded ports rather than the full list
		return displayPortOptionsHelper(true);
	}
	
	/**
	 * Helper function to display the appropriate list of ports. Will display the available ports if
	 * onlyExcluded is false, and the list of excluded ports if onlyExcluded is true.
	 * @param onlyExcluded - boolean value indicating which port list is desired
	 * @return menu item character corresponding to port name 
	 */
	private char displayPortOptionsHelper(boolean onlyExcluded) {
		
		// initializes variables
		char selectedId = '1';            // holds the user's char selection
		boolean validSelection = false;   // switched to true after the user picks a port
		List<String> portList;			  // list holding the ports to display				
		
		// sets portList to either the list of excluded ports or all of the ports
		if (onlyExcluded) { portList = backend.getExcludedPortList(); }
		else { portList = portOptions; }
		
		char endOptionId = (char) ('a' + portList.size() - 1); // character corresponding to the last port in the list
		
		// loops until a valid port option is picked
		while (!validSelection) {
			
			// prompt the user to select a port option
			System.out.println("Choose a letter a-" + endOptionId + " to make a port selection.");
			printPortList(portList);
			
			// reads the user's choice
			System.out.print("Port choice: ");
			selectedId = userInput.next().toLowerCase().charAt(0);
			userInput.nextLine();
			
			// checks if the selected option is within the valid range
			if (selectedId >= 'a' && selectedId <= endOptionId) {  
				// exit the loop if the selection is valid
				validSelection = true; 
			}
			else { 
				// prompt the user to reselect an option if the previous selection was not valid 
				System.out.print("\nInvalid selection. ");
			}
		}
		
		return selectedId;
	}
	
	/**
	 * Displays the given port list to the user with letter indices before each
	 * @param portList - list of port names to be displayed
	 */
	private void printPortList(List<String> portList) {
		
		// letter indices always begin with 'a'
		char optionId = 'a';
		
		// loops through port list and displays each port in the list
		for (int i = 0; i < portList.size(); i++) {
			System.out.println(optionId + ") " + portList.get(i));
			optionId += 1;
		}
	}

	/**
	 * Prompts the user to input their boat's range and then sends the specified range to the backend
	 */
	@Override
	public void displayCapacityPrompt() {
		
		// prompt the user to input the capacity of their vessel
		System.out.println("What is the longest distance that your vessel can travel witout stopping?");
		System.out.println("Please give a distance to the nearest nautical mile.");
		System.out.print("Boat range: ");
		
		// read in the provided capacity and give to the backend
		int boatRange = userInput.nextInt();
		userInput.nextLine();
		backend.changeBoatRange(boatRange);
	}

	/**
	 * Displays the shortest path from the starting port to the destination port to the user.
	 * The distance of each leg is shown along with the total distance.
	 */
	@Override
	public void displayShortestPath() {
		
		// gets the path and weights as lists from the backend
		List<String> path = null;
		List<Double> weights = null;
		try {
			path = backend.getPath();
			weights = backend.getWeights();
		} catch (IllegalArgumentException e) {
			System.out.println("No path found. Please change the details of your voyage and try again.");
			return;
		}
		
		// loops through the path and weight lists and displays each step to the user
		for (int i = 1; i < path.size(); i++) {
			System.out.println(path.get(i-1) + " --> " + path.get(i) + " (" + weights.get(i-1) + ")");
		}
		
		// prints the total path distance
		System.out.println("\nTotal distance: " + backend.getDistance());
	}

	/**
	 * Displays all of the edges in the underlying graph with the edge weights
	 */
	@Override
	public void displayAllEdges(String edgeString) {
		System.out.println(edgeString);
	}

}
