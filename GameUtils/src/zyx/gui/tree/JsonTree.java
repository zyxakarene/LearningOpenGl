package zyx.gui.tree;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

public class JsonTree extends JTree
{
	private DefaultTreeModel model;
	
	public JsonTree()
	{
		JsonTreeNode root = TreeFileScanner.getRoot();
		
		model = new DefaultTreeModel(root);
		setModel(model);
		setCellRenderer(new JsonTreeCellRenderer());
		
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	public File getSelectedItem()
	{
		if (getSelectionCount() == 1)
		{
			Object lastComponent = getSelectionPath().getLastPathComponent();

			if (lastComponent instanceof JsonTreeNode)
			{
				return ((JsonTreeNode) lastComponent).file;
			}
			
		}
		return null;
	}

	public File[] getSelectedSubItems()
	{
		ArrayList<File> fileList = new ArrayList<>();
		
		if (getSelectionCount() == 1)
		{
			Object lastComponent = getSelectionPath().getLastPathComponent();

			if (lastComponent instanceof JsonTreeNode)
			{
				JsonTreeNode node = (JsonTreeNode) lastComponent;
				addSubFiles(node, fileList);
			}
		}
		
		return fileList.toArray(new File[fileList.size()]);
	}

	private void addSubFiles(JsonTreeNode node, ArrayList<File> fileList)
	{
		File file = node.file;
		if (file.isDirectory())
		{
			int length = node.getChildCount();
			for (int i = 0; i < length; i++)
			{
				TreeNode child = node.getChildAt(i);
				if (child instanceof JsonTreeNode)
				{
					addSubFiles((JsonTreeNode)child, fileList);
				}
			}
		}
		else
		{
			fileList.add(file);
		}
	}
}
