package zyx.engine.components.screen.base.generic.window.tree;

import java.util.ArrayList;
import java.util.List;
import zyx.engine.utils.callbacks.ICallback;

public class WindowsTreeNode<TData>
{
	private static final TreeChangedData TREE_CHANGED_DATA = new TreeChangedData();
	
	boolean isLeaf;
	boolean isOpened;
	byte level;
	TData data;
	WindowsTreeNode<TData> parent;
	WindowsTreeRowRenderer<TData> rowRenderer;
	
	ArrayList<WindowsTreeNode<TData>> children;
	
	private ICallback<TreeChangedData> onHierachyChanged;
	
	public WindowsTreeNode(TData data)
	{
		this.data = data;
		this.children = new ArrayList<>();
		
		isOpened = false;
		level = 1;
		isLeaf = true;
	}

	void setHierachyCallback(ICallback<TreeChangedData> callback)
	{
		onHierachyChanged = callback;
	}
	
	void toggleOpened()
	{
		isOpened = !isOpened;
		
		TREE_CHANGED_DATA.node = this;
		TREE_CHANGED_DATA.type = isOpened ? TreeChangedType.Opening : TreeChangedType.Closing;
		onHierachyChanged(TREE_CHANGED_DATA);
	}
	
	protected DefaultWindowsTreeRenderer<TData> createRenderer()
	{
		return new DefaultWindowsTreeRenderer<>();
	}
	
	public TData getData()
	{
		return data;
	}
	
	public void addChild(WindowsTreeNode<TData> child)
	{
		if (child.parent != null)
		{
			child.parent.removeChild(child);
		}
		
		boolean wasLeaf = isLeaf;
		
		children.add(child);
		child.parent = this;
		child.setLevel((byte) (level + 1));
		isLeaf = false;
		
		TREE_CHANGED_DATA.node = wasLeaf ? this : child;
		TREE_CHANGED_DATA.type = TreeChangedType.Addition;
		onHierachyChanged(TREE_CHANGED_DATA);
	}
	
	private void setLevel(byte level)
	{
		this.level = level;
		for (WindowsTreeNode<TData> child : children)
		{
			child.setLevel((byte) (level + 1));
		}
	}
	
	public void removeChild(WindowsTreeNode<TData> child)
	{
		children.remove(child);
		child.parent = null;
		child.level = 0;
		isLeaf = children.isEmpty();
		
		TREE_CHANGED_DATA.node = isLeaf ? this : child;
		TREE_CHANGED_DATA.type = TreeChangedType.Removal;
		onHierachyChanged(TREE_CHANGED_DATA);
	}
	
	void getRowRenderers(List<WindowsTreeRowRenderer<TData>> out)
	{
		if (rowRenderer != null)
		{
			out.add(rowRenderer);
			
			for (WindowsTreeNode<TData> child : children)
			{
				child.getRowRenderers(out);
			}
		}
	}
	
	public void getChildren(List<WindowsTreeNode<TData>> out)
	{
		getChildren(out, false);
	}
	
	public void getChildren(List<WindowsTreeNode<TData>> out, boolean includeGrandChildren)
	{
		out.addAll(children);
		
		if (includeGrandChildren)
		{
			for (WindowsTreeNode<TData> child : children)
			{
				child.getChildren(out, includeGrandChildren);
			}
		}
	}
	
	public WindowsTreeNode<TData> getParent()
	{
		return parent;
	}
	
	public void removeFromParent()
	{
		if (parent != null)
		{
			parent.removeChild(this);
			parent = null;
		}
	}
	
	public boolean isLeaf()
	{
		return isLeaf;
	}
	
	void removeRenderer(boolean fromSelf, boolean fromChildren)
	{
		if (fromChildren)
		{
			for (WindowsTreeNode<TData> child : children)
			{
				child.removeRenderer(true, true);
			}
		}
		
		if (fromSelf && rowRenderer != null)
		{
			rowRenderer.dispose();
			rowRenderer = null;
		}
	}

	private void onHierachyChanged(TreeChangedData originNode)
	{
		if (parent != null)
		{
			parent.onHierachyChanged(originNode);
		}
		
		if (onHierachyChanged != null)
		{
			onHierachyChanged.onCallback(originNode);
		}
	}

	boolean isLastChild(WindowsTreeNode node)
	{
		int size = children.size();
		if (size == 0)
		{
			return false;
		}
		
		return children.get(size - 1) == node;
	}
}
