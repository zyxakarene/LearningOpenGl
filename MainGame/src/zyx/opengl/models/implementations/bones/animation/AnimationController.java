package zyx.opengl.models.implementations.bones.animation;

import zyx.engine.components.animations.IAnimateableMesh;
import zyx.engine.components.animations.MeshAnimator;
import zyx.engine.utils.callbacks.CustomCallback;
import zyx.engine.utils.callbacks.ICallback;
import zyx.utils.DeltaTime;

public class AnimationController implements IAnimateableMesh
{
	private static final String DEFAULT_ANIMATION = "idle";
	
	private CustomCallback<String> animationCompleted;
	
	String animation;
	long animationStartedAt;
	long timeSinceStarted;
	
	String lastAnimation;
	long timeSinceLastAninmation;
	int blendTimer;

	public AnimationController()
	{
		setAnimation(DEFAULT_ANIMATION);
		
		MeshAnimator.getInstance().addAnimatedMesh(this);
		animationCompleted = new CustomCallback<>();
	}
	
	public final void setAnimation(String name)
	{
		blendTimer = 0;
		lastAnimation = animation;
		timeSinceLastAninmation = timeSinceStarted;
		animationStartedAt = DeltaTime.getTimestamp();
		
		animation = name;
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
		blendTimer += elapsedTime;
	}

	@Override
	public void dispose()
	{
		MeshAnimator.getInstance().removeAnimatedMesh(this);
		
		if (animationCompleted != null)
		{
			animationCompleted.dispose();
			animationCompleted = null;
		}
	}

	public void clearBlend()
	{
		lastAnimation = null;
		timeSinceLastAninmation = 0;
	}

	void animationLooped()
	{
		animationCompleted.dispatch(animation);
	}
	
	public void addAnimationCompletedCallback(ICallback<String> callback)
	{
		animationCompleted.addCallback(callback);
	}
	
	public void removeAnimationCompletedCallback(ICallback<String> callback)
	{
		animationCompleted.removeCallback(callback);
	}
}
