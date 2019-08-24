package zyx.utils.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class MatrixUtils
{

	private static Vector3f HELPER_SCALE = new Vector3f();
	private static Vector3f HELPER_X = new Vector3f();
	private static Vector3f HELPER_Y = new Vector3f();
	private static Vector3f HELPER_Z = new Vector3f();
	
	private static Vector3f HELPER_SCALE_X = new Vector3f();
	private static Vector3f HELPER_SCALE_Y = new Vector3f();
	private static Vector3f HELPER_SCALE_Z = new Vector3f();

	/**
	 * Transform the given matrix by the quarternion rotation and the Vector3f translation<br>
	 * Copied from javax.vecmath.Matrix4f
	 *
	 * @param out The matrix to apply the transformation
	 * @param rotation The rotational Quaternion to use
	 * @param translation The translations to use
	 */
	public static void transformMatrix(Quaternion rotation, Vector3f translation, Matrix4f out)
	{
		out.m00 = (1f - 2f * rotation.y * rotation.y - 2f * rotation.z * rotation.z);
		out.m01 = (2f * (rotation.x * rotation.y + rotation.w * rotation.z));
		out.m02 = (2f * (rotation.x * rotation.z - rotation.w * rotation.y));

		out.m10 = (2f * (rotation.x * rotation.y - rotation.w * rotation.z));
		out.m11 = (1f - 2f * rotation.x * rotation.x - 2f * rotation.z * rotation.z);
		out.m12 = (2f * (rotation.y * rotation.z + rotation.w * rotation.x));

		out.m20 = (2f * (rotation.x * rotation.z + rotation.w * rotation.y));
		out.m21 = (2f * (rotation.y * rotation.z - rotation.w * rotation.x));
		out.m22 = (1f - 2f * rotation.x * rotation.x - 2f * rotation.y * rotation.y);

		out.m30 = translation.x;
		out.m31 = translation.y;
		out.m32 = translation.z;

		out.m03 = 0f;
		out.m13 = 0f;
		out.m23 = 0f;
		out.m33 = 1f;
	}

	/*
		[RT.x] [UP.x] [BK.x] [POS.x]
		[RT.y] [UP.y] [BK.y] [POS.y]
		[RT.z] [UP.z] [BK.z] [POS.Z]
		[    ] [    ] [    ] [US   ]
	 */
	public static void getRightFrom(Matrix4f matrix, Vector3f out)
	{
		out.x = matrix.m00;
		out.y = matrix.m01;
		out.z = matrix.m02;
	}

	public static void getUpFrom(Matrix4f matrix, Vector3f out)
	{
		out.x = matrix.m10;
		out.y = matrix.m11;
		out.z = matrix.m12;
	}

	public static void getDirFrom(Matrix4f matrix, Vector3f out)
	{
		out.x = matrix.m20;
		out.y = matrix.m21;
		out.z = matrix.m22;
	}

	public static void setDirTo(Matrix4f matrix, Vector3f dir)
	{
		Vector3f up;
		if (dir.x == 0 && FloatMath.abs(dir.y) == 1 && dir.z == 0)
		{
			up = GeometryUtils.ROTATION_Y;
		}
		else
		{
			up = GeometryUtils.ROTATION_Z;
		}

		dir.normalise();

		Vector3f right = Vector3f.cross(up, dir, null);
		if (right.length() <= 0)
		{
			return;
		}
		right.normalise();
		
		Vector3f upCalc = Vector3f.cross(dir, right, null);
		upCalc.normalise();
		
		getScaleFrom(matrix, HELPER_SCALE);

		matrix.m00 = right.x * HELPER_SCALE.x;
		matrix.m01 = right.y * HELPER_SCALE.x;
		matrix.m02 = right.z * HELPER_SCALE.x;

		matrix.m10 = upCalc.x * HELPER_SCALE.y;
		matrix.m11 = upCalc.y * HELPER_SCALE.y;
		matrix.m12 = upCalc.z * HELPER_SCALE.y;

		matrix.m20 = dir.x * HELPER_SCALE.z;
		matrix.m21 = dir.y * HELPER_SCALE.z;
		matrix.m22 = dir.z * HELPER_SCALE.z;
		
	}

	public static void getPositionFrom(Matrix4f matrix, Vector3f out)
	{
		out.x = matrix.m30;
		out.y = matrix.m31;
		out.z = matrix.m32;
	}

	public static void getScaleFrom(Matrix4f mat, Vector3f out)
	{
		HELPER_SCALE_X.set(mat.m00, mat.m01, mat.m02);
		HELPER_SCALE_Y.set(mat.m10, mat.m11, mat.m12);
		HELPER_SCALE_Z.set(mat.m20, mat.m21, mat.m22);

		out.x = HELPER_SCALE_X.length();
		out.y = HELPER_SCALE_Y.length();
		out.z = HELPER_SCALE_Z.length();
	}
}
