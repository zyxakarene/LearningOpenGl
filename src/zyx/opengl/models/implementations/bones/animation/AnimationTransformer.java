package zyx.opengl.models.implementations.bones.animation;

import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.transform.JointTransform;
import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

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
		Quaternion slerp = JointTransform.slerp(prevTransform.q, nextTransform.q, percentage);
		
		Vector3f euler = JointTransform.toEuler3(slerp);
		prevTransform.lerpToTwo(nextTransform, percentage, euler, LERP_MATRIX);
		
		System.out.println("Slerp: " + slerp);
//		prevTransform.lerpTo(nextTransform, percentage, LERP_MATRIX);
		joint.setAnimationTransform(LERP_MATRIX);
		
		System.out.println();
	}

}
