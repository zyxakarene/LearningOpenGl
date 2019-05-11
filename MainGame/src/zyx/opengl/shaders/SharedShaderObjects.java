package zyx.opengl.shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;


public class SharedShaderObjects
{
    public static final Matrix4f UI_ORTHOGRAPHIC_PROJECTION = new Matrix4f();
    public static final Matrix4f WORLD_PERSPECTIVE_PROJECTION = new Matrix4f();
	
    public static final Matrix4f SHARED_WORLD_VIEW_TRANSFORM = new Matrix4f();
    public static final Matrix4f SHARED_WORLD_MODEL_TRANSFORM = new Matrix4f();
	
    public static final Matrix4f WORLD_PROJECTION_VIEW_TRANSFORM = new Matrix4f();
	
    public static final Matrix4f SUN_VIEW_TRANSFORM = new Matrix4f();
    public static final Matrix4f SUN_ORTHOGRAPHIC_PROJECTION = new Matrix4f();
    public static final Matrix4f SUN_PROJECTION_VIEW_TRANSFORM = new Matrix4f();
	
    public static final Vector4f SHARED_VECTOR_4F = new Vector4f();

	public static void combineMatrices()
	{
		Matrix4f.mul(WORLD_PERSPECTIVE_PROJECTION, SHARED_WORLD_VIEW_TRANSFORM, WORLD_PROJECTION_VIEW_TRANSFORM);
		Matrix4f.mul(SUN_ORTHOGRAPHIC_PROJECTION, SUN_VIEW_TRANSFORM, SUN_PROJECTION_VIEW_TRANSFORM);
	}
}
