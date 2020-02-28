package zyx.debug.views.hierarchy;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import zyx.debug.network.vo.hierarchy.HierarchyInfo;

class HierarchyTreeModel extends DefaultTreeModel
{

	HierarchyTreeModel()
	{
		super(new DefaultMutableTreeNode("N/A"));
	}
	
	void reset(HierarchyInfo data)
	{
		HierarchyNode newRoot = new HierarchyNode(data);
		setRoot(newRoot);
	}
}
