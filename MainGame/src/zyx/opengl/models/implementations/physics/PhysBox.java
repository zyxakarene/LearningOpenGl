package zyx.opengl.models.implementations.physics;

import org.lwjgl.util.vector.Vector3f;
import zyx.utils.geometry.Box;
import zyx.utils.interfaces.IDisposeable;


public class PhysBox implements IDisposeable
{
	private PhysObject[] objects;
	private Box boundingBox;
	private PhysTriangle[] allTriangles;

	private int trianglesAdded;
	private int objectsAdded;
	
	public PhysBox(int triangleCount, Box boundingBox, int objectCount)
	{
		trianglesAdded = 0;
		objectsAdded = -1;
		
		allTriangles = new PhysTriangle[triangleCount];
		objects = new PhysObject[objectCount];
		
		this.boundingBox = boundingBox;
	}

	public void addObject(int triangleCount, short boneId)
	{
		objectsAdded++;
		objects[objectsAdded] = new PhysObject(triangleCount, boneId);
	}
	
	public void addTriangle(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f n)
	{
		Vector3f vertex1 = new Vector3f(v1.x, v1.y, v1.z);
		Vector3f vertex2 = new Vector3f(v2.x, v2.y, v2.z);
		Vector3f vertex3 = new Vector3f(v3.x, v3.y, v3.z);
		Vector3f normal = new Vector3f(n.x, n.y, n.z);
		
		PhysTriangle triangle = new PhysTriangle(vertex1, vertex2, vertex3, normal);
		
		allTriangles[trianglesAdded] = triangle;
		trianglesAdded++;
		
		objects[objectsAdded].addTriangle(triangle);
	}

	public PhysObject[] getObjects()
	{
		return objects;
	}
	
	public PhysTriangle[] getTriangles()
	{
		return allTriangles;
	}
	
	public Box getBoundingBox()
	{
		return boundingBox;
	}

	@Override
	public void dispose()
	{
		if (objects != null)
		{
			for (PhysObject object : objects)
			{
				object.dispose();
			}
		}
		
		if (allTriangles != null)
		{
			for (PhysTriangle triangle : allTriangles)
			{
				triangle.dispose();
			}
		}
		
		boundingBox = null;
		allTriangles = null;
		objects = null;
	}
	
	
}
