package zyx.engine.components.screen.base.events.types.mouse;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.game.controls.input.MouseData;

public class MouseEvent extends DisplayObjectEvent<MouseEventType>
{
	public DisplayObject mouseTarget;
	
	public int x;
	public int y;
	
	public int dX;
	public int dY;

	public MouseEvent(MouseEventType type)
	{
		super(type);
	}
	
	public MouseEvent setup(DisplayObject target, MouseData mouseData)
	{
		this.mouseTarget = target;
		this.x = mouseData.x;
		this.y = mouseData.y;
		this.dX = mouseData.dX;
		this.dY = mouseData.dY;
		
		return this;
	}
}
