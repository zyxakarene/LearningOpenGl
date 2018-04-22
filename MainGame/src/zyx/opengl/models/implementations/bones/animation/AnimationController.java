package zyx.opengl.models.implementations.bones.animation;

import zyx.utils.DeltaTime;
import zyx.utils.interfaces.IUpdateable;

public class AnimationController implements IUpdateable
{
	private static final String DEFAULT_ANIMATION = "idle";
	
	String animation;
	long animationStartedAt;
	long timeSinceStarted;

	public AnimationController()
	{
		setAnimation(DEFAULT_ANIMATION);
	}
	
	public final void setAnimation(String name)
	{
		animation = name;
		animationStartedAt = DeltaTime.getTimestamp();
		timeSinceStarted = 0;
	}

	@Override
	public String toString()
	{
		return animation;
	}

	public String getAnimation()
	{
		return animation;
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		timeSinceStarted += elapsedTime;
	}
	
	
}
