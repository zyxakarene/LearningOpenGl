package zyx.engine.components.screen.base.generic.window.list;

import zyx.engine.components.screen.base.DisplayObjectContainer;

public abstract class WindowsListRenderer<TData> extends DisplayObjectContainer
{
	private TData data;
	private int index;
	
	private boolean selected;
	
	public final void setData(TData value)
	{
		if (data != value)
		{
			data = value;
			onDataChanged(data);
		}
	}
	
	public final void setSelected(boolean value)
	{
		if (selected != value)
		{
			selected = value;
			OnSelectionChanged(selected);
		}
	}

	public int getIndex()
	{
		return index;
	}
	
	public void refresh()
	{
		onRefresh(data, selected);
	}
	
	protected void onRefresh(TData data, boolean selected)
	{
		
	}

	protected abstract void onDataChanged(TData data);

	protected abstract void OnSelectionChanged(boolean selected);
}
