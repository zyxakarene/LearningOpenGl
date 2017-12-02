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
	public static boolean random;

	private float x, y, z;
	private float rotX, rotY, rotZ;

	private Matrix4f matrix;
	
	private Quaternion q;

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

		q = setEulerAngles(rotX, rotY, rotZ);
		
		transform(matrix);
	}
	
	private static Quaternion setEulerAngles (float x, float y, float z)
	{
		return setEulerAnglesRad(FloatMath.toRadians(x), FloatMath.toRadians(y), FloatMath.toRadians(z));
	}

	private static Quaternion setEulerAnglesRad (float x, float y, float z)
	{
		final float hr = z * 0.5f;
		final float shr = (float)Math.sin(hr);
		final float chr = (float)Math.cos(hr);
		final float hp = x * 0.5f;
		final float shp = (float)Math.sin(hp);
		final float chp = (float)Math.cos(hp);
		final float hy = y * 0.5f;
		final float shy = (float)Math.sin(hy);
		final float chy = (float)Math.cos(hy);
		final float chy_shp = chy * shp;
		final float shy_chp = shy * chp;
		final float chy_chp = chy * chp;
		final float shy_shp = shy * shp;

		x = (chy_shp * chr) + (shy_chp * shr); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
		y = (shy_chp * chr) - (chy_shp * shr); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
		z = (chy_chp * shr) - (shy_shp * chr); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
		float w = (chy_chp * chr) + (shy_shp * shr); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
		
		return new Quaternion(x, y, z, w);
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

	public void lerpTo(JointTransform next, float percent, Matrix4f out)
	{
		LERP.x = Linear.lerp(x, next.x, percent);
		LERP.y = Linear.lerp(y, next.y, percent);
		LERP.z = Linear.lerp(z, next.z, percent);
		LERP.rotX = Linear.lerp(rotX, next.rotX, percent);
		LERP.rotY = Linear.lerp(rotY, next.rotY, percent);
		LERP.rotZ = Linear.lerp(rotZ, next.rotZ, percent);

		out.setIdentity();
		LERP.transform(out);
	}

	public Matrix4f getMatrix()
	{
		return matrix;
	}
}
