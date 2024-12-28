public interface NavigationAppFrontendInterface {
	//public NavigationAppFrontendXX(Scanner userInput, NavigationAppBackendInterface backend);
	public void runCommandLoop();
	public char displayPortOptions();  // gives the user port options and asks for a selection
	public void displayCapacityPrompt();  // asks the user to specify the furthest distance their vessel can go
	public char displayUnexcludePortOptions();
	public void displayShortestPath();  // displays the calculated shortest path back to the user
	public void displayAllEdges(String edgeString);  // displays a list of all connected ports with the weights connecting them
}
