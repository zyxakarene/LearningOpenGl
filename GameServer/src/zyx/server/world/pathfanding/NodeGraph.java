package zyx.server.world.pathfanding;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class NodeGraph
{
	private ArrayList<Node> nodes;

	NodeGraph()
	{
		nodes = new ArrayList<>();
		
		Node start = addNodeAt(0, 0, 0);
		Node midTop = addNodeAt(85, 50, 0);
		Node midMid = addNodeAt(95, 125, 0);
		Node midBtm = addNodeAt(145, 180, 0);
		Node table = addNodeAt(80, 100, 0);
		Node chair = addNodeAt(80, 150, 0);
		Node monitor = addNodeAt(150, 55, 0);
		Node fridge = addNodeAt(200, 45, 0);
		Node stove = addNodeAt(250, 55, 0);
		Node foodTable = addNodeAt(240, 100, 0);
		Node dishwasher = addNodeAt(170, 250, 0);
		
		connectNodes(monitor, midTop);
		connectNodes(dishwasher, foodTable);
		
		connectNodes(monitor, fridge);
		connectNodes(fridge, stove);
		connectNodes(stove, foodTable);
		connectNodes(foodTable, monitor);
		
		connectNodes(start, midTop);
		connectNodes(midTop, midMid);
		connectNodes(midMid, midBtm);
		connectNodes(midBtm, dishwasher);
		connectNodes(midMid, chair);
		connectNodes(midMid, table);
	}
	
	public Node addNodeAt(float x, float y, float z)
	{
		Node node = new Node(x, y, z);
		nodes.add(node);
		
		return node;
	}
	
	public void connectNodes(Node a, Node b)
	{
		a.connectNodes(b);
	}
	
	public Node getClosetsTo(float x, float y, float z)
	{
		Node closest = null;
		float shortestDistance = Float.MAX_VALUE;
		
		for (Node node : nodes)
		{
			float distance = node.distanceTo(x, y, z);
			if (distance < shortestDistance)
			{
				shortestDistance = distance;
				closest = node;
			}
		}
		
		return closest;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		for (Node node : nodes)
		{
			node.draw(g);
		}
	}

	void clearParents()
	{
		for (Node node : nodes)
		{
			node.setParent(null);
		}
	}
}
