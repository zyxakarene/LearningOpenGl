package zyx.engine.components.screen.base.events.types.size;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.utils.math.Vector2Int;

public class SizeEvent extends DisplayObjectEvent<SizeEventType>
{
	public final Vector2Int size;
	
	public SizeEvent(SizeEventType type)
	{
		super(type);
		size = new Vector2Int();
		bubbles = false;
	}
	
	public SizeEvent setup(DisplayObject target, int width, int height)
	{
		this.target = target;
		size.x = width;
		size.y = height;
		
		return this;
	}
}
