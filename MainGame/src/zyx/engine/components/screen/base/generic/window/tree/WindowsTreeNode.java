package zyx.engine.components.screen.base.generic.window.tree;

import java.util.ArrayList;
import zyx.engine.utils.callbacks.ICallback;

public class WindowsTreeNode<TData>
{
	boolean isLeaf;
	boolean isOpened;
	byte level;
	TData data;
	
	ArrayList<WindowsTreeNode<TData>> children;
	private WindowsTreeNode<TData> parent;
	
	private ICallback<WindowsTreeNode> onHierachyChanged;
	
	public WindowsTreeNode(TData data)
	{
		this.data = data;
		this.children = new ArrayList<>();
		
		isOpened = true;
		level = 0;
		isLeaf = true;
	}

	void setHierachyCallback(ICallback<WindowsTreeNode> callback)
	{
		onHierachyChanged = callback;
	}
	
	void toggleOpened()
	{
		isOpened = !isOpened;
		onHierachyChanged();
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
		
		children.add(child);
		child.parent = this;
		child.level = (byte) (level + 1);
		isLeaf = false;
		
		onHierachyChanged();
	}
	
	public void removeChild(WindowsTreeNode<TData> child)
	{
		children.remove(child);
		child.parent = null;
		child.level = 0;
		isLeaf = children.isEmpty();
		
		onHierachyChanged();
	}
	
	public boolean isLeaf()
	{
		return isLeaf;
	}
	
	void destroy()
	{
		if (children != null)
		{
			for (WindowsTreeNode<TData> child : children)
			{
				child.destroy();
			}
		}
		
		children = null;
		data = null;
	}

	private void onHierachyChanged()
	{
		if (parent != null)
		{
			parent.onHierachyChanged();
		}
		
		if (onHierachyChanged != null)
		{
			onHierachyChanged.onCallback(this);
		}
	}
}
