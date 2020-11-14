package zyx.engine.components.screen.base.generic.window.tree;

import java.util.LinkedList;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.utils.callbacks.ICallback;

public class WindowsTree<TData> extends DisplayObjectContainer implements ICallback<WindowsTreeNode>
{
	private WindowsTreeNode<TData> root;

	public WindowsTree(WindowsTreeNode<TData> root)
	{
		setRoot(root);
	}
	
	public final void setRoot(WindowsTreeNode<TData> newRoot)
	{
		if (root != null)
		{
			root.setHierachyCallback(null);
		}
		
		root = newRoot;
		root.setHierachyCallback(this);
		
		createTree();
	}

	@Override
	public void onCallback(WindowsTreeNode data)
	{
		createTree();
	}

	private void createTree()
	{
		removeChildren(true);
		
		LinkedList<WindowsTreeNode<TData>> nodes = new LinkedList<>();
		nodes.add(root);
		
		float y = 0;
		
		while (nodes.isEmpty() == false)
		{			
			WindowsTreeNode<TData> node = nodes.removeFirst();
			if (node.isOpened)
			{
				nodes.addAll(0, node.children);
			}

			WindowsTreeRowRenderer<TData> row = new WindowsTreeRowRenderer<>(node);
			addChild(row);
			row.setPosition(true, node.level * 16, y);
			
			y += row.getRendererHeight();
		}
	}
}
