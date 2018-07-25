package zyx.opengl.models.implementations.bones.animation;

import zyx.engine.components.animations.IAnimateableMesh;
import zyx.engine.components.animations.MeshAnimator;
import zyx.utils.DeltaTime;

public class AnimationController implements IAnimateableMesh
{
	private static final String DEFAULT_ANIMATION = "idle";
	
	String animation;
	long animationStartedAt;
	long timeSinceStarted;

	public AnimationController()
	{
		setAnimation(DEFAULT_ANIMATION);
		
		MeshAnimator.getInstance().addAnimatedMesh(this);
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

	@Override
	public void dispose()
	{
		MeshAnimator.getInstance().removeAnimatedMesh(this);
	}
}
