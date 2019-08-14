package zyx.server.world.pathfanding;

import java.awt.Graphics;
import java.util.ArrayList;

public class Node
{

	public final float x;
	public final float y;
	public final float z;

	private ArrayList<Node> connections;
	private Node parent;

	Node(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;

		connections = new ArrayList<>();
		parent = null;
	}

	void connectNodes(Node other)
	{
		if (other != this)
		{
			//Add node to my list
			if (!connections.contains(other))
			{
				connections.add(other);
			}

			//Add me to the nodes list
			if (!other.connections.contains(this))
			{
				other.connections.add(this);
			}
		}
	}

	void removeConnection(Node node)
	{
		connections.remove(node);
	}

	public void draw(Graphics g)
	{
		int xPos = (int) x;
		int yPos = (int) y;

		int size = 16;
		int hSize = 8;

		int innerSize = 4;
		int innerHSize = 2;

		g.drawRect(xPos - hSize, yPos - hSize, size, size);

		g.fillRect(xPos - innerHSize, yPos - innerHSize, innerSize, innerSize);
		for (int i = 0; i < connections.size(); i++)
		{
			Node node = connections.get(i);

			int nodeX = (int) node.x;
			int nodeY = (int) node.y;

			g.drawLine(xPos, yPos, nodeX, nodeY);
		}
	}

	void clearConnections()
	{
		for (Node connection : connections)
		{
			connection.removeConnection(this);
		}

		connections.clear();
	}

	Node getParent()
	{
		return parent;
	}

	boolean hasParent()
	{
		return parent != null;
	}

	void setParent(Node parent)
	{
		this.parent = parent;
	}

	ArrayList<Node> getConnections()
	{
		return connections;
	}

	@Override
	public String toString()
	{
		return String.format("Node{%s, %s, %s}", x, y, z);
	}

	float distanceTo(float xPos, float yPos, float zPos)
	{
		float dX = xPos - x;
		float dY = yPos - y;
		float dZ = zPos - z;
		
		return (dX * dX) + (dY * dY) + (dZ * dZ);
	}
}
