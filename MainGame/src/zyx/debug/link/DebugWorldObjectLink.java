package zyx.debug.link;

import zyx.debug.editor.hierarchy.DebugWorldTreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import zyx.engine.components.world.World3D;
import zyx.engine.components.world.WorldObject;
import zyx.engine.components.world.WorldObjectNode;

public class DebugWorldObjectLink
{
	private final Object LOCK = new Object();
	private boolean update = false;
	
	private DebugWorldTreeNode glRootNode;
	private HashMap<WorldObject, DebugWorldTreeNode> objectMap;
	private HashMap<WorldObject, ArrayList<DebugWorldTreeNode>> fosterChildrenMap;
	

	DebugWorldObjectLink()
	{
		objectMap = new HashMap<>();
		fosterChildrenMap = new HashMap<>();
	}

	public DebugWorldTreeNode getGlRootNode()
	{
		if (glRootNode == null)
		{
			World3D world = World3D.getInstance();
			glRootNode = new DebugWorldTreeNode(world);
			objectMap.put(world, glRootNode);
		}
		return glRootNode;
	}
	
	public void addNode(WorldObject obj, WorldObject parent)
	{
		DebugWorldTreeNode node = new DebugWorldTreeNode(obj);
		objectMap.put(obj, node);
		
		DebugWorldTreeNode parentNode = objectMap.get(parent);
		if (parentNode != null)
		{
			ArrayList<DebugWorldTreeNode> children = fosterChildrenMap.remove(obj);
			if (children != null)
			{
				for (DebugWorldTreeNode childNode : children)
				{
					node.addChild(childNode);
				}
			}
			
			parentNode.addChild(node);
		}
		else
		{
			addToFosterChildren(node, parent);
		}
	}
	
	private void addToFosterChildren(DebugWorldTreeNode node, WorldObject parent)
	{
		ArrayList<DebugWorldTreeNode> list = fosterChildrenMap.get(parent);
		if (list == null)
		{
			list = new ArrayList<>();
			fosterChildrenMap.put(parent, list);
		}
		
		list.add(node);
	}
	
	public void removeNode(WorldObject child)
	{
		DebugWorldTreeNode node = objectMap.get(child);
		if (node != null)
		{
			node.removeFromParent();
			addToFosterChildren(node, child);
		}
	}
	
	public void disposeNode(WorldObject child)
	{
		removeNode(child);
		objectMap.remove(child);
		fosterChildrenMap.remove(child);
	}
	
	public void updateList()
	{
		synchronized (LOCK)
		{
			update = true;
		}
	}

	public boolean hasUpdate()
	{
		synchronized (LOCK)
		{			
			return update;
		}
	}
	
	public WorldObjectNode getActiveWorldObjects()
	{
		synchronized(LOCK)
		{
			update = false;
			
			WorldObject base = World3D.getInstance();
			
			return new WorldObjectNode(base);
		}
	}
}
