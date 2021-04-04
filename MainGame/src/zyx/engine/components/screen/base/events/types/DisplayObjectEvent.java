package zyx.engine.components.screen.base.events.types;

import zyx.engine.components.screen.base.DisplayObject;

public abstract class DisplayObjectEvent<T extends Enum>
{
	private static int ID_COUNTER = 0;
	
	public int id;
	public boolean bubbles;
	public DisplayObject target;

	
	public T type;

	protected DisplayObjectEvent(T type)
	{
		this.type = type;
		
		id = ID_COUNTER++;
		bubbles = true;
	}
}
