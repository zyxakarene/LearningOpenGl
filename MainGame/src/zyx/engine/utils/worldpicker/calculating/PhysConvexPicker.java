package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.input.MouseData;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysTriangle;
import zyx.utils.cheats.DebugPoint;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;

public class PhysConvexPicker extends AbstractPicker
{

	@Override
	public boolean collided(Vector3f pos, Vector3f dir, IPhysbox physContainer, Vector3f intersectPoint)
	{
		PhysBox phys = physContainer.getPhysbox();
		Matrix4f mat = physContainer.getMatrix();
		if (phys == null)
		{
			return false;
		}

		PhysTriangle[] triangles = phys.getTriangles();

		boolean collided = testTriangle(pos, dir, triangles, mat);

		intersectPoint.set(pos);
		return collided;
	}

	private float intersectPlane(Vector3f planeNormal, Vector3f vertexPos, Vector3f startPos, Vector3f rayDir)
	{
		float d = Vector3f.dot(vertexPos, planeNormal);
		vertexPos = new Vector3f(planeNormal);
		vertexPos.scale(d);

		// assuming vectors are all normalized
		float denom = Vector3f.dot(planeNormal, rayDir);
		if (denom > 1e-6)
		{
			Vector3f p0l0 = Vector3f.sub(vertexPos, startPos, null);
			return Vector3f.dot(p0l0, planeNormal) / denom;
		}

		return -1;
	}

	private boolean testTriangle(Vector3f startPos, Vector3f rayDir, PhysTriangle[] triangles, Matrix4f matrix)
	{

		int count = 0;
		int pass = 0;
		for (PhysTriangle triangle : triangles)
		{
			VERTEX_1.set(triangle.v1);
			VERTEX_2.set(triangle.v2);
			VERTEX_3.set(triangle.v3);
			NORMAL.set(triangle.normal);

			float t = intersectPlane(NORMAL, VERTEX_1, startPos, rayDir);

			count++;
			if (t > 0)
			{
				pass++;

				Vector3f pos = new Vector3f(startPos);
				pos.x += rayDir.x * t;
				pos.y += rayDir.y * t;
				pos.z += rayDir.z * t;

				float dotLeft = Vector3f.dot(NORMAL, pos);
				float dotRight = Vector3f.dot(NORMAL, VERTEX_1);

				if (dotLeft <= dotRight)
				{
					if (MouseData.data.isLeftClicked())
					{
						DebugPoint.addToScene(pos.x, pos.y, pos.z, 10000);
					}

					return true;
				}

			}
			else
			{
				return false;
			}
		}

		Print.out("Count:", count, "Pass:", pass);

		return false;
	}
}
