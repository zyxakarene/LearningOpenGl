package zyx.utils.tween.impl.positionable;

import zyx.utils.interfaces.IPositionable;
import zyx.utils.tween.FloatTween;

public class TweenPositionableSingleScale extends FloatTween<IPositionable>
{
	@Override
	protected void onEase(IPositionable target, Float value)
	{
		target.setScale(value, value, value);
	}

}
