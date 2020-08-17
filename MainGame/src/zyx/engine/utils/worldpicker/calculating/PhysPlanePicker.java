package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysObject;
import zyx.opengl.models.implementations.physics.PhysTriangle;
import zyx.utils.cheats.Print;
import zyx.utils.interfaces.IPhysbox;

public class PhysPlanePicker extends AbstractPicker
{
	private final Matrix4f INVERT_TRANSPOSE = new Matrix4f();
	private final Matrix4f mat = new Matrix4f();

	@Override
	public boolean collided(Vector3f p0, Vector3f dS, IPhysbox physContainer, Vector3f intersectPoint)
	{
		PhysBox phys = physContainer.getPhysbox();
		Matrix4f mat = physContainer.getMatrix();
		if (phys == null)
		{
			return false;
		}

		boolean hitOBB = RayOBB.hit(phys.getBoundingBox(), mat, p0, dS);
		if (!hitOBB)
		{
			return false;
		}
		
		PhysObject[] objects = phys.getObjects();
		
		Vector3f p1 = new Vector3f();
		p1.x = p0.x + (10000 * dS.x);
		p1.y = p0.y + (10000 * dS.y);
		p1.z = p0.z + (10000 * dS.z);
		
		for (PhysObject object : objects)
		{
			PhysTriangle[] triangles = object.getTriangles();
			short boneId = object.getBoneId();
			Matrix4f boneMatrix = physContainer.getBoneMatrix(boneId);
			
			Matrix4f.mul(mat, boneMatrix, mat);

			Matrix4f.load(mat, INVERT_TRANSPOSE);
			Matrix4f.invert(INVERT_TRANSPOSE, INVERT_TRANSPOSE);
			Matrix4f.transpose(INVERT_TRANSPOSE, INVERT_TRANSPOSE);
			
			tE = 0f;
			tL = 1f;
			for (PhysTriangle face : triangles)
			{
				NORMAL.set(face.normal);

				transformVertex(NORMAL, INVERT_TRANSPOSE);

				float diff = Vector3f.dot(NORMAL, dS);
				if (diff > 0)
				{
					//Backfacing triangle
//					return false;
				}
				vi.set(face.v1);
				transformVertex(vi, mat);

				VERTEX_1.x = p0.x - vi.x;
				VERTEX_1.y = p0.y - vi.y;
				VERTEX_1.z = p0.z - vi.z;
				float N = -Vector3f.dot(VERTEX_1, face.normal);
				float D = Vector3f.dot(dS, face.normal);

				if (D == 0)
				{
					if (N < 0)
					{
						return false;
					}
					else
					{
						continue;
					}
				}

				float t = N / D;
				System.out.println(t);
				if (D < 0)
				{
					tE = tE > t ? tE : t;
					if (tE > tL)
					{
						return false;
					}
				}
				else
				{
					tL = tL < t ? tL : t;
					if (tL < tE)
					{
						return false;
					}
				}

				intersectPoint.x = p0.x + (tE * dS.x);
				intersectPoint.y = p0.y + (tE * dS.y);
				intersectPoint.z = p0.z + (tE * dS.z);
				return true;
			}
		}

		return false;
	}
	
	private float tE;
	private float tL;

	private Vector3f vi = new Vector3f();
}
