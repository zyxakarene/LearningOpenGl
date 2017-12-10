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
}
