package zyx.engine.components.screen.base;

import zyx.engine.components.screen.base.docks.DockType;
import zyx.utils.geometry.IntRectangle;
import zyx.utils.geometry.Rectangle;
import zyx.utils.interfaces.IUpdateable;

public abstract class ContainerDock extends DisplayObjectContainer implements IUpdateable
{

	public final DockType type;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	private boolean hasSetup;
			
	public ContainerDock(DockType type)
	{
		hasSetup = false;
		name = getClass().getSimpleName();
		this.type = type;
	}
			
	public final void setBounds(int x, int width, int y, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setPosition(true, this.x, this.y);
		clipRect = new Rectangle(0, 0, width, height);

		if (hasSetup)
		{
			onSizeChanged();
		}
		else
		{
			hasSetup = true;
			setup();
		}
		
	}

	void getBounds(IntRectangle rect)
	{
		rect.x = x;
		rect.y = y;
		rect.width = width;
		rect.height = height;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
	}
	
	protected abstract void setup();
	
	protected abstract void onSizeChanged();
}
