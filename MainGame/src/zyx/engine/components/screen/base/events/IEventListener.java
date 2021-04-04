package zyx.engine.components.screen.base.events;

import zyx.engine.components.screen.base.events.types.DisplayObjectEvent;
import zyx.engine.components.screen.base.events.types.IDisplayObjectEventListener;

public interface IEventListener
{
	void addListener(IDisplayObjectEventListener listener);
	void removeListener(IDisplayObjectEventListener listener);
	void dispatchEvent(DisplayObjectEvent event);
}
