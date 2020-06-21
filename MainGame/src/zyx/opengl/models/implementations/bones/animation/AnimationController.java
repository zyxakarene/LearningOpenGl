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
	
	String lastAnimation;
	long timeSinceLastAninmation;
	long timeAtChange;
	int blendTimer;

	public AnimationController()
	{
		setAnimation(DEFAULT_ANIMATION);
		
		MeshAnimator.getInstance().addAnimatedMesh(this);
	}
	
	public final void setAnimation(String name)
	{
		blendTimer = 0;
		lastAnimation = animation;
		timeSinceLastAninmation = timeSinceStarted;
		timeAtChange = DeltaTime.getTimestamp();
		
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
		
		if (lastAnimation != null)
		{
			blendTimer += elapsedTime;
			
			if (blendTimer >= 500)
			{
				lastAnimation = null;
				timeSinceLastAninmation = 0;
			}
		}
	}

	@Override
	public void dispose()
	{
		MeshAnimator.getInstance().removeAnimatedMesh(this);
	}

	public void clearBlend()
	{
		lastAnimation = null;
		timeSinceLastAninmation = 0;
	}
}
