package zyx.engine.components.animations;

import zyx.engine.components.screen.base.events.IEventListener;

public interface IFocusable extends IEventListener
{
	void onKeyPressed(char character);
	void onFocused();
	void onUnFocused();
}
