package zyx.debug.views.hierarchy;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import zyx.engine.components.world.WorldObjectNode;

public class HierarchyNode extends DefaultMutableTreeNode
{
	public WorldObjectNode data;
	
	public HierarchyNode(WorldObjectNode data)
	{
		super(data);
		
		this.data = data;
		
		ArrayList<WorldObjectNode> dataChildren = this.data.children;
		for (WorldObjectNode dataChild : dataChildren)
		{
			HierarchyNode childNode = new HierarchyNode(dataChild);
			add(childNode);
		}
	}
}
