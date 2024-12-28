import java.util.List;

public class ExclusiveDijkstraGraphFD<NodeType,EdgeType extends Number> 
	extends DijkstraGraph<NodeType,EdgeType> 
	implements ExclusiveDijkstraGraphInterface<NodeType,EdgeType> {

	@Override
	public boolean excludeNode(NodeType excluded) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean includeNode(NodeType include) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<NodeType> getExcludedNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEdgeLimit(double maximumCost) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public double getEdgeLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

}
