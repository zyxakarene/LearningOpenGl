package zyx.debug.views.network.tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import zyx.debug.network.vo.network.NetworkCommandInfo;
import zyx.debug.network.vo.network.NetworkData;
import zyx.debug.network.vo.network.NetworkDataType;

public class NetworkTree extends JTree
{

	private final DefaultTreeModel model;

	public NetworkTree(NetworkCommandInfo info)
	{
		TreeNode rootNode = createTree(info);

		model = new DefaultTreeModel(rootNode);
		setModel(model);

		setCellRenderer(new NetworkTreeCellRenderer());

		setRowHeight(17);
	}

	private TreeNode createTree(NetworkCommandInfo info)
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(info.command, true);

		for (int i = 0; i < info.count; i++)
		{
			NetworkData obj = info.data.get(i);
			long timestamp = info.timestamps.get(i);

			DefaultMutableTreeNode readNode = new DefaultMutableTreeNode(new NetworkTreeItemWrapper(info, timestamp), true);
			addReadableToNode(readNode, obj);

			root.add(readNode);
		}

		return root;
	}

	private void addReadableToNode(DefaultMutableTreeNode readNode, NetworkData obj)
	{
		String[] keys = obj.getAllKeys();

		for (String key : keys)
		{
			Object objData = obj.dataMap.get(key);
			NetworkDataType typeData = obj.typeMap.get(key);

			switch (typeData)
			{
				case OBJECT:
				{
					DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(new NetworkTreeItemWrapper(objData, key), true);
					addReadableToNode(dataNode, (NetworkData) objData);
					readNode.add(dataNode);
					break;
				}
				case ARRAY:
				{
					NetworkData[] array = (NetworkData[]) objData;
					DefaultMutableTreeNode arrayNode = new DefaultMutableTreeNode(new NetworkTreeItemWrapper(array, key), true);
					for (int i = 0; i < array.length; i++)
					{
						Object item = array[i];

						DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(new NetworkTreeItemWrapper(item, i), true);
						addObjectToNode(dataNode, item);
						arrayNode.add(dataNode);
					}
					readNode.add(arrayNode);
					break;
				}
				case BYTE_ARRAY:
				{
					int byteDataLen = (int) objData;
					String nodeText = key + ": ByteArray[" + byteDataLen + "]";
					addObjectToNode(readNode, new NetworkTreeItemWrapper(objData, nodeText));
					break;
				}
				default:
				{
					String nodeText = key + ": " + objData;
					addObjectToNode(readNode, new NetworkTreeItemWrapper(objData, nodeText));
					break;
				}
			}
		}
	}

	private void addObjectToNode(DefaultMutableTreeNode dataNode, Object item)
	{
		if (item instanceof NetworkData)
		{
			addReadableToNode(dataNode, (NetworkData) item);
		}
		else
		{
			DefaultMutableTreeNode leafNode = new DefaultMutableTreeNode(item, false);
			dataNode.add(leafNode);
		}
	}

}
