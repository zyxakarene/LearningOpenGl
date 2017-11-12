package dev.bones.animation;

import dev.bones.skeleton.Joint;
import dev.bones.transform.JointTransform;
import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;

class AnimationTransformer
{

	private static final Matrix4f LERP_MATRIX = new Matrix4f();

	static void transform(HashMap<String, JointTransform> prevTransforms, HashMap<String, JointTransform> nextTransforms, HashMap<String, Joint> joints, float percentage)
	{
		String[] keys = new String[prevTransforms.size()];
		prevTransforms.keySet().toArray(keys);

		for (String key : keys)
		{
			Joint joint = joints.get(key);
			JointTransform prevTransform = prevTransforms.get(key);
			JointTransform nextTransform = nextTransforms.get(key);

			transform(prevTransform, nextTransform, joint, percentage);
		}
	}

	private static void transform(JointTransform prevTransform, JointTransform nextTransform, Joint joint, float percentage)
	{
		prevTransform.lerpTo(nextTransform, percentage, LERP_MATRIX);
		joint.setAnimationTransform(LERP_MATRIX);
	}

}
