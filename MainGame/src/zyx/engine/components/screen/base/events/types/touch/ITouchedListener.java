package zyx.engine.components.screen.base.events.types.touch;

import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;

public interface ITouchedListener extends IDisplayObjectEventListener<TouchEventType, TouchEvent>
{
	void mouseEnter(TouchEvent event);
	void mouseLeave(TouchEvent event);
	void mouseDown(TouchEvent event);
	void mouseUp(TouchEvent event);
	void mouseClick(TouchEvent event);
}
