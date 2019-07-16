package zyx.debug.views.network;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import zyx.net.data.ReadableDataArray;
import zyx.net.data.ReadableDataObject;

public class DebugNetworkTree extends JTree
{

	private final DefaultTreeModel model;

	public DebugNetworkTree(NetworkInfo info)
	{
		TreeNode rootNode = createTree(info);

		model = new DefaultTreeModel(rootNode);
		setModel(model);
	}

	private TreeNode createTree(NetworkInfo info)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(info.command, true);

		for (int i = 0; i < info.count; i++)
		{
			ReadableDataObject obj = info.data.get(i);
			long timestamp = info.timestamps.get(i);

			DefaultMutableTreeNode readNode = new DefaultMutableTreeNode(timestamp, true);
			addReadableToNode(readNode, obj);

			root.add(readNode);
		}

		return root;
	}

	private void addReadableToNode(DefaultMutableTreeNode readNode, ReadableDataObject obj)
	{
		String[] keys = obj.getAllKeys();

		for (String key : keys)
		{
			Object objData = obj.getRaw(key);

			if (objData instanceof ReadableDataObject)
			{
				DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(key, true);
				addReadableToNode(dataNode, (ReadableDataObject) objData);
			}
			else if (objData instanceof ReadableDataArray)
			{
				ReadableDataArray array = (ReadableDataArray) objData;
				DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(key + "[" + array.size() + "]", true);

				for (int i = 0; i < array.size(); i++)
				{
					Object item = array.get(i);
					
					DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(i, true);
					addObjectToNode(dataNode, item);
					arrayNode.add(dataNode);
				}
				readNode.add(arrayNode);
			}
			else
			{
				String value = key + ": " + obj.getToString(key);
				addObjectToNode(readNode, value);
			}
		}
	}

	private void addObjectToNode(DefaultMutableTreeNode dataNode, Object item)
	{
		if (item instanceof ReadableDataObject)
		{
			addReadableToNode(dataNode, (ReadableDataObject) item);
		}
		else
		{
			DefaultMutableTreeNode leafNode = new DefaultMutableTreeNode(item.toString(), false);
			dataNode.add(leafNode);
		}
	}

}
