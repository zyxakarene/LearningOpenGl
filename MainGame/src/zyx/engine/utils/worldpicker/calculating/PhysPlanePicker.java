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
	private final Matrix4f BONE_TRANSFORM = new Matrix4f();

	@Override
	public boolean collided(Vector3f pos, Vector3f dir, IPhysbox physContainer, Vector3f intersectPoint)
	{
		PhysBox phys = physContainer.getPhysbox();
		Matrix4f mat = physContainer.getMatrix();
		if (phys == null)
		{
			return false;
		}

		boolean hitOBB = RayOBB.hit(phys.getBoundingBox(), mat, pos, dir);
		if (!hitOBB)
		{
			return false;
		}
		
		PhysObject[] objects = phys.getObjects();
		
		Vector3f end = new Vector3f();
		end.x = pos.x + (10000 * dir.x);
		end.y = pos.y + (10000 * dir.y);
		end.z = pos.z + (10000 * dir.z);
		
		for (PhysObject object : objects)
		{
			PhysTriangle[] triangles = object.getTriangles();
			short boneId = object.getBoneId();
			Matrix4f boneMatrix = physContainer.getBoneMatrix(boneId);
			
			Matrix4f.mul(mat, boneMatrix, BONE_TRANSFORM);

			Matrix4f.load(BONE_TRANSFORM, INVERT_TRANSPOSE);
			Matrix4f.invert(INVERT_TRANSPOSE, INVERT_TRANSPOSE);
			Matrix4f.transpose(INVERT_TRANSPOSE, INVERT_TRANSPOSE);
			
			tE = 0f;
			tL = 1f;
			for (PhysTriangle triangle : triangles)
			{
				boolean collided = testTriangle(pos, end, dir, triangle, BONE_TRANSFORM, intersectPoint);
				if (collided)
				{
					return true;
				}
			}
		}

		return false;
	}
	
	private float tE;
	private float tL;

	private boolean testTriangle(Vector3f p0, Vector3f p1, Vector3f dS, PhysTriangle face, Matrix4f mat, Vector3f intersectPoint)
	{
		NORMAL.set(face.normal);

		transformVertex(NORMAL, INVERT_TRANSPOSE);

		float diff = Vector3f.dot(NORMAL, dS);
		if (diff > 0)
		{
			//Backfacing triangle
			return false;
		}
		VERTEX_1.set(face.v1);
		transformVertex(VERTEX_1, mat);
		
		Vector3f.dot(dS, dS)
		//http://geomalgorithms.com/a13-_intersect-4.html#Intersect%20Segment%20and%20Polyhedron
		
		return false;
	}
}
