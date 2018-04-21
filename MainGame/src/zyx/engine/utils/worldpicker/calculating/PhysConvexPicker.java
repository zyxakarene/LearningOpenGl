package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.game.controls.input.MouseData;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.models.implementations.physics.PhysObject;
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

		boolean hitOBB = RayOBB.hit(phys.getBoundingBox(), mat, pos, dir);
		if (!hitOBB)
		{
			return false;
		}
		
		PhysObject[] objects = phys.getObjects();
		
		for (PhysObject object : objects)
		{
			PhysTriangle[] triangles = object.getTriangles();
			short boneId = object.getBoneId();
			Matrix4f boneMatrix = physContainer.getBoneMatrix(boneId);
			
			boolean collided = testTriangle(pos, dir, triangles, boneMatrix);
			if (collided)
			{
				return true;
			}
		}


//		intersectPoint.set(pos);
		return false;
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
		PhysTriangle triangle0 = triangles[5];
		
		Vector3f N = triangle0.normal;
		Vector3f p0 = startPos;
		Vector3f v = rayDir;
		float D = Vector3f.dot(N, triangle0.v1);
		//D = (N.dot.C) / (a1^2 + b1^2 + c1^2)
		D = Vector3f.dot(N, triangle0.v3) / ((N.x * N.x) + (N.y * N.y) + (N.z * N.z));
		
		
		float a = Vector3f.dot(N, p0);
		float b = Vector3f.dot(N, v);
		float t = (D - a) / (b);
		Vector3f point = new Vector3f(p0);
		point.x += t * v.x;
		point.y += t * v.y;
		point.z += t * v.z;
		
		if (MouseData.data.isLeftClicked())
		{
			DebugPoint.addToScene(point, 2000);
		}
		
		int count = 0;
		int pass = 0;
		for (PhysTriangle triangle : triangles)
		{
			VERTEX_1.set(triangle.v1);
			VERTEX_2.set(triangle.v2);
			VERTEX_3.set(triangle.v3);
			NORMAL.set(triangle.normal);
			
			float dot = Vector3f.dot(point, NORMAL);
			if (MouseData.data.isLeftDown())
			{
				System.out.println(dot);
			}
		
			if (dot <= 0)
			{
				return false;
			}
			
		}

		return true;
	}
}
