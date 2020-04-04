package zyx.engine.touch;

import java.util.HashMap;
import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.DisplayObjectContainer;
import zyx.engine.curser.CursorManager;
import zyx.game.controls.input.MouseData;
import zyx.utils.interfaces.IUpdateable;

public class MouseTouchManager implements IUpdateable
{

	private static final int LONG_PRESS_TIME = 1000;

	private static final MouseTouchManager INSTANCE = new MouseTouchManager();

	private TouchData data;
	private TouchState currentState;
	private MouseData mouseData;
	private int pressTimer;
	private boolean forceUpdate;
	
	private HashMap<DisplayObject, TouchEntry> touchListeners;
	
	private DisplayObject currentTarget;
	private DisplayObject mouseDownTarget;
	private boolean hasDownTarget;
	private boolean enabled;

	private MouseTouchManager()
	{
		currentState = TouchState.HOVER;
		mouseData = MouseData.data;
		pressTimer = 0;
		data = new TouchData();
		touchListeners = new HashMap<>();
		enabled = true;
	}

	public static MouseTouchManager getInstance()
	{
		return INSTANCE;
	}

	public TouchState currentState()
	{
		return currentState;
	}

	public void setEnabled(boolean value)
	{
		if (currentTarget != null)
		{
			dispatchTo(currentTarget, currentState, false);
			currentState = TouchState.RELEASE;
			
			currentTarget = null;
		}
		
		enabled = value;
	}
	
	public void registerTouch(DisplayObject target, ITouched listener)
	{
		if (touchListeners.containsKey(target) == false)
		{
			touchListeners.put(target, new TouchEntry(target));
		}
		
		TouchEntry entry = touchListeners.get(target);
		entry.addListener(listener);
	}
	
	public void unregisterTouch(DisplayObject target, ITouched listener)
	{
		TouchEntry entry = touchListeners.get(target);
		
		if (entry != null)
		{
			entry.removeListener(listener);
			
			if (entry.touches.isEmpty())
			{
				touchListeners.remove(target);
			}
		}
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		boolean leftDown = mouseData.isLeftDown();
		boolean hasMovement = mouseData.dX != 0 || mouseData.dY != 0;
		TouchState newState = currentState;
		boolean update = false;

		if(mouseData.grabbed || !enabled)
		{
			return;
		}
		
		switch (currentState)
		{
			case LONG_PRESSED:
			{
				newState = TouchState.LONG_PRESS_WAIT;
				break;
			}
			case CLICK:
			case RELEASE:
			{
				mouseDownTarget = null;
				hasDownTarget = false;
				newState = TouchState.HOVER;
				break;
			}
			case HOVER:
			{
				if (leftDown)
				{
					pressTimer = 0;
					mouseDownTarget = currentTarget;
					hasDownTarget = true;
					newState = TouchState.DOWN;
				}
				break;
			}
			case DOWN:
			{
				if (leftDown)
				{
					pressTimer += elapsedTime;

					if (hasMovement)
					{
						newState = TouchState.DRAG;
					}
					else if (pressTimer >= LONG_PRESS_TIME)
					{
						newState = TouchState.LONG_PRESSED;
					}
				}
				else
				{
					newState = TouchState.CLICK;
				}
				break;
			}
			case DRAG:
			case LONG_PRESS_WAIT:
			{
				update = leftDown && hasMovement;
				if (!leftDown)
				{
					newState = TouchState.RELEASE;
				}
				break;
			}
		}

		if (forceUpdate || update || newState != currentState)
		{
			forceUpdate = false;
			currentState = newState;
			
			data.x = mouseData.x;
			data.y = mouseData.y;
			data.dX = mouseData.dX;
			data.dY = mouseData.dY;
			
			if (hasDownTarget)
			{
				if (mouseDownTarget != null)
				{
					boolean collision = (mouseDownTarget == currentTarget);
					dispatchTo(mouseDownTarget, currentState, collision);
				}
			}
			else if (currentTarget != null)
			{
				dispatchTo(currentTarget, currentState, true);
			}
		}
	}

	public void setTouchedObject(DisplayObject target)
	{
		if (!hasDownTarget && currentTarget != null && currentTarget != target)
		{
			dispatchTo(currentTarget, currentState, false);
		}
		
		currentTarget = target;
		forceUpdate = true;
	}

	private void dispatchTo(DisplayObject target, TouchState state, boolean collision)
	{
		TouchEntry entry = touchListeners.get(target);
		
		if (entry != null)
		{
			for (ITouched touch : entry.touches)
			{
				touch.onTouched(state, collision, data);
			}
		}
		
		DisplayObjectContainer parent = target.getParent();
		if (parent != null)
		{
			dispatchTo(parent, state, collision);
		}
		
		if (target.focusable && target.hoverIcon != null)
		{
			CursorManager.getInstance().setCursor(target.hoverIcon);
		}
	}
	
	public boolean hasTarget()
	{
		return currentTarget != null;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
}
