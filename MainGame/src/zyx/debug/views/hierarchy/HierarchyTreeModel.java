package zyx.debug.views.hierarchy;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import zyx.engine.components.world.WorldObjectNode;

public class HierarchyTreeModel extends DefaultTreeModel
{

	public HierarchyTreeModel()
	{
		super(new DefaultMutableTreeNode("N/A"));
	}
	
	public void reset(WorldObjectNode data)
	{
		HierarchyNode newRoot = new HierarchyNode(data);
		setRoot(newRoot);
	}
}
