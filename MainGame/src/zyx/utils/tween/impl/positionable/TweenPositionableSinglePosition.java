package zyx.utils.tween.impl.positionable;

import zyx.utils.interfaces.IPositionable;
import zyx.utils.tween.FloatTween;
import zyx.utils.tween.impl.TweenAxis;

public class TweenPositionableSinglePosition extends FloatTween<IPositionable>
{

	private TweenAxis axis;

	public TweenPositionableSinglePosition(TweenAxis axis)
	{
		this.axis = axis;
	}

	@Override
	protected void onEase(IPositionable target, Float value)
	{
		switch (axis)
		{
			case X:
			{
				target.setX(value);
				break;
			}
			case Y:
			{
				target.setX(value);
				break;
			}
			case Z:
			{
				target.setX(value);
				break;
			}
		}
	}

}
