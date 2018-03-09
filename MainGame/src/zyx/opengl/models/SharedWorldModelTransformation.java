package zyx.opengl.models;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;

public class SharedWorldModelTransformation
{
	private static final Vector3f SHARED_ROTATION = new Vector3f(0, 0, 0);
	private static final Vector3f SHARED_POSITION = new Vector3f(0, 0, 0);
	private static final Vector3f SHARED_SCALE = new Vector3f(1, 1, 1);

	private static final Matrix4f MODEL_MATRIX = WorldShader.MATRIX_MODEL;

	public static void transform(Vector3f position, Vector3f rotation, Vector3f scale)
	{
		SHARED_POSITION.set(position);
		SHARED_ROTATION.set(rotation);
		SHARED_SCALE.set(scale);
		
		MODEL_MATRIX.translate(SHARED_POSITION);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.x), GeometryUtils.ROTATION_X);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.y), GeometryUtils.ROTATION_Y);
		MODEL_MATRIX.rotate(FloatMath.toRadians(SHARED_ROTATION.z), GeometryUtils.ROTATION_Z);

		MODEL_MATRIX.scale(SHARED_SCALE);
	}
}
