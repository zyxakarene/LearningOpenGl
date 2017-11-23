package dev.bones.transform;

import org.lwjgl.util.vector.Matrix4f;
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

	private JointTransform()
	{
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

		transform(matrix);
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
