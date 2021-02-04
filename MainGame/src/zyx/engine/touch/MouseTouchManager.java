package zyx.engine.touch;

import zyx.engine.components.screen.base.DisplayObject;
import zyx.engine.components.screen.base.EventCache;
import zyx.engine.components.screen.base.events.types.mouse.MouseEventType;
import zyx.engine.curser.CursorManager;
import zyx.engine.curser.GameCursor;
import zyx.game.controls.input.MouseData;
import zyx.utils.interfaces.IUpdateable;

public class MouseTouchManager implements IUpdateable
{

	private static final MouseTouchManager INSTANCE = new MouseTouchManager();

	private MouseEventType currentState;
	private MouseData mouseData;
	private boolean forceUpdate;

	private DisplayObject nextTarget;
	private DisplayObject currentTarget;
	private DisplayObject mouseDownTarget;
	private boolean enabled;

	private MouseTouchManager()
	{
		currentState = MouseEventType.Up;
		mouseData = MouseData.data;
		enabled = true;
	}

	public static MouseTouchManager getInstance()
	{
		return INSTANCE;
	}

	public MouseEventType currentState()
	{
		return currentState;
	}

	public void setEnabled(boolean value)
	{
		if (currentTarget != null)
		{
			currentTarget.dispatchEvent(EventCache.get(MouseEventType.Up).setup(currentTarget, mouseData));
			currentTarget.dispatchEvent(EventCache.get(MouseEventType.Exit).setup(currentTarget, mouseData));

			currentTarget = null;
		}

		enabled = value;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		boolean leftDown = mouseData.isLeftDown();
		boolean hasMovement = mouseData.dX != 0 || mouseData.dY != 0;
		MouseEventType newState = currentState;

		if (mouseData.grabbed || !enabled)
		{
			return;
		}

		switch (currentState)
		{
			case Up:
			case Enter:
			{
				if (leftDown)
				{
					nextTarget = currentTarget;
					mouseDownTarget = currentTarget;
					newState = MouseEventType.Down;
				}
				else
				{
					mouseDownTarget = null;
				}
				break;
			}
			case Exit:
			{
				newState = MouseEventType.Up;
				break;
			}
			case Down:
			{
				if (hasMovement)
				{
					newState = MouseEventType.Drag;
				}
				else if (!leftDown)
				{
					newState = MouseEventType.Click;
				}
				break;
			}
			case Click:
			{
				newState = MouseEventType.Up;
				break;
			}
			case Drag:
			{
				if (!leftDown)
				{
					if (currentTarget == nextTarget)
					{
						newState = MouseEventType.Click;
					}
					else
					{
						newState = MouseEventType.Up;
					}
				}
				else
				{
					forceUpdate = true;
				}
				break;
			}

		}

		if (forceUpdate || newState != currentState)
		{
			forceUpdate = false;
			currentState = newState;

			if (mouseDownTarget != null)
			{
				mouseDownTarget.dispatchEvent(EventCache.get(currentState).setup(mouseDownTarget, mouseData));
			}
			else if (currentTarget != null)
			{
				currentTarget.dispatchEvent(EventCache.get(currentState).setup(currentTarget, mouseData));
			}
		}
	}

	public void setTouchedObject(DisplayObject target)
	{
		if (currentState == MouseEventType.Drag)
		{
			//Don't change target while dragging

			nextTarget = target;
			setCursor(mouseDownTarget);
			return;
		}

		if (target == null && currentTarget != null)
		{
			//Going from something to nothing
			currentTarget.dispatchEvent(EventCache.get(MouseEventType.Exit).setup(currentTarget, mouseData));
			currentTarget = null;

			CursorManager.getInstance().setCursor(GameCursor.POINTER);
		}
		else if (target != null)
		{
			//Hitting something, anything

			if (target != currentTarget)
			{
				//Going from something to something else
				if (currentTarget != null)
				{
					currentTarget.dispatchEvent(EventCache.get(MouseEventType.Exit).setup(currentTarget, mouseData));
				}

				currentTarget = target;
				currentTarget.dispatchEvent(EventCache.get(MouseEventType.Enter).setup(currentTarget, mouseData));
			}

			setCursor(target);
		}
	}

	private void setCursor(DisplayObject target)
	{
		if (target != null && target.hoverIcon != null)
		{
			//The target needs a hover icon
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
