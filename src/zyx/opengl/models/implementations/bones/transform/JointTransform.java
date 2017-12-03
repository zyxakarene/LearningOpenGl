package zyx.opengl.models.implementations.bones.transform;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;
import zyx.utils.tween.Linear;

public class JointTransform
{

	private static final JointTransform LERP = new JointTransform();
	private static final Vector3f SHARED_POSITION = new Vector3f();
	private static final Vector3f SHARED_ROTATION = new Vector3f();

	private float x, y, z;
	private float rotX, rotY, rotZ;

	private Matrix4f matrix;
	
	public Quaternion q;

	private JointTransform()
	{
		q = new Quaternion(0, 0, 0, 1);
	}

	public JointTransform(float x, float y, float z, float rotX, float rotY, float rotZ)
	{
		
		this.x = x;
		this.y = y;
		this.z = z;

		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;

		this.matrix = new Matrix4f();

		q = setEulerAnglesZYX(rotX, rotY, rotZ); //YZX order
		
		transform(matrix);
	}
	
	/**
	 * Must be order ZYX
	 */
	private static Quaternion setEulerAnglesZYX (float x, float y, float z)
	{
		Quaternion q = new Quaternion();
		
		float c = FloatMath.cos(x / 2f);
        float d = FloatMath.cos(y / 2f);
        float e = FloatMath.cos(z / 2f);
        float f = FloatMath.sin(x / 2f);
        float g = FloatMath.sin(y / 2f);
        float h = FloatMath.sin(z / 2f);
		
		q.x = f * d * e - c * g * h;
		q.y = c * g * e + f * d * h;
		q.z = c * d * h - f * g * e;
		q.w = c * d * e + f * g * h;
		
		return q;
	}
	/**
	 * Must be order YZX
	 */
	private static Quaternion setEulerAnglesRad (float y, float x, float z)
	{
		final float hr = z * 0.5f;
		final float shr = FloatMath.sin(hr);
		final float chr = FloatMath.cos(hr);
		final float hp = x * 0.5f;
		final float shp = FloatMath.sin(hp);
		final float chp = FloatMath.cos(hp);
		final float hy = y * 0.5f;
		final float shy = FloatMath.sin(hy);
		final float chy = FloatMath.cos(hy);
		final float chy_shp = chy * shp;
		final float shy_chp = shy * chp;
		final float chy_chp = chy * chp;
		final float shy_shp = shy * shp;

		x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
		y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
		z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
		float w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
		
		return new Quaternion(x, -y, z, w);
	}
	
	private static final Vector3f VEC = new Vector3f();
	public static Vector3f toEuler(Quaternion q1)
	{
		float test = q1.x*q1.y + q1.z*q1.w;
		if (test > 0.499) { // singularity at north pole
			VEC.x = 2 * FloatMath.atan2(q1.x,q1.w);
			VEC.y = FloatMath.PI/2;
			VEC.z = 0;
			return VEC;
		}
		if (test < -0.499) { // singularity at south pole
			VEC.x = -2 * FloatMath.atan2(q1.x,q1.w);
			VEC.y = - FloatMath.PI/2;
			VEC.z = 0;
			return VEC;
		}
		float sqx = q1.x*q1.x;
		float sqy = q1.y*q1.y;
		float sqz = q1.z*q1.z;
		VEC.x = FloatMath.atan2(2*q1.y*q1.w-2*q1.x*q1.z , 1 - 2*sqy - 2*sqz);
		VEC.y = FloatMath.asin(2*test);
		VEC.z = FloatMath.atan2(2*q1.x*q1.w-2*q1.y*q1.z , 1 - 2*sqx - 2*sqz);
		
		return VEC;
	}
	
	public static Vector3f toEuler2(Quaternion q)
	{
		// roll (x-axis rotation)
		float sinr = 2.0f * (q.w * q.x + q.y * q.z);
		float cosr = 1.0f - 2.0f * (q.x * q.x + q.y * q.y);
		VEC.x = FloatMath.atan2(sinr, cosr);

		// pitch (y-axis rotation)
		float sinp = 2.0f * (q.w * q.y - q.z * q.x);
		float absSin = sinp >= 0 ? sinp : -sinp;
		if (absSin >= 1)
		{
			// use 90 degrees if out of range
			if (sinp < 0)
			{
				VEC.y = -(FloatMath.PI / 2);
			}
			else
			{
				VEC.y = FloatMath.PI / 2;
			}
		} 
		else
		{
			VEC.y = FloatMath.asin(sinp);
		}

		// yaw (z-axis rotation)
		float siny = 2.0f * (q.w * q.z + q.x * q.y);
		float cosy = 1.0f - 2.0f * (q.y * q.y + q.z * q.z);  
		VEC.z = FloatMath.atan2(siny, cosy);
		
		return VEC;
	}
	
	public static Vector3f toEuler3(Quaternion q)
	{
		float[] res = new float[3];
		// for rotSeq zyx, 
		// x = res[0], y = res[1], z = res[2]

		threeaxisrot( 2*(q.x*q.y + q.w*q.z),
                     q.w*q.w + q.x*q.x - q.y*q.y - q.z*q.z,
                    -2*(q.x*q.z - q.w*q.y),
                     2*(q.y*q.z + q.w*q.x),
                     q.w*q.w - q.x*q.x - q.y*q.y + q.z*q.z,
                     res);
		
		VEC.set(res[0], res[1], res[2]);
		return VEC;
	}
	
	private static void threeaxisrot(float r11, float r12, float r21, float r31, float r32, float res[])
	{
		res[0] = FloatMath.atan2( r31, r32 );
		res[1] = FloatMath.asin ( r21 );
		res[2] = FloatMath.atan2( r11, r12 );
	 }
	
	private static final Quaternion RESULT = new Quaternion();
	public static Quaternion slerp (Quaternion start, Quaternion end, float alpha)
	{
		final float d = start.x * end.x + start.y * end.y + start.z * end.z + start.w * end.w;
		float absDot = d < 0.f ? -d : d;

		// Set the first and second scale for the interpolation
		float scale0 = 1f - alpha;
		float scale1 = alpha;

		// Check if the angle between the 2 quaternions was big enough to
		// warrant such calculations
		if ((1 - absDot) > 0.1) {// Get the angle between the 2 quaternions,
			// and then store the sin() of that angle
			final float angle = FloatMath.acos(absDot);
			final float invSinTheta = 1f / (float)FloatMath.sin(angle);

			// Calculate the scale for q1 and q2, according to the angle and
			// it's sine value
			scale0 = (FloatMath.sin((1f - alpha) * angle) * invSinTheta);
			scale1 = (FloatMath.sin((alpha * angle)) * invSinTheta);
		}

		if (d < 0.f) scale1 = -scale1;

		// Calculate the x, y, z and w values for the quaternion by using a
		// special form of linear interpolation for quaternions.
		RESULT.x = (scale0 * start.x) + (scale1 * end.x);
		RESULT.y = (scale0 * start.y) + (scale1 * end.y);
		RESULT.z = (scale0 * start.z) + (scale1 * end.z);
		RESULT.w = (scale0 * start.w) + (scale1 * end.w);
		
		return RESULT;
	}

	private void transform(Matrix4f mat)
	{
		SHARED_POSITION.set(x, y, z);
		SHARED_ROTATION.set(rotX, rotY, rotZ);
		mat.translate(SHARED_POSITION);
		mat.rotate(SHARED_ROTATION.z, GeometryUtils.ROTATION_Z);
		mat.rotate(SHARED_ROTATION.y, GeometryUtils.ROTATION_Y);
		mat.rotate(SHARED_ROTATION.x, GeometryUtils.ROTATION_X);
	}

	public void lerpToTwo(JointTransform next, float percent, Vector3f rot, Matrix4f out)
	{
		LERP.x = Linear.lerp(x, next.x, percent);
		LERP.y = Linear.lerp(y, next.y, percent);
		LERP.z = Linear.lerp(z, next.z, percent);
		LERP.rotX = rot.x;
		LERP.rotY = rot.y;
		LERP.rotZ = rot.z;
		
		System.out.println("Initial ROT: " + rotX + ", " + rotY + ", " + rotZ);
		System.out.println("Initial " + q);

		
		System.out.println("QuatToEuler: " + rot);
		
		out.setIdentity();
		LERP.transform(out);
	}
	
	public void lerpTo(JointTransform next, float percent, Matrix4f out)
	{
		LERP.x = Linear.lerp(x, next.x, percent);
		LERP.y = Linear.lerp(y, next.y, percent);
		LERP.z = Linear.lerp(z, next.z, percent);
		LERP.rotX = Linear.lerp(rotX, next.rotX, percent);
		LERP.rotY = Linear.lerp(rotY, next.rotY, percent);
		LERP.rotZ = Linear.lerp(rotZ, next.rotZ, percent);

		System.out.println("Euler: " + rotX + ", " + rotY + ", " + rotZ);
		
		out.setIdentity();
		LERP.transform(out);
	}

	public Matrix4f getMatrix()
	{
		return matrix;
	}
}
