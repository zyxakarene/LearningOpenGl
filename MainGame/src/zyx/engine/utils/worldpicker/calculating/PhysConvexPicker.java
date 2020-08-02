package zyx.engine.utils.worldpicker.calculating;

import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
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
	private final Matrix4f INVERT_TRANSPOSE = new Matrix4f();
	private final Matrix4f BONE_TRANSFORM = new Matrix4f();

	@Override
	public boolean collided(Vector3f pos, Vector3f dir, IPhysbox physContainer, Vector3f intersectPoint)
	{
		if (MouseData.data.isLeftClicked())
		{
			DebugPoint.clearAll();
		}
		
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
		
		pos = new Vector3f(0, 0, 0);
		dir = new Vector3f(0, 1, 0);
				
		for (PhysObject object : objects)
		{
			PhysTriangle[] triangles = object.getTriangles();
			short boneId = object.getBoneId();
			Matrix4f boneMatrix = physContainer.getBoneMatrix(boneId);
			
			Matrix4f.mul(mat, boneMatrix, BONE_TRANSFORM);

			Matrix4f.load(BONE_TRANSFORM, INVERT_TRANSPOSE);
			Matrix4f.invert(INVERT_TRANSPOSE, INVERT_TRANSPOSE);
			Matrix4f.transpose(INVERT_TRANSPOSE, INVERT_TRANSPOSE);

			
			boolean collided = testTriangle(pos, dir, triangles, BONE_TRANSFORM, intersectPoint);
			if (collided)
			{
				return true;
			}
		}


		return false;
	}

	private boolean testTriangle(Vector3f startPos, Vector3f rayDir, PhysTriangle[] triangles, Matrix4f matrix, Vector3f intersectPoint)
	{
		ArrayList<Vector3f> transformedVerts = new ArrayList<>();
		for (PhysTriangle triangle : triangles)
		{
			Vector3f vert1 = new Vector3f(triangle.v1);
			Vector3f vert2 = new Vector3f(triangle.v2);
			Vector3f vert3 = new Vector3f(triangle.v3);
			
			transformVertex(vert1, matrix);
			transformVertex(vert2, matrix);
			transformVertex(vert3, matrix);
			
			transformedVerts.add(vert1);
			transformedVerts.add(vert2);
			transformedVerts.add(vert3);
		}
		
		ArrayList<Vector3f> lessThanX = new ArrayList<>();
		ArrayList<Vector3f> greaterEqualThanX = new ArrayList<>();
		
		//Split up into X groups (Left, Right)
		for (Vector3f vertex : transformedVerts)
		{
			if (vertex.x < startPos.x)
			{
				lessThanX.add(vertex);
			}
			else
			{
				greaterEqualThanX.add(vertex);
			}
		}
		
		//Testing for X sides
		if (lessThanX.isEmpty() || greaterEqualThanX.isEmpty())
		{
			return false;
		}
			
		ArrayList<Vector3f> lessThanZ = new ArrayList<>();
		ArrayList<Vector3f> greaterEqualThanZ = new ArrayList<>();
		transformedVerts.clear();
		
		//Spliting up into Z groups (Up, Down)
		for (Vector3f grVec : greaterEqualThanX)
		{
			for (Vector3f leVec : lessThanX)
			{
				float x1 = leVec.x;
				float y1 = leVec.y;
				float z1 = leVec.z;
				
				float x2 = grVec.x;
				float y2 = grVec.y;
				float z2 = grVec.z;
				
				if (x1 < -0.9f && x2 > 1.9f && z1 > 2.99f)
				{
					Print.out("");
				}
				
				float deltaX = x2 - x1;
				float deltaY = y2 - y1;
				float deltaZ = z2 - z1;
				
				deltaY = deltaY / deltaX;
				deltaZ = deltaZ / deltaX;
				deltaX = 1f;
				
				float offsetZ = deltaZ * x1;
				offsetZ = z1 - offsetZ;
				
				float offsetY = deltaY * x1;
				offsetY = y1 - offsetY;
				
				Vector3f newPoint = new Vector3f();
				newPoint.x = 0;
				newPoint.y = offsetY;
				newPoint.z = offsetZ;
				
				if (newPoint.z < 0)
				{
					lessThanZ.add(newPoint);
				}
				else
				{
					greaterEqualThanZ.add(newPoint);
				}
			}
		}
		
		//Testing for Z sides
		if (lessThanZ.isEmpty() || greaterEqualThanZ.isEmpty())
		{
			return false;
		}
		
		int lessThanY = 0;
		int greaterEqualThanY = 0;
		transformedVerts.clear();
		//Spliting up into Y groups (Forward, Backward)
		for (Vector3f grVec : greaterEqualThanZ)
		{
			for (Vector3f leVec : lessThanZ)
			{
				float x1 = leVec.z;
				float y1 = leVec.y;
				
				float x2 = grVec.z;
				float y2 = grVec.y;
								
				float deltaX = x2 - x1;
				float deltaY = y2 - y1;
				
				deltaY = deltaY / deltaX;
				deltaX = 1f;
				
				float offsetY = deltaY * x1;
				offsetY = y1 - offsetY;
				
				Vector3f newPoint = new Vector3f();
				newPoint.x = 0;
				newPoint.y = offsetY;
				newPoint.z = 0;
				
				if (newPoint.y < 0)
				{
					lessThanY++;
				}
				else
				{
					greaterEqualThanY++;
				}
				
				transformedVerts.add(newPoint);
			}
		}
		
		if (lessThanY == 0 || greaterEqualThanY == 0)
		{
			return false;
		}
		
		float minY = Float.MAX_VALUE;
		for (Vector3f vert : transformedVerts)
		{
			if (vert.y < minY)
			{
				minY = vert.y;
			}
		}
		
		intersectPoint.x = startPos.x  + (rayDir.x * minY);
		intersectPoint.y = startPos.y  + (rayDir.y * minY);
		intersectPoint.z = startPos.z  + (rayDir.z * minY);
		
		return true;
	}
}
