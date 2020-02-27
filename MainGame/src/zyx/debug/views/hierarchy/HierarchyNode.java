package zyx.debug.views.hierarchy;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import zyx.debug.views.base.IDebugIcon;

class HierarchyNode<D extends IDebugIcon> extends DefaultMutableTreeNode
{
	AbstractHierarchyData<D> data;
	
	HierarchyNode(AbstractHierarchyData<D> nodeData)
	{
		super(nodeData);
		
		data = nodeData;
		
		ArrayList<AbstractHierarchyData<D>> dataChildren = data.getChildren();
		for (AbstractHierarchyData dataChild : dataChildren)
		{
			HierarchyNode childNode = new HierarchyNode(dataChild);
			add(childNode);
		}
	}
}
