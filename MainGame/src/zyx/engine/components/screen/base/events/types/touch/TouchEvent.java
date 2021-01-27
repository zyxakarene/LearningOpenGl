package zyx.engine.components.screen.base.events.types.touch;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;

public class TouchEvent extends DisplayObjectEvent<TouchEventType>
{
	public DisplayObject target;
	
	public int x;
	public int y;
	
	public int dX;
	public int dY;

	public TouchEvent(TouchEventType type, DisplayObject target)
	{
		this(type, target, 0, 0, 0, 0);
	}

	public TouchEvent(TouchEventType type, DisplayObject target, int x, int y)
	{
		this(type, target, x, y, 0, 0);
	}

	public TouchEvent(TouchEventType type, DisplayObject target, int x, int y, int dX, int dY)
	{
		super(type);
		
		this.target = target;
		this.x = x;
		this.y = y;
		this.dX = dX;
		this.dY = dY;
	}
}
