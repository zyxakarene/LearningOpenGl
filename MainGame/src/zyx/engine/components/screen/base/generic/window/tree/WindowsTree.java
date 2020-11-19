package zyx.engine.components.screen.base.generic.window.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.utils.callbacks.ICallback;

public class WindowsTree<TData> extends DisplayObjectContainer
{
	private WindowsTreeNode<TData> root;
	
	private ICallback<TreeChangedData> hierachyChanged;
	private ArrayList<WindowsTreeRowRenderer<TData>> rowRenderers;

	public WindowsTree(WindowsTreeNode<TData> root)
	{
		hierachyChanged = this::onHierachyChanged;
		rowRenderers = new ArrayList<>();
		
		setRoot(root);
	}
	
	public final void setRoot(WindowsTreeNode<TData> newRoot)
	{
		if (root != null)
		{
			root.setHierachyCallback(null);
		}
		
		root = newRoot;
		root.setHierachyCallback(hierachyChanged);
		
		createTree();
	}

	private void onHierachyChanged(TreeChangedData data)
	{
		WindowsTreeNode node = data.node;
		
		if (node.isLeaf == false && node.isOpened == false && data.removed)
		{
			int startIndex = rowRenderers.indexOf(node.rowRenderer);
			int endIndex = rowRenderers.size() - 1;
			int startLevel = node.level;
			
			int len = rowRenderers.size();
			for (int i = startIndex + 1; i < len; i++)
			{
				WindowsTreeRowRenderer<TData> row = rowRenderers.get(i);
				int nextLevel = row.level;
				if (nextLevel <= startLevel)
				{
					endIndex = i - 1;
					break;
				}
			}
			
			for (int i = endIndex; i > startIndex; i--)
			{
				WindowsTreeRowRenderer<TData> row = rowRenderers.remove(i);
				row.removeFromParent(true);
			}
			
			positionRows();
		}
		else
		{
			createTree();
		}
	}

	private void createTree()
	{
		removeChildren(true);
		rowRenderers.clear();
		
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
			rowRenderers.add(row);
			node.rowRenderer = row;
			
			y += row.getRendererHeight();
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();
		
		if (rowRenderers != null)
		{
			rowRenderers.clear();
			rowRenderers = null;
		}
	}

	private void positionRows()
	{
		float y = 0;
		
		int len = rowRenderers.size();
		for (int i = 0; i < len; i++)
		{
			WindowsTreeRowRenderer<TData> row = rowRenderers.get(i);
			row.setPosition(true, row.level * 16, y);
			y += row.getRendererHeight();
		}
	}
}
