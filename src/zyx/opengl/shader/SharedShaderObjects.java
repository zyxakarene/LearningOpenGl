package zyx.opengl.shader;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;


public class SharedShaderObjects
{
    public static final Matrix4f SHARED_VIEW_TRANSFORM = new Matrix4f();
    public static final Matrix4f SHARED_MODEL_TRANSFORM = new Matrix4f();
    public static final Vector3f SHARED_VECTOR_3F = new Vector3f();
    public static final Vector4f SHARED_VECTOR_4F = new Vector4f();
}
