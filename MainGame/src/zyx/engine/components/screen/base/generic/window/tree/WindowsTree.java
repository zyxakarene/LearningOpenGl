package zyx.engine.components.screen.base.generic.window.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.components.screen.base.generic.window.scroll.IScrollableView;
import zyx.engine.utils.callbacks.ICallback;

public class WindowsTree<TData> extends DisplayObjectContainer implements IScrollableView
{
	private WindowsTreeNode<TData> root;
	
	private ICallback<TreeChangedData> hierachyChanged;

	public WindowsTree(WindowsTreeNode<TData> root)
	{
		hierachyChanged = this::onHierachyChanged;
		
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
		
		createTreeFrom(root);
	}

	private void onHierachyChanged(TreeChangedData data)
	{
		WindowsTreeNode node = data.node;
		
		switch (data.type)
		{
			case Closing:
			{
				node.removeRenderer(false, true);
				break;
			}
			case Removal:
			{
				node.removeRenderer(true, true);
				break;
			}
			case Addition:
			case Opening:
			{
				if (node == root || node.parent != null && node.parent.isOpened)
				{
					createTreeFrom(node);	
				}
				break;
			}
			default:
				createTreeFrom(root);
				break;
		}
		
		positionRows();
		
		if (changed != null)
		{
			changed.onCallback(getTotalHeight());
		}
	}

	private void createTreeFrom(WindowsTreeNode<TData> start)
	{
		start.removeRenderer(true, true);
		
		LinkedList<WindowsTreeNode<TData>> nodes = new LinkedList<>();
		nodes.add(start);
		
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
			node.rowRenderer = row;
			
			y += row.getRendererHeight();
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}

	private void positionRows()
	{
		List<WindowsTreeRowRenderer<TData>> renderesList = new ArrayList<>();
		root.getRowRenderers(renderesList);
		
		float y = 0;
		
		int len = renderesList.size();
		for (int i = 0; i < len; i++)
		{
			WindowsTreeRowRenderer<TData> row = renderesList.get(i);
			row.setPosition(true, row.level * 16, y);
			y += row.getRendererHeight();
		}
	}

	@Override
	public int getTotalHeight()
	{
		return (int) getHeight();
	}

	private ICallback<Integer> changed;
	
	@Override
	public void addHeightChangedListener(ICallback<Integer> callback)
	{
		changed = callback;
	}

	@Override
	public void removeHeightChangedListener()
	{
		changed = null;
	}
}
