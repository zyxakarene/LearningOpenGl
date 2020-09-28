package zyx.gui.tree;

import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;

public class JsonTreeNode extends DefaultMutableTreeNode
{

	public final File file;

	public JsonTreeNode(File file)
	{
		super(file);
		
		this.file = file;
	}

	@Override
	public String toString()
	{
		return file.getName();
	}
}
