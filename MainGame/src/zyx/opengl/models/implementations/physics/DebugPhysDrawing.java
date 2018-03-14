package zyx.opengl.models.implementations.physics;

import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.SharedPools;
import zyx.opengl.models.implementations.LoadableValueObject;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.textures.ColorTexture;
import zyx.utils.geometry.Box;

public class DebugPhysDrawing
{

	private static final HashMap<PhysBox, WorldModel> MESH_MAP = new HashMap<>();
	private static final HashMap<PhysBox, WorldModel> BOUNDING_BOX_MAP = new HashMap<>();
	
	public static WorldModel[] getModelFor(PhysBox box)
	{
		if (MESH_MAP.containsKey(box) == false)
		{
			createModel(box);
		}

		WorldModel[] models = new WorldModel[2];
		models[0] = MESH_MAP.get(box);
		models[1] = BOUNDING_BOX_MAP.get(box);
		
		return models;
	}

	private static void createModel(PhysBox box)
	{
		PhysTriangle[] triangles = box.getTriangles();
		Box boundingBox = box.getBoundingBox();

		WorldModel mesh = getMeshModel(triangles);
		WorldModel bounding = getBoundingModel(boundingBox);

		MESH_MAP.put(box, mesh);
		BOUNDING_BOX_MAP.put(box, bounding);
	}

	private static WorldModel getMeshModel(PhysTriangle[] triangles)
	{
		int vertexCount = (triangles.length * 3 * 12);
		float[] vertexData = new float[vertexCount];

		int elementCount = (triangles.length * 3);
		int[] elementData = new int[elementCount];

		fillData(triangles, vertexData, elementData);
		Skeleton skeleton = new Skeleton(getMeshJoint("root", 1), getMeshJoint("dummy", 0));
		LoadableValueObject vo = new LoadableValueObject(vertexData, elementData, skeleton, null, "");
		vo.setGameTexture(new ColorTexture(0xFF0000));
		WorldModel model = new WorldModel(vo);

		return model;
	}

	private static Joint getMeshJoint(String name, int id)
	{
		Matrix4f matrix = SharedPools.MATRIX_POOL.getInstance();
		return new Joint(id, name, matrix);
	}

	private static void fillData(PhysTriangle[] triangles, float[] vertexData, int[] elementData)
	{
		Vector3f normal = new Vector3f();
		int index = 0;
		for (PhysTriangle triangle : triangles)
		{
			calculateNormal(normal, triangle);
			normal.set(triangle.normal);

			vertexData[index++] = triangle.v1.x;
			vertexData[index++] = triangle.v1.y;
			vertexData[index++] = triangle.v1.z;
			vertexData[index++] = normal.x;
			vertexData[index++] = normal.y;
			vertexData[index++] = normal.z;
			vertexData[index++] = 0;
			vertexData[index++] = 0;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
			vertexData[index++] = triangle.v2.x;
			vertexData[index++] = triangle.v2.y;
			vertexData[index++] = triangle.v2.z;
			vertexData[index++] = normal.x;
			vertexData[index++] = normal.y;
			vertexData[index++] = normal.z;
			vertexData[index++] = 0;
			vertexData[index++] = 0;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
			vertexData[index++] = triangle.v3.x;
			vertexData[index++] = triangle.v3.y;
			vertexData[index++] = triangle.v3.z;
			vertexData[index++] = normal.x;
			vertexData[index++] = normal.y;
			vertexData[index++] = normal.z;
			vertexData[index++] = 0;
			vertexData[index++] = 0;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
			vertexData[index++] = 1;
		}

		for (int i = 0; i < elementData.length; i++)
		{
			elementData[i] = i;
		}
	}

	private static void calculateNormal(Vector3f normal, PhysTriangle triangle)
	{
		Vector3f ab = toVector(triangle.v1, triangle.v2);
		Vector3f ac = toVector(triangle.v1, triangle.v3);

		Vector3f.cross(ab, ac, normal);
	}

	private static Vector3f toVector(Vector3f v1, Vector3f v2)
	{
		Vector3f vec = new Vector3f();
		Vector3f.sub(v1, v2, vec);

		if (vec.length() == 0)
		{
			return null;
		}
		else
		{
			vec.normalise();
			return vec;
		}
	}

	private static WorldModel getBoundingModel(Box boundingBox)
	{
		int triangleCount = 2 * 6;

		int vertexCount = triangleCount * 3 * 12;
		float[] vertexData = new float[vertexCount];

		int elementCount = triangleCount * 3;
		int[] elementData = new int[elementCount];

		fillData(boundingBox, vertexData, elementData);
		Skeleton skeleton = new Skeleton(getMeshJoint("root", 1), getMeshJoint("dummy", 0));
		LoadableValueObject vo = new LoadableValueObject(vertexData, elementData, skeleton, null, "");
		vo.setGameTexture(new ColorTexture(0x00FF00));
		WorldModel model = new WorldModel(vo);

		return model;
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
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		
		vertexData[index++] = v2.x;
		vertexData[index++] = v2.y;
		vertexData[index++] = v2.z;
		vertexData[index++] = n.x;
		vertexData[index++] = n.y;
		vertexData[index++] = n.z;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		
		vertexData[index++] = v3.x;
		vertexData[index++] = v3.y;
		vertexData[index++] = v3.z;
		vertexData[index++] = n.x;
		vertexData[index++] = n.y;
		vertexData[index++] = n.z;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		vertexData[index++] = 1;
		
		return index;
	}
}
