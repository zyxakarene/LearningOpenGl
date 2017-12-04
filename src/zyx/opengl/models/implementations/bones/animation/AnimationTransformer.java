package zyx.opengl.models.implementations.bones.animation;

import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.transform.JointTransform;
import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.math.MatrixUtils;
import zyx.utils.tween.TweenUtils;

class AnimationTransformer
{

	private static final Vector3f PREV_POS = new Vector3f();
	private static final Vector3f NEXT_POS = new Vector3f();
	private static final Quaternion PREV_ROT = new Quaternion();
	private static final Quaternion NEXT_ROT = new Quaternion();
	
	private static final Quaternion INTERPOLATED_ROT = new Quaternion();
	private static final Vector3f INTERPOLATED_POS = new Vector3f();
	private static final Matrix4f TRANSFORM_MATRIX = new Matrix4f();

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
		prevTransform.getPosition(PREV_POS);
		nextTransform.getPosition(NEXT_POS);
		prevTransform.getRotation(PREV_ROT);
		nextTransform.getRotation(NEXT_ROT);
		
		TweenUtils.LINEAR.lerp(PREV_POS, NEXT_POS, percentage, INTERPOLATED_POS);
		TweenUtils.LINEAR.slerp(PREV_ROT, NEXT_ROT, percentage, INTERPOLATED_ROT);
		
		MatrixUtils.transformMatrix(INTERPOLATED_ROT, INTERPOLATED_POS, TRANSFORM_MATRIX);
		joint.setAnimationTransform(TRANSFORM_MATRIX);
	}
}
