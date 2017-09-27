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

	static Matrix4f createOrthographic(float near, float far, Matrix4f out)
	{
		float left = -WIDTH / 2;
		float right = WIDTH / 2;
		float top = HEIGHT / 2;
		float bottom = -HEIGHT / 2;

		out.setIdentity();

		out.m00 = 2.0f / (right - left);
		out.m11 = 2.0f / (top - bottom);
		out.m22 = -2.0f / (far - near);

		out.m30 = -(right + left) / (right - left);
		out.m31 = -(top + bottom) / (top - bottom);
		out.m32 = -(far + near) / (far - near);
		out.m33 = 1.0f;

		return out;
	}
}
