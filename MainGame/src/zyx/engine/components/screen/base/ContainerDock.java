package zyx.engine.components.screen.base;

import zyx.utils.geometry.Rectangle;

public class ContainerDock extends DisplayObjectContainer
{

	public int x;
	public int y;
	public int width;
	public int height;
			
	public ContainerDock()
	{
		name = getClass().getSimpleName();
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

	protected void setup()
	{
		
	}
}
