package zyx.engine.utils.worldpicker.calculating;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.geometry.Box;
import zyx.utils.math.MatrixUtils;

public class RayOBB
{
	private static Vector3f modelScale = new Vector3f();
	private static Vector3f modelPosition = new Vector3f();
	
	private static Vector3f boxRayDelta = new Vector3f();
	private static Vector3f axisVector = new Vector3f();
	
	private static float tMin = 0;
	private static float tMax = 0;
	
	public static boolean hit(Box box, Matrix4f modelMatrix, Vector3f rayPos, Vector3f rayDir)
	{
		tMin = 0.0f;
		tMax = 100000.0f;
		
		MatrixUtils.getScaleFrom(modelMatrix, modelScale);
		MatrixUtils.getPositionFrom(modelMatrix, modelPosition);
		Vector3f.sub(modelPosition, rayPos, boxRayDelta);

		axisVector.x = modelMatrix.m00;
		axisVector.y = modelMatrix.m01;
		axisVector.z = modelMatrix.m02;
		axisVector.normalise();

		float e = Vector3f.dot(axisVector, boxRayDelta);
		float f = Vector3f.dot(rayDir, axisVector);

		float t1 = (e + (box.minX * modelScale.x)) / f;
		float t2 = (e + (box.maxX * modelScale.x)) / f;

		boolean xTest = testValues(t1, t2);
		if (!xTest)
		{
			return false;
		}
		
		
		
		
		
		
		
		
		
		
		axisVector.x = modelMatrix.m10;
		axisVector.y = modelMatrix.m11;
		axisVector.z = modelMatrix.m12;
		axisVector.normalise();

		e = Vector3f.dot(axisVector, boxRayDelta);
		f = Vector3f.dot(rayDir, axisVector);

		t1 = (e + (box.minY * modelScale.y)) / f;
		t2 = (e + (box.maxY * modelScale.y)) / f;

		boolean yTest = testValues(t1, t2);
		if (!yTest)
		{
			return false;
		}
		
		
		
		
		
		
		
		
		
		
		axisVector.x = modelMatrix.m20;
		axisVector.y = modelMatrix.m21;
		axisVector.z = modelMatrix.m22;
		axisVector.normalise();

		e = Vector3f.dot(axisVector, boxRayDelta);
		f = Vector3f.dot(rayDir, axisVector);

		t1 = (e + (box.minZ * modelScale.z)) / f;
		t2 = (e + (box.maxZ * modelScale.z)) / f;
		// tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
		// tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
		
		boolean zTest = testValues(t1, t2);
		if (!zTest)
		{
			return false;
		}
		
		return true;
	}

	private static boolean testValues(float t1, float t2)
	{
		if (t1 > t2)
		{
			// if wrong order. swap t1 and t2
			float w = t1;
			t1 = t2;
			t2 = w;
		}

		// tMax is the nearest "far" intersection (amongst the X,Y and Z planes pairs)
		if (t2 < tMax)
		{
			tMax = t2;
		}
		// tMin is the farthest "near" intersection (amongst the X,Y and Z planes pairs)
		if (t1 > tMin)
		{
			tMin = t1;
		}

		return tMax >= tMin;
	}
}
