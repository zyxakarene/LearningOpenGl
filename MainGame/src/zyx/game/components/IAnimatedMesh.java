package zyx.game.components;

import zyx.engine.utils.callbacks.ICallback;

public interface IAnimatedMesh
{
	void addAnimationCompletedCallback(ICallback<String> callback);
	void removeAnimationCompletedCallback(ICallback<String> callback);
	void setAnimation(String animation);
}
