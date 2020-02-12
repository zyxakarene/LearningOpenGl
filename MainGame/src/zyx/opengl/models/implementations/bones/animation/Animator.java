package zyx.opengl.models.implementations.bones.animation;

import java.util.HashMap;

import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.interfaces.IDisposeable;

public class Animator implements IDisposeable
{

	private Animation currentAnimation;

	private HashMap<String, Joint> joints;
	private HashMap<String, Animation> animations;
	private long timeSinceStarted;
	private String[] keys;

	public Animator(HashMap<String, Joint> joints, HashMap<String, Animation> animations)
	{
		this.joints = joints;
		this.animations = animations;
		
		keys = new String[joints.size()];
		joints.keySet().toArray(keys);
	}

	public void setCurrentAnimation(AnimationController controller)
	{
		currentAnimation = animations.get(controller.animation);
		timeSinceStarted = controller.timeSinceStarted;
	}

	public void update()
	{
		if (currentAnimation != null)
		{
			float timeSinceStart = timeSinceStarted;

			int animationLength = currentAnimation.length;

			float currentFrame = (timeSinceStart / GameConstants.ANIMATION_MS_PER_FRAME);

			if (currentAnimation.looping)
			{
				currentFrame = currentFrame % animationLength;
			}
			else if (currentFrame >= animationLength)
			{
				currentFrame = animationLength - 1;
			}
			
			int prevFrame = FloatMath.floor(currentFrame);
			int nextFrame = FloatMath.ceil(currentFrame);

			if (nextFrame >= animationLength)
			{
				nextFrame = currentAnimation.looping ? 0 : animationLength - 1;
			}

			float percentage = currentFrame - prevFrame;

			AnimationFrame prevAnimationFrame = currentAnimation.frames[prevFrame];
			AnimationFrame nextAnimationFrame = currentAnimation.frames[nextFrame];

			AnimationTransformer.transform(prevAnimationFrame.transforms, nextAnimationFrame.transforms, joints, keys, percentage);
		}
		else
		{
			AnimationTransformer.nullTransform(joints);
		}
	}

	@Override
	public void dispose()
	{
		currentAnimation = null;
		joints = null;
		animations = null;
	}
}
