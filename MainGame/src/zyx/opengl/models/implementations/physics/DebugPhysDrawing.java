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

public class DebugPhysDrawing
{

	private static final HashMap<PhysBox, WorldModel> MAP = new HashMap<>();

	public static WorldModel getModelFor(PhysBox box)
	{
		if (MAP.containsKey(box) == false)
		{
			createModel(box);
		}

		return MAP.get(box);
	}

	private static void createModel(PhysBox box)
	{
		int vertexCount = box.getTriangles().length * 3 * 12;
		float[] vertexData = new float[vertexCount];

		int elementCount = box.getTriangles().length * 3;
		int[] elementData = new int[elementCount];

		fillData(box.getTriangles(), vertexData, elementData);

		Skeleton skeleton = new Skeleton(getMeshJoint("root", 1), getMeshJoint("dummy", 0));
		LoadableValueObject vo = new LoadableValueObject(vertexData, elementData, skeleton, null, "tile.png");
		vo.setGameTexture(new ColorTexture(0xFF0000));
		WorldModel model = new WorldModel(vo);

		MAP.put(box, model);
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
}
