package zyx.debug.views.network.tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import zyx.debug.views.network.NetworkInfo;
import zyx.net.data.ReadableDataArray;
import zyx.net.data.ReadableDataObject;

public class NetworkTree extends JTree
{

	private final DefaultTreeModel model;

	public NetworkTree(NetworkInfo info)
	{
		TreeNode rootNode = createTree(info);

		model = new DefaultTreeModel(rootNode);
		setModel(model);
		
		setCellRenderer(new NetworkTreeCellRenderer());
		
		setRowHeight(17);
	}

	private TreeNode createTree(NetworkInfo info)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(info.command, true);

		for (int i = 0; i < info.count; i++)
		{
			ReadableDataObject obj = info.data.get(i);
			long timestamp = info.timestamps.get(i);
			
			DefaultMutableTreeNode readNode = new DefaultMutableTreeNode(new NetworkTreeItemWrapper(info, timestamp), true);
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
				DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(new NetworkTreeItemWrapper(objData, key), true);
				addReadableToNode(dataNode, (ReadableDataObject) objData);
				
				readNode.add(dataNode);
			}
			else if (objData instanceof ReadableDataArray)
			{
				ReadableDataArray array = (ReadableDataArray) objData;
				DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(new NetworkTreeItemWrapper(array, key), true);

				for (int i = 0; i < array.size(); i++)
				{
					Object item = array.get(i);
					
					DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(new NetworkTreeItemWrapper(item, i), true);
					addObjectToNode(dataNode, item);
					arrayNode.add(dataNode);
				}
				readNode.add(arrayNode);
			}
			else if (objData instanceof byte[])
			{
				byte[] byteData = (byte[]) objData;
				String nodeText = key + ": ByteArray[" + byteData.length + "]";
				addObjectToNode(readNode, new NetworkTreeItemWrapper(objData, nodeText));
			}
			else
			{
				String nodeText = key + ": " + objData;
				addObjectToNode(readNode, new NetworkTreeItemWrapper(objData, nodeText));
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
			DefaultMutableTreeNode leafNode = new DefaultMutableTreeNode(item, false);
			dataNode.add(leafNode);
		}
	}

}
