package zyx.logic.converter.smd.bones.importer;

import java.util.HashMap;
import java.util.Map;
import zyx.logic.converter.smd.bones.Node;

public class SmdNodeHandler implements ISmdHandler
{

	private final HashMap<Integer, Node> nodes;
	private final HashMap<Node, Integer> parentIds;

	public SmdNodeHandler()
	{
		nodes = new HashMap<>();
		parentIds = new HashMap<>();
	}

	@Override
	public void handleLine(String line)
	{
		String[] split = line.split(" ");
		int nodeId = Integer.parseInt(split[0]);
		String name = split[1];
		int parentId = Integer.parseInt(split[2]);
		
		Node node = new Node();
		node.setNodeId(nodeId);
		node.setName(name);
		
		nodes.put(nodeId, node);
		parentIds.put(node, parentId);
		
		System.out.println("Created node: " + nodeId + " " + name + " " + parentId);
	}

	@Override
	public Object getResult()
	{
		setAllParentNodes();

		Node[] dummy = new Node[0];
		return nodes.values().toArray(dummy);
	}

	private void setAllParentNodes()
	{
		for (Map.Entry<Node, Integer> entry : parentIds.entrySet())
		{
			Node node = entry.getKey();
			Integer parentId = entry.getValue();

			node.setParentNode(nodes.get(parentId));
		}

		parentIds.clear();
	}
}