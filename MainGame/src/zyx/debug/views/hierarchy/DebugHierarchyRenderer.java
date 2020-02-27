package zyx.debug.views.hierarchy;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import zyx.debug.views.base.DebugIcons;

class DebugHierarchyRenderer extends DefaultTreeCellRenderer
{

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		if (value instanceof HierarchyNode)
		{
			HierarchyNode node = (HierarchyNode) value;
			
			Icon icon = DebugIcons.createIcon(node.data.instance);
			setLeafIcon(icon);
			setOpenIcon(icon);
			setClosedIcon(icon);
		}
		
		Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		
		return component;
	}
}
