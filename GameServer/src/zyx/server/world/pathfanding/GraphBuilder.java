package zyx.server.world.pathfanding;

public class GraphBuilder
{	
	private static NodeGraph instance;
	
	public static NodeGraph getGraph()
	{
		if (instance == null)
		{
			instance = new NodeGraph();
		}
		
		return instance;
	}
	
	public static void saveToFile(NodeGraph graph, String path)
	{
		
	}
}
