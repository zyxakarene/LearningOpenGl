package zyx.utils.math;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.FloatMath;

public class QuaternionUtils
{

	public static Quaternion toQuat(Vector3f radians, Quaternion out)
	{
		if (out == null)
		{
			out = new Quaternion();
		}

		float c = (float) Math.cos(radians.x / 2f);
		float d = (float) Math.cos(radians.y / 2f);
		float e = (float) Math.cos(radians.z / 2f);
		float f = (float) Math.sin(radians.x / 2f);
		float g = (float) Math.sin(radians.y / 2f);
		float h = (float) Math.sin(radians.z / 2f);

		out.x = f * d * e - c * g * h;
		out.y = c * g * e + f * d * h;
		out.z = c * d * h - f * g * e;
		out.w = c * d * e + f * g * h;

		return out;
	}

	public static Vector3f toRad(Quaternion quat, Vector3f out)
	{
		if (out == null)
		{
			out = new Vector3f();
		}

		float[] mat = toRotationMatrix(quat);
		float a = mat[0];
		float f = mat[4];
		float g = mat[8];
		float h = mat[1];
		float k = mat[5];
		float l = mat[9];
		float m = mat[2];
		float n = mat[6];
		float e = mat[10];

		float clamped = FloatMath.clamp(m, -1, 1);
		
		out.y = FloatMath.asin(-clamped);
		if (0.99999 > Math.abs(m))
		{
			out.x = FloatMath.atan2(n, e);
			out.z = FloatMath.atan2(h, a);
		}
		else
		{
			out.x = 0;
			out.z = FloatMath.atan2(-f, k);
		}

		return out;
	}

	private static float[] toRotationMatrix(Quaternion quat)
	{
		float c = quat.x;
		float d = quat.y;
		float e = quat.z;
		float f = quat.w;
		float g = c + c;
		float h = d + d;
		float k = e + e;
		float a = c * g;
		float l = c * h;
		float m = d * h;
		c = c * k;
		d = d * k;
		e = e * k;
		g = f * g;
		h = f * h;
		f = f * k;

		float[] mat = new float[16];
		mat[0] = 1 - (m + e);
		mat[4] = l - f;
		mat[8] = c + h;
		mat[1] = l + f;
		mat[5] = 1 - (a + e);
		mat[9] = d - g;
		mat[2] = c - h;
		mat[6] = d + g;
		mat[10] = 1 - (a + m);
		mat[3] = 0;
		mat[7] = 0;
		mat[11] = 0;
		mat[12] = 0;
		mat[13] = 0;
		mat[14] = 0;
		mat[15] = 1;

		return mat;
	}
}
