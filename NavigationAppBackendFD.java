import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class NavigationAppBackendFD implements NavigationAppBackendInterface {
	int boatRange = 0;
	
	private ExclusiveDijkstraGraphInterface<String, Double> graph;
	
	public NavigationAppBackendFD(ExclusiveDijkstraGraphInterface<String,Double> graph) {
		this.graph = graph;
	}
	
	@Override
	public void loadGraphFromFile(String filename) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeBoatRange(int newRange) throws IllegalArgumentException {
		this.boatRange = newRange;
		
	}

	@Override
	public int getBoatRange() {
		return this.boatRange;
	}

	@Override
	public void setOrigin(String origin) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDestination(String origin) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDestination() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void excludePort(String port) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void includePort(String port) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getPortList() {
		// TODO Auto-generated method stub
		List<String> ports = new ArrayList<String>();
		ports.add("Miami");
		ports.add("NYC");
		ports.add("Charleston");
		ports.add("Anapolis");
		ports.add("Haiti");
		ports.add("Bahamas");
		ports.add("Puerto Rico");
		return ports;
	}

	@Override
	public List<String> getExcludedPortList() {
		List<String> ports = new ArrayList<String>();
		ports.add("Haiti");
		ports.add("Bahamas");
		return ports;
	}

	@Override
	public String getGraphString() {
		String graphString = "Miami -> Charlestion (3) \n"
				+ "NYC -> Haiti (45) \n"
				+ "Haiti -> Bahamas (14)";
		return graphString;
	}

	@Override
	public List<String> getPath() throws IllegalArgumentException {
		List<String> path = new ArrayList<String>();
		path.add("Miami");
		path.add("Charleston");
		path.add("NYC");
		return path;
	}

	@Override
	public List<Double> getWeights() throws IllegalArgumentException {
		List<Double> path = new ArrayList<Double>();
		path.add(10.0);
		path.add(22.1);
		return path;
	}

	@Override
	public int getDistance() throws IllegalArgumentException {
		return 46;
	}

}
