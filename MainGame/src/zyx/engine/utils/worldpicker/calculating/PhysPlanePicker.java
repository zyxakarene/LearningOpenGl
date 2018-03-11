package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysTriangle;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;

public class PhysPlanePicker extends AbstractPicker
{

	private static final Vector3f VERTEX_0 = new Vector3f();
	private static final Vector3f VERTEX_1 = new Vector3f();
	private static final Vector3f VERTEX_2 = new Vector3f();

	private static final Vector3f AB = new Vector3f();
	private static final Vector3f AC = new Vector3f();
	private static final Vector3f TRIANGLE_NORMAL = new Vector3f();

	@Override
	public boolean collided(Vector3f pos, Vector3f dir, IPhysbox physContainer, Vector3f intersectPoint)
	{
		PhysBox phys = physContainer.getPhysbox();
		Matrix4f mat = physContainer.getMatrix();
		if (phys == null)
		{
			return false;
		}
		boolean collided = false;

		PhysTriangle[] triangles = phys.getTriangles();
		if (triangles.length == 0)
		{
			return false;
		}

		Vector3f endPos = new Vector3f(pos);
		endPos.x += (dir.x * 10000);
		endPos.y += (dir.y * 10000);
		endPos.z += (dir.z * 10000);

		int negativeCount = 0;
		for (PhysTriangle triangle : triangles)
		{
			collided = testTriangle(pos, endPos, triangle, mat);

			if (!collided)
			{
				negativeCount++;
				return false;
			}
		}
		Print.out(negativeCount, triangles.length);

		intersectPoint.set(pos);
		return collided;
	}

	private boolean testTriangle(Vector3f startPos, Vector3f endPos, PhysTriangle triangle, Matrix4f matrix)
	{
		triangle.getVertex1(0, VERTEX_0);
		triangle.getVertex1(1, VERTEX_1);
		triangle.getVertex1(2, VERTEX_2);

		transformVertex(VERTEX_0, matrix);
		transformVertex(VERTEX_1, matrix);
		transformVertex(VERTEX_2, matrix);

		Vector3f.sub(VERTEX_0, VERTEX_1, AB);
		Vector3f.sub(VERTEX_0, VERTEX_2, AC);
		AB.normalise();
		AC.normalise();

		Vector3f.cross(AB, AC, TRIANGLE_NORMAL);
		TRIANGLE_NORMAL.normalise();

		float dotStart = Vector3f.dot(startPos, TRIANGLE_NORMAL);
		float dotEnd = Vector3f.dot(endPos, TRIANGLE_NORMAL);

		if ((dotStart > 0 && dotEnd < 0) || (dotStart < 0 && dotEnd > 0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
