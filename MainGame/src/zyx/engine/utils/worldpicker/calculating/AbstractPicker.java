package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import zyx.utils.interfaces.IPhysbox;

public abstract class AbstractPicker
{
	private static final Vector4f VERTEX_HELPER = new Vector4f();

	public abstract boolean collided(Vector3f pos, Vector3f dir, IPhysbox physContainer, Vector3f intersectPoint);
	
	protected void transformVertex(Vector3f vertex, Matrix4f mat)
	{
		VERTEX_HELPER.x = vertex.x;
		VERTEX_HELPER.y = vertex.y;
		VERTEX_HELPER.z = vertex.z;
		VERTEX_HELPER.w = 1;

		Matrix4f.transform(mat, VERTEX_HELPER, VERTEX_HELPER);

		vertex.x = VERTEX_HELPER.x;
		vertex.y = VERTEX_HELPER.y;
		vertex.z = VERTEX_HELPER.z;
	}
	
}
