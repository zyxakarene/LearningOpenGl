package zyx.utils.tween;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.FloatMath;

public class Linear
{

	private static final Quaternion HELPER_QUAT = new Quaternion();

	Linear()
	{
	}

	public void lerp(Vector3f from, Vector3f to, float fraction, Vector3f out)
	{
		out.x = from.x + fraction * (to.x - from.x);
		out.y = from.y + fraction * (to.y - from.y);
		out.z = from.z + fraction * (to.z - from.z);
	}
	
	public float lerp(float from, float to, float fraction)
	{
		return from + fraction * (to - from);
	}

	public Quaternion slerp(Quaternion start, Quaternion end, float fraction, Quaternion out)
	{
		if (out == null)
		{
			out = HELPER_QUAT;
		}

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
