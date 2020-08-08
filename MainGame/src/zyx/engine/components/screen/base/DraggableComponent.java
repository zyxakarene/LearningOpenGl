package zyx.engine.components.screen.base;

import org.lwjgl.util.vector.Vector2f;
import zyx.engine.touch.ITouched;
import zyx.engine.touch.TouchData;
import zyx.engine.touch.TouchState;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IDisposeable;

public class DraggableComponent implements ITouched, IDisposeable
{
	private static final Vector2f HELPER_POS = new Vector2f();

	private DisplayObject component;
	private DisplayObject moving;
	
	private float downPosX;
	private float downPosY;

	public DraggableComponent(DisplayObject draggable, DisplayObject moveable)
	{
		component = draggable;
		component.addTouchListener(this);
		moving = moveable;
	}

	@Override
	public void onTouched(TouchState state, boolean collided, TouchData data)
	{
		if (state == TouchState.DOWN)
		{
			moving.getPosition(false, HELPER_POS);
			downPosX = data.x - HELPER_POS.x;
			downPosY = data.y - HELPER_POS.y;
		}
		else if (state == TouchState.DRAG)
		{
			moving.setPosition(false, data.x - downPosX, data.y - downPosY);
		}
	}

	@Override
	public void dispose()
	{
		if (component != null)
		{
			component.removeTouchListener(this);
			component = null;
		}
		
		moving = null;
	}
}
