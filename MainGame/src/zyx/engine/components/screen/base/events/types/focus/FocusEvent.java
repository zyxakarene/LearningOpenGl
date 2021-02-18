package zyx.engine.components.screen.base.events.types.focus;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.game.controls.input.PressedKey;

public class FocusEvent extends DisplayObjectEvent<FocusEventType>
{
	public DisplayObject target;
	public boolean hasFocus;
	
	public PressedKey key;
	
	public FocusEvent(FocusEventType type)
	{
		super(type);
	}
	
	public FocusEvent setup(DisplayObject target, boolean hasFocus)
	{
		return setup(target, hasFocus, null);
	}
	
	public FocusEvent setup(DisplayObject target, boolean hasFocus, PressedKey key)
	{
		this.target = target;
		this.hasFocus = hasFocus;
		this.key = key;
		
		return this;
	}
}
