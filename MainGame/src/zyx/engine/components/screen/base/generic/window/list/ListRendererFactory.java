package zyx.engine.components.screen.base.generic.window.list;

import java.util.LinkedList;
import zyx.utils.interfaces.IDisposeable;

class ListRendererFactory<TData> implements IDisposeable
{

	private Class<? extends WindowsListRenderer<TData>> renderType;
	private LinkedList<WindowsListRenderer<TData>> rendererCache;
	
	ListRendererFactory(Class<? extends WindowsListRenderer<TData>> rendererClass)
	{
		renderType = rendererClass;
		rendererCache = new LinkedList<>();
	}
	
	WindowsListRenderer<TData> getRenderer()
	{
		if (rendererCache.size() > 0)
		{
			return rendererCache.removeLast();
		}
		
		try
		{
			return renderType.newInstance();
		}
		catch (InstantiationException | IllegalAccessException ex)
		{
			ex.printStackTrace();
		}
		
		return null;
	}
	
	void returnRenderer(WindowsListRenderer<TData> renderer)
	{
		rendererCache.add(renderer);
	}

	@Override
	public void dispose()
	{
		if (rendererCache != null)
		{
			for (WindowsListRenderer<TData> renderer : rendererCache)
			{
				renderer.dispose();
			}
			
			rendererCache.clear();
			rendererCache = null;
		}
		
		renderType = null;
	}

}
