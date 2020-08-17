package zyx.engine.components.screen.base.generic.window.scroll;

import zyx.engine.utils.callbacks.ICallback;

public interface IScrollableView
{
	int getTotalHeight();
	void addHeightChangedListener(ICallback<Integer> callback);
	void removeHeightChangedListener();
}
