package zyx.debug.views.hierarchy;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import zyx.debug.views.base.IDebugIcon;

class HierarchyTreeModel<D extends IDebugIcon> extends DefaultTreeModel
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
