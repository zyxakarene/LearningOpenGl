package zyx.opengl.models.implementations.physics;

import org.lwjgl.util.vector.Vector3f;
import zyx.utils.geometry.Box;


public class PhysBox
{
	private final Box boundingBox;
	private final PhysTriangle[] triangles;

	private int trianglesAdded;
	
	public PhysBox(int triangleCount)
	{
		trianglesAdded = 0;
		triangles = new PhysTriangle[triangleCount];
		boundingBox = new Box(-10, 10, 1-0, 10, -10, 10);
	}

	public void addTriangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f n)
	{
		Vector3f vertex1 = new Vector3f(v1.x, v1.y, v1.z);
		Vector3f vertex2 = new Vector3f(v2.x, v2.y, v2.z);
		Vector3f vertex3 = new Vector3f(v3.x, v3.y, v3.z);
		Vector3f normal = new Vector3f(n.x, n.y, n.z);
		
		triangles[trianglesAdded] = new PhysTriangle(vertex1, vertex2, vertex3, normal);
		trianglesAdded++;
	}
	
	public PhysTriangle[] getTriangles()
	{
		return triangles;
	}
}
