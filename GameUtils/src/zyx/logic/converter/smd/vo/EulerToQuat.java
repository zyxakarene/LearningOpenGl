package zyx.logic.converter.smd.vo;

import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

public class EulerToQuat
{

	/**
	 * Must be order ZYX
	 */
	public static Quaternion transform(Vector3f rot)
	{
		Quaternion q = new Quaternion();

		float c = (float) Math.cos(rot.x / 2f);
		float d = (float) Math.cos(rot.y / 2f);
		float e = (float) Math.cos(rot.z / 2f);
		float f = (float) Math.sin(rot.x / 2f);
		float g = (float) Math.sin(rot.y / 2f);
		float h = (float) Math.sin(rot.z / 2f);

		q.x = f * d * e - c * g * h;
		q.y = c * g * e + f * d * h;
		q.z = c * d * h - f * g * e;
		q.w = c * d * e + f * g * h;

		return q;
	}
}
