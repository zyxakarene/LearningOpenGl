package zyx.opengl.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import zyx.utils.cheats.Print;
import zyx.utils.geometry.Plane;

public class ViewFrustum
{

	public final Plane left;
	public final Plane right;
	public final Plane near;
	public final Plane far;
	public final Plane top;
	public final Plane bottom;

	public ViewFrustum()
	{
		left = new Plane();
		right = new Plane();
		near = new Plane();
		far = new Plane();
		top = new Plane();
		bottom = new Plane();
	}

	public boolean isInsideView(Vector3f worldPosition, float diameter)
	{
		boolean isOutside
				= isOutsidePlane(worldPosition, near, diameter)
				|| isOutsidePlane(worldPosition, left, diameter)
				|| isOutsidePlane(worldPosition, right, diameter)
				|| isOutsidePlane(worldPosition, top, diameter)
				|| isOutsidePlane(worldPosition, bottom, diameter)
				|| isOutsidePlane(worldPosition, far, diameter);

		return !isOutside;
	}

	private boolean isOutsidePlane(Vector3f worldPosition, Plane plane, float diameter)
	{
		float dotProduct = worldPosition.x * plane.a + worldPosition.y * plane.b + worldPosition.z * plane.c;
		float dist = dotProduct + plane.d + diameter;
//		Print.out(dist);
		return dist < 0;
	}

	public void extractPlanesFrom(Matrix4f matrix)
	{
		left.d = matrix.m33 + matrix.m30;
		left.c = matrix.m23 + matrix.m20;
		left.b = matrix.m13 + matrix.m10;
		left.a = matrix.m03 + matrix.m00;
		left.normalize();

		right.d = matrix.m33 - matrix.m30;
		right.c = matrix.m23 - matrix.m20;
		right.b = matrix.m13 - matrix.m10;
		right.a = matrix.m03 - matrix.m00;
		right.normalize();

		bottom.d = matrix.m33 + matrix.m31;
		bottom.c = matrix.m23 + matrix.m21;
		bottom.b = matrix.m13 + matrix.m11;
		bottom.a = matrix.m03 + matrix.m01;
		bottom.normalize();

		top.d = matrix.m33 - matrix.m31;
		top.c = matrix.m23 - matrix.m21;
		top.b = matrix.m13 - matrix.m11;
		top.a = matrix.m03 - matrix.m01;
		top.normalize();

		near.d = matrix.m33 + matrix.m32;
		near.c = matrix.m23 + matrix.m22;
		near.b = matrix.m13 + matrix.m12;
		near.a = matrix.m03 + matrix.m02;
		near.normalize();

		far.d = matrix.m33 - matrix.m32;
		far.c = matrix.m23 - matrix.m22;
		far.b = matrix.m13 - matrix.m12;
		far.a = matrix.m03 - matrix.m02;
		far.normalize();
	}

	@Override
	public String toString()
	{
		return "ViewFrustum{" + "\nleft=" + left + ", \nright=" + right + ", \nnear=" + near + ", \nfar=" + far + ", \ntop=" + top + ", \nbottom=" + bottom + '}';
	}
}
