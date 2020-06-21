package zyx.opengl.models.implementations.bones.animation;

import java.util.Collection;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.transform.JointTransform;
import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.FloatMath;
import zyx.utils.math.MatrixUtils;

class AnimationTransformer
{

	private static final Vector3f PREV_POS = new Vector3f();
	private static final Vector3f NEXT_POS = new Vector3f();
	private static final Quaternion PREV_ROT = new Quaternion();
	private static final Quaternion NEXT_ROT = new Quaternion();

	private static final Quaternion INTERPOLATED_ROT = new Quaternion();
	private static final Vector3f INTERPOLATED_POS = new Vector3f();
	private static final Matrix4f TRANSFORM_MATRIX = new Matrix4f();

	static void transform(HashMap<Byte, JointTransform> prevTransforms,
						  HashMap<Byte, JointTransform> nextTransforms,
						  HashMap<Byte, Joint> joints,
						  Byte[] keys,
						  float percentage,
						  HashMap<Byte, JointTransform> blendTransforms,
						  float blendPercentage)
	{
		for (byte key : keys)
		{
			Joint joint = joints.get(key);
			JointTransform prevTransform = prevTransforms.get(key);
			JointTransform nextTransform = nextTransforms.get(key);
			
			JointTransform blendTransform = null;
			if (blendTransforms != null)
			{
				blendTransform = blendTransforms.get(key);
			}

			if (prevTransform != null && nextTransform != null)
			{
				transform(prevTransform, nextTransform, joint, percentage, blendTransform, blendPercentage);
			}
		}
	}

	static void nullTransform(HashMap<Byte, Joint> jointMap)
	{
		TRANSFORM_MATRIX.setIdentity();

		Collection<Joint> jointCollection = jointMap.values();
		for (Joint joint : jointCollection)
		{
			joint.setTPose();
		}
	}

	private static void transform(JointTransform prevTransform,
								  JointTransform nextTransform, 
								  Joint joint, 
								  float percentage, 
								  JointTransform blendTransform, 
								  float blendPercentage)
	{
		prevTransform.getPosition(PREV_POS);
		nextTransform.getPosition(NEXT_POS);
		prevTransform.getRotation(PREV_ROT);
		nextTransform.getRotation(NEXT_ROT);

		lerp(PREV_POS, NEXT_POS, percentage, INTERPOLATED_POS);
		slerp(PREV_ROT, NEXT_ROT, percentage, INTERPOLATED_ROT);

		if (blendTransform != null)
		{
			blendTransform.getPosition(NEXT_POS);
			blendTransform.getRotation(NEXT_ROT);
			
			lerp(NEXT_POS, INTERPOLATED_POS, blendPercentage, INTERPOLATED_POS);
			slerp(NEXT_ROT, INTERPOLATED_ROT, blendPercentage, INTERPOLATED_ROT);
		}
		
		MatrixUtils.transformMatrix(INTERPOLATED_ROT, INTERPOLATED_POS, TRANSFORM_MATRIX);
		
		joint.setAnimationTransform(TRANSFORM_MATRIX);
	}

	private static void lerp(Vector3f from, Vector3f to, float fraction, Vector3f out)
	{
		out.x = from.x + fraction * (to.x - from.x);
		out.y = from.y + fraction * (to.y - from.y);
		out.z = from.z + fraction * (to.z - from.z);
	}
	
	private static Quaternion slerp(Quaternion start, Quaternion end, float fraction, Quaternion out)
	{
		float d = start.x * end.x + start.y * end.y + start.z * end.z + start.w * end.w;
		float absDot = d < 0.f ? -d : d;

		// Set the first and second scale for the interpolation
		float scale0 = 1f - fraction;
		float scale1 = fraction;

		// Check if the angle between the 2 quaternions was big enough to
		// warrant such calculations
		if ((1 - absDot) > 0.1f)
		{
			// Get the angle between the 2 quaternions,
			// and then store the sin() of that angle
			float angle = FloatMath.acos(absDot);
			float invSinTheta = 1f / FloatMath.sin(angle);

			// Calculate the scale for q1 and q2, according to the angle and
			// it's sine value
			scale0 = (FloatMath.sin((1f - fraction) * angle) * invSinTheta);
			scale1 = (FloatMath.sin((fraction * angle)) * invSinTheta);
		}

		if (d < 0)
		{
			scale1 = -scale1;
		}

		// Calculate the x, y, z and w values for the quaternion by using a
		// special form of linear interpolation for quaternions.
		out.x = (scale0 * start.x) + (scale1 * end.x);
		out.y = (scale0 * start.y) + (scale1 * end.y);
		out.z = (scale0 * start.z) + (scale1 * end.z);
		out.w = (scale0 * start.w) + (scale1 * end.w);

		return out;
	}
	
}
