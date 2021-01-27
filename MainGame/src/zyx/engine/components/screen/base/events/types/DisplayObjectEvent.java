package zyx.engine.components.screen.base.events.types;

public class DisplayObjectEvent<T extends Enum>
{
	private static int ID_COUNTER = 0;
	
	public int id;
	public boolean bubbles;
	
	public final T type;

	public DisplayObjectEvent(T type)
	{
		this.type = type;
		
		id = ID_COUNTER++;
		bubbles = true;
	}
}
