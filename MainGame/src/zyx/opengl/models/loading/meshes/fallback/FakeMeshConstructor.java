package zyx.opengl.models.loading.meshes.fallback;

import org.lwjgl.util.vector.Vector3f;
import zyx.utils.geometry.Box;

class FakeMeshConstructor
{

	static FakeMeshData getVertexData(Box boundingBox)
	{
		int triangleCount = 12; //2 top, 2 btm, 2 left, 2 right, 2 front, 2 back

		int vertexCount = triangleCount * 3 * 12; //triangles * vertecies * vertexData
		float[] vertexData = new float[vertexCount];

		int elementCount = triangleCount * 3;
		int[] elementData = new int[elementCount];

		fillData(boundingBox, vertexData, elementData);

		return new FakeMeshData(vertexData, elementData);
	}
	
	private static void fillData(Box b, float[] vertexData, int[] elementData)
	{
		int index = 0;
		Vector3f t1 = new Vector3f(b.maxX, b.maxY, b.maxZ);
		Vector3f t2 = new Vector3f(b.maxX, b.minY, b.maxZ);
		Vector3f t3 = new Vector3f(b.minX, b.minY, b.maxZ);
		Vector3f t4 = new Vector3f(b.minX, b.maxY, b.maxZ);
		Vector3f b1 = new Vector3f(b.maxX, b.maxY, b.minZ);
		Vector3f b2 = new Vector3f(b.maxX, b.minY, b.minZ);
		Vector3f b3 = new Vector3f(b.minX, b.minY, b.minZ);
		Vector3f b4 = new Vector3f(b.minX, b.maxY, b.minZ);
		Vector3f normal = new Vector3f();

		normal.set(0, 0, 1); //Top
		index = addVertex(t4, t2, t1, normal, vertexData, index);
		index = addVertex(t4, t3, t2, normal, vertexData, index);
		normal.set(0, 0, -1); //bottom
		index = addVertex(b1, b2, b4, normal, vertexData, index);
		index = addVertex(b2, b3, b4, normal, vertexData, index);

		normal.set(1, 0, 0); //front
		index = addVertex(t1, t2, b2, normal, vertexData, index);
		index = addVertex(t1, b2, b1, normal, vertexData, index);
		normal.set(-1, 0, 0); //back
		index = addVertex(t3, t4, b4, normal, vertexData, index);
		index = addVertex(b4, b3, t3, normal, vertexData, index);

		normal.set(0, 1, 0); //left
		index = addVertex(b4, t4, t1, normal, vertexData, index);
		index = addVertex(b1, b4, t1, normal, vertexData, index);
		normal.set(0, -1, 0); //right
		index = addVertex(b2, t2, t3, normal, vertexData, index);
		index = addVertex(b3, b2, t3, normal, vertexData, index);

		for (int i = 0; i < elementData.length; i++)
		{
			elementData[i] = i;
		}
	}

	private static int addVertex(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f n, float[] vertexData, int index)
	{
		vertexData[index++] = v1.x;
		vertexData[index++] = v1.y;
		vertexData[index++] = v1.z;
		vertexData[index++] = n.x;
		vertexData[index++] = n.y;
		vertexData[index++] = n.z;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 1;
		vertexData[index++] = 0;

		vertexData[index++] = v2.x;
		vertexData[index++] = v2.y;
		vertexData[index++] = v2.z;
		vertexData[index++] = n.x;
		vertexData[index++] = n.y;
		vertexData[index++] = n.z;
		vertexData[index++] = 10;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 1;
		vertexData[index++] = 0;

		vertexData[index++] = v3.x;
		vertexData[index++] = v3.y;
		vertexData[index++] = v3.z;
		vertexData[index++] = n.x;
		vertexData[index++] = n.y;
		vertexData[index++] = n.z;
		vertexData[index++] = 0;
		vertexData[index++] = 10;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 1;
		vertexData[index++] = 0;

		return index;
	}
}
