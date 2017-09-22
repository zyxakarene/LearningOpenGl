package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import zyx.utils.FloatMath;
import zyx.utils.GameConstants;

class Projection
{

	private static final float WIDTH = GameConstants.GAME_WIDTH;
	private static final float HEIGHT = GameConstants.GAME_HEIGHT;

	static Matrix4f createPerspective(float fov, float near, float far, Matrix4f out)
	{
		float aspect = WIDTH / HEIGHT;

		far *= 30;

		float angle = (fov / 180.0f) * FloatMath.PI;
		float f = 1.0f / FloatMath.tan(angle * 0.5f);

		/* Note, matrices are accessed like 2D arrays in C.
         They are column major, i.e m[y][x] */
		out.setIdentity();
		out.m00 = f / aspect;
		out.m11 = f;
		out.m22 = (far + near) / (near - far);
		out.m23 = -1.0f;
		out.m32 = (2.0f * far * near) / (near - far);
		
		return out;
	}

	static Matrix4f createOrthographic(float near, float far)
	{
		float scale = 50f;
		
		float left = -WIDTH / scale;
		float right = WIDTH / scale;
		float top = HEIGHT / scale;
		float bottom = -HEIGHT / scale;

		Matrix4f OrthoMatrix = new Matrix4f();

		OrthoMatrix.m00 = 2.0f / (right - left);
		OrthoMatrix.m11 = 2.0f / (top - bottom);
		OrthoMatrix.m22 = -2.0f / (far - near);

		OrthoMatrix.m30 = -(right + left) / (right - left);
		OrthoMatrix.m31 = -(top + bottom) / (top - bottom);
		OrthoMatrix.m32 = -(far + near) / (far - near);
		OrthoMatrix.m33 = 1.0f;

		return OrthoMatrix;
	}
}
