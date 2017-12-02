package zyx.opengl.models.implementations.bones.animation;

import java.util.HashMap;

import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.utils.DeltaTime;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.interfaces.IUpdateable;

public class Animator implements IUpdateable
{
	private long animationStartedAt;
	private float currentFrame;
	private Animation currentAnimation;
	
	private final HashMap<String, Joint> joints;
	private final HashMap<String, Animation> animations;

	public Animator(HashMap<String, Joint> joints, HashMap<String, Animation> animations)
	{
		this.joints = joints;
		this.animations = animations;
		this.currentFrame = 0;
		this.animationStartedAt = 0;
	}
	
	public void setCurrentAnimation(String name)
	{
		currentAnimation = animations.get(name);
		currentFrame = 0;
		animationStartedAt = DeltaTime.getTimestamp();
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		if (currentAnimation != null)
		{
			float timeSinceStart = (int) (timestamp - animationStartedAt);
			int animationLength = currentAnimation.length;
			
			currentFrame = (timeSinceStart / GameConstants.ANIMATION_MS_PER_FRAME) % animationLength;
			
			int prevFrame = FloatMath.floor(currentFrame);
			int nextFrame = FloatMath.ceil(currentFrame);
			
			if (nextFrame >= animationLength)
			{
				nextFrame = currentAnimation.loopable ? 0 : animationLength - 1;
			}
			
			float percentage = currentFrame - prevFrame;
			
			System.out.println(percentage + "% between frame " + prevFrame + " and " + nextFrame);
			
			AnimationFrame prevAnimationFrame = currentAnimation.frames[prevFrame];
			AnimationFrame nextAnimationFrame = currentAnimation.frames[nextFrame];
			
			AnimationTransformer.transform(prevAnimationFrame.transforms, nextAnimationFrame.transforms, joints, percentage);
		}
	}
}
