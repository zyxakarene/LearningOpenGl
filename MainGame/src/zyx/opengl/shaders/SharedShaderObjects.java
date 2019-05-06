package zyx.opengl.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;


public class SharedShaderObjects
{
    public static final Matrix4f SHARED_ORTHOGRAPHIC_TRANSFORM = new Matrix4f();
    public static final Matrix4f SHARED_PROJECTION_TRANSFORM = new Matrix4f();
    public static final Matrix4f SHARED_VIEW_TRANSFORM = new Matrix4f();
    public static final Matrix4f SHARED_MODEL_TRANSFORM = new Matrix4f();
	
    public static final Matrix4f SHARED_PROJECTION_VIEW_TRANSFORM = new Matrix4f();
	
    public static final Vector4f SHARED_VECTOR_4F = new Vector4f();

	public static void combineMatrices()
	{
		Matrix4f.mul(SHARED_PROJECTION_TRANSFORM, SHARED_VIEW_TRANSFORM, SHARED_PROJECTION_VIEW_TRANSFORM);
	}
}
