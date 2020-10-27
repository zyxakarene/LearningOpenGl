package zyx.engine.components.screen.base;

import zyx.engine.components.screen.base.docks.DockType;
import zyx.utils.geometry.IntRectangle;
import zyx.utils.geometry.Rectangle;

public class ContainerDock extends DisplayObjectContainer
{

	public final DockType type;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
			
	public ContainerDock(DockType type)
	{
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

		setup();
	}

	void getBounds(IntRectangle rect)
	{
		rect.x = x;
		rect.y = y;
		rect.width = width;
		rect.height = height;
	}
	
	protected void setup()
	{
		
	}
}
