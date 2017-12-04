package zyx.opengl.models.implementations.bones.transform;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.math.MatrixUtils;

public class JointTransform
{

	private final Matrix4f matrix;
	
	private final Quaternion rotation;
	private final Vector3f position;

	public JointTransform(float x, float y, float z, float rotX, float rotY, float rotZ, float rotW)
	{
		matrix = new Matrix4f();

		rotation = new Quaternion(rotX, rotY, rotZ, rotW);
		position = new Vector3f(x, y, z);
		
		MatrixUtils.transformMatrix(rotation, position, matrix);
	}

	public void getPosition(Vector3f out)
	{
		out.x = position.x;
		out.y = position.y;
		out.z = position.z;
	}
	
	public void getRotation(Quaternion out)
	{
		out.x = rotation.x;
		out.y = rotation.y;
		out.z = rotation.z;
		out.w = rotation.w;
	}
	
	public Matrix4f getMatrix()
	{
		return matrix;
	}
}
