package zyx.engine.touch;

import zyx.engine.components.screen.base.ITouched;
import zyx.game.controls.input.MouseData;
import zyx.utils.interfaces.IUpdateable;

public class MouseTouchManager implements IUpdateable
{

	private static final int LONG_PRESS_TIME = 1000;

	private static final MouseTouchManager instance = new MouseTouchManager();

	private TouchData data;
	private TouchState currentState;
	private MouseData mouseData;
	private int pressTimer;

	private MouseTouchManager()
	{
		currentState = TouchState.HOVER;
		mouseData = MouseData.data;
		pressTimer = 0;
		data = new TouchData();
	}

	public static MouseTouchManager getInstance()
	{
		return instance;
	}

	public TouchState currentState()
	{
		return currentState;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		boolean leftDown = mouseData.isLeftDown();
		boolean hasMovement = mouseData.dX != 0 || mouseData.dY != 0;
		TouchState newState = currentState;
		boolean update = false;

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
				newState = TouchState.HOVER;
				break;
			}
			case HOVER:
			{
				if (leftDown)
				{
					pressTimer = 0;
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

		if (update || newState != currentState)
		{
			currentState = newState;
			
			data.x = mouseData.x;
			data.y = mouseData.y;
			data.dX = mouseData.dX;
			data.dY = mouseData.dY;
		}
	}
}
