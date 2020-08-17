package zyx.engine.components.screen.base.generic.window.list;

import zyx.engine.components.screen.base.generic.VList;
import zyx.engine.components.screen.base.generic.window.scroll.IScrollableView;
import zyx.engine.utils.callbacks.ICallback;

public class WindowsList<TData> extends VList implements IScrollableView
{
	private static final WindowsListRenderer[] NO_RENDERERS = new WindowsListRenderer[0];
	private static final Object[] NO_DATA = new Object[0];
	
	private ICallback<Integer> scrollListener;
	
	private ListRendererFactory<TData> factory;
	private TData[] data;
	private WindowsListRenderer[] renderers;
	
	public WindowsList()
	{
		renderers = NO_RENDERERS;
	}
	
	public void setRenderer(Class<? extends WindowsListRenderer<TData>> renderer)
	{
		if (factory != null)
		{
			factory.dispose();
		}
		
		factory = new ListRendererFactory<>(renderer);
	}
	
	public void setData(TData[] dataArray)
	{
		clean();
		
		data = dataArray;
		int len = data.length;
		renderers = new WindowsListRenderer[len];
		
		for (int i = 0; i < len; i++)
		{
			TData dataEntry = data[i];
			
			WindowsListRenderer<TData> renderer = factory.getRenderer();
			renderer.setData(dataEntry);
			
			addChild(renderer);
			renderers[i] = renderer;
		}
		
		if (scrollListener != null)
		{
			int height = getTotalHeight();
			scrollListener.onCallback(height);
		}
	}

	private void clean()
	{
		for (WindowsListRenderer renderer : renderers)
		{
			factory.returnRenderer(renderer);
			removeChild(renderer);
		}
		
		renderers = NO_RENDERERS;
	}

	@Override
	public int getTotalHeight()
	{
		if (renderers.length == 0)
		{
			return 0;
		}
		else
		{
			WindowsListRenderer renderer = renderers[0];
			return (int) (renderer.getHeight() * renderers.length);
		}
	}

	@Override
	public void addHeightChangedListener(ICallback<Integer> callback)
	{
		scrollListener = callback;
	}

	@Override
	public void removeHeightChangedListener()
	{
		scrollListener = null;
	}
}
