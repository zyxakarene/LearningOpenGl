package zyx.engine.touch;

import java.util.ArrayList;
import zyx.engine.components.screen.base.DisplayObject;

class TouchEntry
{
	DisplayObject target;
	ArrayList<ITouched> touches;

	public TouchEntry(DisplayObject target)
	{
		this.target = target;
		this.touches = new ArrayList<>();
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
	
}
