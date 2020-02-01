package zyx.server.world.pathfanding;

import java.util.ArrayList;
import java.util.Collections;
import org.lwjgl.util.vector.Vector3f;

public class AStarPathFinder
{

	private int currentNodeIndex;
	private int nodeCount;

	private NodeGraph graph;

	private ArrayList<Vector3f> path;
	private ArrayList<Node> closedList;
	private ArrayList<Node> openList;

	public AStarPathFinder()
	{
		graph = GraphBuilder.getGraph();
		path = new ArrayList<>();

		closedList = new ArrayList<>();
		openList = new ArrayList<>();
	}

	public void preparePath(float fromX, float fromY, float fromZ, float toX, float toY, float toZ)
	{
		path.clear();

		Node nodeFrom = graph.getClosetsTo(fromX, fromY, fromZ);
		Node nodeTo = graph.getClosetsTo(toX, toY, toZ);

		path.add(new Vector3f(toX, toY, toZ));

		createPathBetween(nodeFrom, nodeTo);
		currentNodeIndex = 0;
	}

	public void getCurrentTarget(Vector3f out)
	{
		Vector3f current = path.get(currentNodeIndex);
		out.set(current);
	}

	public void getNextTarget(Vector3f out)
	{
		if (hasNextNode())
		{
			int nextIndex = currentNodeIndex + 1;
			Vector3f current = path.get(nextIndex);
			out.set(current);
		}
		else
		{
			getCurrentTarget(out);
		}
	}

	public boolean hasMoreNodes()
	{
		return currentNodeIndex < nodeCount;
	}

	public boolean hasNextNode()
	{
		return currentNodeIndex + 1 < nodeCount;
	}

	public void onHitNode()
	{
		currentNodeIndex++;
	}

	private void createPathBetween(Node from, Node to)
	{
		openList.add(from);

		while (!openList.isEmpty())
		{
			Node current = getLowestF(openList, from, to);

			if (current == to)
			{
				createFinalPathFrom(to);
				openList.clear();
			}
			else
			{
				openList.remove(current);
				closedList.add(current);

				ArrayList<Node> connections = current.getConnections();
				int size = connections.size();

				for (int i = 0; i < size; i++)
				{
					Node neighbor = connections.get(i);
					if (!closedList.contains(neighbor))
					{
						if (!openList.contains(neighbor))
						{
							neighbor.setParent(current);
							openList.add(neighbor);
						}
					}
				}
			}
		}

		closedList.clear();
		openList.clear();
	}

	private void createFinalPathFrom(Node destination)
	{
		Node node = destination;
		while (node.hasParent())
		{
			path.add(new Vector3f(node.x, node.y, node.z));
			node = node.getParent();
		}
		path.add(new Vector3f(node.x, node.y, node.z));

		nodeCount = path.size();

		Collections.reverse(path);
		graph.clearParents();
	}

	private static Node getLowestF(ArrayList<Node> openList, Node from, Node to)
	{
		Node lowest = null;
		float fValue = Float.MAX_VALUE;

		float gValue;
		float dX;
		float dY;
		float dZ;
		float hValue;

		for (int i = 0; i < openList.size(); i++)
		{
			Node node = openList.get(i);

			gValue = 0;
			dX = to.x - node.x;
			dY = to.y - node.y;
			dZ = to.z - node.z;
			hValue = (dX * dX) + (dY * dY) + (dZ * dZ);

			while (node != from)
			{
				dX = node.x - from.x;
				dY = node.y - from.y;
				dZ = node.z - from.z;
				gValue = gValue + ((dX * dX) + (dY * dY) + (dZ * dZ));
				node = node.getParent();
			}

			if (gValue + hValue < fValue)
			{
				fValue = gValue + hValue;
				lowest = openList.get(i);
			}
		}

		return lowest;
	}
}
