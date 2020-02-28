package zyx.debug.views.hierarchy;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import zyx.debug.network.vo.hierarchy.HierarchyInfo;

class HierarchyNode extends DefaultMutableTreeNode
{
	HierarchyInfo data;
	
	HierarchyNode(HierarchyInfo nodeData)
	{
		super(nodeData);
		
		data = nodeData;
		
		ArrayList<HierarchyInfo> dataChildren = data.children;
		for (HierarchyInfo dataChild : dataChildren)
		{
			HierarchyNode childNode = new HierarchyNode(dataChild);
			add(childNode);
		}
	}
}
