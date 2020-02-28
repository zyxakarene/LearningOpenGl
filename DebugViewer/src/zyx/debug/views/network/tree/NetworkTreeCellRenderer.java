package zyx.debug.views.network.tree;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import zyx.debug.views.base.DebugIcons;

public class NetworkTreeCellRenderer extends DefaultTreeCellRenderer
{

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

		if (userObject instanceof NetworkTreeItemWrapper)
		{
			NetworkTreeItemWrapper wrapper = (NetworkTreeItemWrapper) userObject;
			setIcon(DebugIcons.createIcon(wrapper.getWrappedClass()));
		}
		else
		{
			setIcon(NetworkIcons.createIcon(userObject.getClass()));
		}
		
		return this;
	}
}
