import java.io.FileNotFoundException;
import java.util.List;

public interface NavigationAppBackendInterface {
    // public NavigationAppBackendInterface(ExclusiveDijkstraGraphInterface graph);

    public void loadGraphFromFile(String filename) throws FileNotFoundException;

    public void changeBoatRange(int newRange) throws IllegalArgumentException;
    public int getBoatRange();
    public void setOrigin(String origin) throws IllegalArgumentException;
    public String getOrigin();
    public void setDestination(String origin) throws IllegalArgumentException;
    public String getDestination();
    public void excludePort(String port) throws IllegalArgumentException;
    public void includePort(String port) throws IllegalArgumentException;

    public List<String> getPortList();
    public List<String> getExcludedPortList();
    public String getGraphString();

    public List<String> getPath() throws IllegalArgumentException;
    public List<Double> getWeights() throws IllegalArgumentException;
    public int getDistance() throws IllegalArgumentException;
}
