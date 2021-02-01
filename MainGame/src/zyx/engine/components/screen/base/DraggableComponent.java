package zyx.engine.components.screen.base;

import org.lwjgl.util.vector.Vector2f;
import zyx.engine.components.screen.base.events.types.touch.IMouseDownListener;
import zyx.engine.components.screen.base.events.types.touch.IMouseDraggedListener;
import zyx.engine.components.screen.base.events.types.touch.TouchEvent;
import zyx.utils.interfaces.IDisposeable;

public class DraggableComponent implements IDisposeable
{
	private static final Vector2f HELPER_POS = new Vector2f();

	private DisplayObject component;
	private DisplayObject moving;
	
	private float downPosX;
	private float downPosY;
	
	private IMouseDownListener downListener;
	private IMouseDraggedListener dragListener;

	public DraggableComponent(DisplayObject draggable, DisplayObject moveable)
	{
		component = draggable;
		moving = moveable;
		
		downListener = this::onMouseDown;
		dragListener = this::onMouseDragged;
		
		component.addListener(downListener);
		component.addListener(dragListener);
	}

	private void onMouseDown(TouchEvent event)
	{
		moving.getPosition(false, HELPER_POS);
		downPosX = event.x - HELPER_POS.x;
		downPosY = event.y - HELPER_POS.y;
	}

	private void onMouseDragged(TouchEvent event)
	{
		moving.setPosition(false, event.x - downPosX, event.y - downPosY);
	}
	
	@Override
	public void dispose()
	{
		if (downListener != null)
		{
			component.removeListener(downListener);
			downListener = null;
		}
		
		if (dragListener != null)
		{
			component.removeListener(dragListener);
			dragListener = null;
		}
		
		moving = null;
		component = null;
	}
}
