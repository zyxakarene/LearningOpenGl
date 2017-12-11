package zyx.opengl.models.implementations.bones.animation;

import zyx.utils.DeltaTime;

public class AnimationController
{
	private static final String DEFAULT_ANIMATION = "idle";
	
	String animation;
	long animationStartedAt;

	public AnimationController()
	{
		setAnimation(DEFAULT_ANIMATION);
	}
	
	public final void setAnimation(String name)
	{
		animation = name;
		animationStartedAt = DeltaTime.getTimestamp();
	}

	@Override
	public String toString()
	{
		return animation;
	}
	
	
}
