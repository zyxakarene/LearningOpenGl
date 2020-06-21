package zyx.opengl.models.implementations.bones.animation;

import java.util.HashMap;

import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.transform.JointTransform;
import zyx.utils.DeltaTime;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IDisposeable;

public class Animator implements IDisposeable
{

	private Animation currentAnimation;

	private HashMap<Byte, Joint> joints;
	private HashMap<String, Animation> animations;
	private long timeSinceStarted;
	private Byte[] keys;

	private Animation lastAnimation;
	private long timeSinceLastStarted;
	private long timeAtChange;

	public Animator(HashMap<Byte, Joint> joints, HashMap<String, Animation> animations)
	{
		this.joints = joints;
		this.animations = animations;

		keys = new Byte[joints.size()];
		joints.keySet().toArray(keys);
	}

	public void setCurrentAnimation(AnimationController controller)
	{
		lastAnimation = animations.get(controller.lastAnimation);
		timeSinceLastStarted = controller.timeSinceLastAninmation;
		timeAtChange = controller.timeAtChange;

		currentAnimation = animations.get(controller.animation);
		timeSinceStarted = controller.timeSinceStarted;
	}

	public void update()
	{
		if (currentAnimation != null)
		{
			float blendPercent = 0f;
			HashMap<Byte, JointTransform> blendTransforms = null;

			if (lastAnimation != null)
			{
				float timeSinceLastStart = timeSinceLastStarted;
				int lastAnimationLength = lastAnimation.length;
				float lastFrame = (timeSinceLastStart / GameConstants.ANIMATION_MS_PER_FRAME);

				if (lastAnimation.looping)
				{
					lastFrame = lastFrame % lastAnimationLength;
				}
				else if (lastFrame >= lastAnimationLength)
				{
					lastFrame = lastAnimationLength - 1;
				}

				int prevFrame = FloatMath.floor(lastFrame);

				float timeSinceBlend = DeltaTime.getTimestamp() - timeAtChange;
				blendPercent = timeSinceBlend / 500f;
				Print.out(blendPercent);
				AnimationFrame blendAnimationFrame = lastAnimation.frames[prevFrame];
				blendTransforms = blendAnimationFrame.transforms;
			}

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

			AnimationTransformer.transform(prevAnimationFrame.transforms, nextAnimationFrame.transforms, joints, keys, percentage, blendTransforms, blendPercent);
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
		keys = null;
	}
}
