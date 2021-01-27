package zyx.engine.components.screen.base.events.types.touch;

import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;

public abstract class TouchedAdaptor implements ITouchedListener
{

	@Override
	public final Class<? extends IDisplayObjectEventListener> getTypeClass()
	{
		return ITouchedListener.class;
	}
	
	@Override
	public void mouseEnter(TouchEvent event)
	{
	}

	@Override
	public void mouseLeave(TouchEvent event)
	{
	}

	@Override
	public void mouseDown(TouchEvent event)
	{
	}

	@Override
	public void mouseUp(TouchEvent event)
	{
	}

	@Override
	public void mouseClick(TouchEvent event)
	{
	}
}
