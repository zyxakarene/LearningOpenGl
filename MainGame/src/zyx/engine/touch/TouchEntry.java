package zyx.engine.touch;

import java.util.ArrayList;
import zyx.engine.components.screen.base.DisplayObject;

class TouchEntry
{
	DisplayObject target;
	ArrayList<ITouched> touches;
	
	private TouchState lastState;
	private int lastX;
	private int lastY;
	private boolean lastCollision;

	public TouchEntry(DisplayObject target)
	{
		this.target = target;
		this.touches = new ArrayList<>();
		
		this.lastState = null;
		this.lastX = -1;
		this.lastY = -1;
	}

	void addListener(ITouched listener)
	{
		if (touches.contains(listener) == false)
		{
			touches.add(listener);
		}
	}

	void removeListener(ITouched listener)
	{
		touches.remove(listener);
	}

	void touch(TouchState state, boolean collision, TouchData data)
	{
		if (state != lastState || lastX != data.x || lastY != data.y || lastCollision != collision)
		{
			lastState = state;
			lastX = data.x;
			lastY = data.y;
			lastCollision = collision;
			
			for (ITouched touch : touches)
			{
				touch.onTouched(state, collision, data);
			}
			
			if (lastState == TouchState.RELEASE)
			{
				lastState = null;
				lastX = -1;
				lastY = -1;
				lastCollision = false;
			}
		}
	}
	
}
