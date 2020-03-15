package zyx.utils.tween.impl.positionable;

import org.lwjgl.util.vector.Vector3f;
import zyx.utils.interfaces.IPositionable;
import zyx.utils.tween.Vector3fTween;

public class TweenPositionableVectorPosition extends Vector3fTween<IPositionable>
{
	@Override
	protected void onEase(IPositionable target, Vector3f value)
	{
		target.setPosition(true, value);
	}

}
