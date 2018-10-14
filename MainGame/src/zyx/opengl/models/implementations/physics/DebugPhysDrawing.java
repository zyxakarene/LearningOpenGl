package zyx.opengl.models.implementations.physics;

import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.LoadableWorldModelVO;
import zyx.opengl.models.implementations.WorldModel;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.textures.ColorTexture;
import zyx.utils.geometry.Box;
import zyx.utils.interfaces.IPhysbox;

public class DebugPhysDrawing
{

	public static final int INDEX_MESH = 0;
	public static final int INDEX_BOUNDING = 1;
	
	private static final HashMap<PhysBox, WorldModel> MESH_MAP = new HashMap<>();
	private static final HashMap<PhysBox, WorldModel> BOUNDING_BOX_MAP = new HashMap<>();
	
	public static WorldModel[] getModelFor(IPhysbox physBox)
	{
		PhysBox box = physBox.getPhysbox();
		
		if (MESH_MAP.containsKey(box) == false)
		{
			createModel(box);
		}

		WorldModel[] models = new WorldModel[2];
		models[INDEX_MESH] = MESH_MAP.get(box);
		models[INDEX_BOUNDING] = BOUNDING_BOX_MAP.get(box);
				
		return models;
	}

	private static void createModel(PhysBox box)
	{
		PhysObject[] objects = box.getObjects();
		Box boundingBox = box.getBoundingBox();

		WorldModel bounding = getBoundingModel(boundingBox);
		WorldModel mesh = getMeshModel(box.getTriangles().length, objects);
		MESH_MAP.put(box, mesh);
		BOUNDING_BOX_MAP.put(box, bounding);
	}

	private static WorldModel getMeshModel(int totalTriangleCount, PhysObject[] objects)
	{
		int vertexCount = (totalTriangleCount * 3 * 12);
		float[] vertexData = new float[vertexCount];

		int elementCount = (totalTriangleCount * 3);
		int[] elementData = new int[elementCount];

		int index = 0;
		for (PhysObject object : objects)
		{
			PhysTriangle[] triangles = object.getTriangles();
			int boneId = object.getBoneId();
			
			index = fillData(triangles, vertexData, boneId, index);
		}
		
		for (int i = 0; i < elementData.length; i++)
		{
			elementData[i] = i;
		}
		
		Skeleton skeleton = new Skeleton(getMeshJoint("root", 0), getMeshJoint("dummy", 0));
		LoadableWorldModelVO vo = new LoadableWorldModelVO(vertexData, elementData, skeleton, null, "");
		vo.setGameTexture(new ColorTexture(0xFF0000));
		WorldModel model = new WorldModel(vo);

		return model;
	}

	private static Joint getMeshJoint(String name, int id)
	{
		Matrix4f matrix = new Matrix4f();
		return new Joint(id, name, matrix);
	}

	private static int fillData(PhysTriangle[] triangles, float[] vertexData, int boneId, int index)
	{
		Vector3f normal = new Vector3f();
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
			vertexData[index++] = boneId;
			vertexData[index++] = 0;
			vertexData[index++] = 1;
			vertexData[index++] = 0;
			vertexData[index++] = triangle.v2.x;
			vertexData[index++] = triangle.v2.y;
			vertexData[index++] = triangle.v2.z;
			vertexData[index++] = normal.x;
			vertexData[index++] = normal.y;
			vertexData[index++] = normal.z;
			vertexData[index++] = 0;
			vertexData[index++] = 0;
			vertexData[index++] = boneId;
			vertexData[index++] = 0;
			vertexData[index++] = 1;
			vertexData[index++] = 0;
			vertexData[index++] = triangle.v3.x;
			vertexData[index++] = triangle.v3.y;
			vertexData[index++] = triangle.v3.z;
			vertexData[index++] = normal.x;
			vertexData[index++] = normal.y;
			vertexData[index++] = normal.z;
			vertexData[index++] = 0;
			vertexData[index++] = 0;
			vertexData[index++] = boneId;
			vertexData[index++] = 0;
			vertexData[index++] = 1;
			vertexData[index++] = 0;
		}
		
		return index;
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
		int triangleCount = 12; //2 top, 2 btm, 2 left, 2 right, 2 front, 2 back

		int vertexCount = triangleCount * 3 * 12; //triangles * vertecies * vertexData
		float[] vertexData = new float[vertexCount];

		int elementCount = triangleCount * 3;
		int[] elementData = new int[elementCount];

		fillData(boundingBox, vertexData, elementData);
		Skeleton skeleton = new Skeleton(getMeshJoint("root11", 0), getMeshJoint("dummy22", 0));
		LoadableWorldModelVO vo = new LoadableWorldModelVO(vertexData, elementData, skeleton, null, "");
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
		vertexData[index++] = 0;
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
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 0;
		vertexData[index++] = 1;
		vertexData[index++] = 0;
		
		return index;
	}
}
