package zyx.debug.views.hierarchy;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

class HierarchyNode<D> extends DefaultMutableTreeNode
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
