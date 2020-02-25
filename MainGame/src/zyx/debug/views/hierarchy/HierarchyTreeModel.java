package zyx.debug.views.hierarchy;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

class HierarchyTreeModel<D> extends DefaultTreeModel
{

	HierarchyTreeModel()
	{
		super(new DefaultMutableTreeNode("N/A"));
	}
	
	void reset(AbstractHierarchyData<D> data)
	{
		HierarchyNode<D> newRoot = new HierarchyNode<>(data);
		setRoot(newRoot);
	}
}
