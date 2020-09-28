package zyx.gui.tree;

import java.awt.Component;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import zyx.gui.tree.icons.IconManager;

public class JsonTreeCellRenderer extends DefaultTreeCellRenderer
{

	private final Icon skeleton;
	private final Icon mesh;
	private final Icon openFolder;
	private final Icon emptyFolder;

	public JsonTreeCellRenderer()
	{
		emptyFolder = IconManager.getIcon(IconManager.EMPTY_FOLDER);
		openFolder = IconManager.getIcon(IconManager.OPEN_FOLDER);
		mesh = IconManager.getIcon(IconManager.MESH);
		skeleton = IconManager.getIcon(IconManager.SKELETON);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		if (value instanceof JsonTreeNode)
		{
			String name = ((JsonTreeNode) value).file.getName();
			if (name.contains("skeleton"))
			{
				setLeafIcon(skeleton);
			}
			else
			{
				setLeafIcon(mesh);
			}
		}
		return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Icon getOpenIcon()
	{
		return openFolder;
	}

	@Override
	public Icon getClosedIcon()
	{
		return emptyFolder;
	}
}
