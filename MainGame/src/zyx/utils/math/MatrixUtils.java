package zyx.utils.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class MatrixUtils
{

	/**
	 * Transform the given matrix by the quarternion rotation and the Vector3f translation<br>
	 * Copied from javax.vecmath.Matrix4f
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

	public static void getPositionFrom(Matrix4f matrix, Vector3f out)
	{
		out.x = matrix.m30;
		out.y = matrix.m31;
		out.z = matrix.m32;
	}

	public static void getScaleFrom(Matrix4f mat, Vector3f out)
	{
		Vector3f x = new Vector3f(mat.m00, mat.m01, mat.m02);
		Vector3f y = new Vector3f(mat.m10, mat.m11, mat.m12);
		Vector3f z = new Vector3f(mat.m20, mat.m21, mat.m22);
		
		out.x = x.length();
		out.y = y.length();
		out.z = z.length();
	}
}
