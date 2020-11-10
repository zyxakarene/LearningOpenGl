package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import zyx.engine.utils.ScreenSize;
import zyx.utils.FloatMath;

public class Projection
{

	public static Matrix4f createPerspective(float screenWidth, float screenheight, float fov, float near, float far, Matrix4f out)
	{
		float aspect = screenWidth / screenheight;

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

	public static Matrix4f createOrthographic(float near, float far, float scale, Matrix4f out)
	{
		final float WIDTH = ScreenSize.windowWidth;
		final float HEIGHT = ScreenSize.windowHeight;

		float left = -WIDTH / scale;
		float right = WIDTH / scale;
		float top = HEIGHT / scale;
		float bottom = -HEIGHT / scale;

		return createOrthographic(near, far, left, right, top, bottom, out);
	}

	public static Matrix4f createOrthographic(float near, float far, float left, float right, float top, float bottom, Matrix4f out)
	{
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
