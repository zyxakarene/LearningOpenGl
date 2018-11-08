package zyx.opengl.camera;

import java.util.Arrays;
import org.lwjgl.util.vector.Matrix4f;

public class ViewFrustum
{
	public float[] left = new float[4];
	public float[] right = new float[4];
	public float[] near = new float[4];
	public float[] far = new float[4];
	public float[] top = new float[4];
	public float[] bottom = new float[4];

	@Override
	public String toString()
	{
		return "ViewFrustum{" + "\nleft=" + Arrays.toString(left) + ", \nright=" + Arrays.toString(right) + ", \nnear=" + Arrays.toString(near) + ", \nfar=" + Arrays.toString(far) + ", \ntop=" + Arrays.toString(top) + ", \nbottom=" + Arrays.toString(bottom) + '}';
	}
	
	public static void extractPlanes(Matrix4f matrix, float left[], float right[], float top[], float bottom[], float near[], float far[])
	{
		left[3] = matrix.m33 + matrix.m30;
		left[2] = matrix.m23 + matrix.m20;
		left[1] = matrix.m13 + matrix.m10;
		left[0] = matrix.m03 + matrix.m00;
		
		right[3] = matrix.m33 - matrix.m30;
		right[2] = matrix.m23 - matrix.m20;
		right[1] = matrix.m13 - matrix.m10;
		right[0] = matrix.m03 - matrix.m00;
		
		bottom[3] = matrix.m33 + matrix.m31;
		bottom[2] = matrix.m23 + matrix.m21;
		bottom[1] = matrix.m13 + matrix.m11;
		bottom[0] = matrix.m03 + matrix.m01;		
		
		top[3] = matrix.m33 - matrix.m31;
		top[2] = matrix.m23 - matrix.m21;
		top[1] = matrix.m13 - matrix.m11;
		top[0] = matrix.m03 - matrix.m01;
		
		near[3] = matrix.m33 + matrix.m32;
		near[2] = matrix.m23 + matrix.m22;
		near[1] = matrix.m13 + matrix.m12;
		near[0] = matrix.m03 + matrix.m02;
		
		far[3] = matrix.m33 - matrix.m32;
		far[2] = matrix.m23 - matrix.m22;
		far[1] = matrix.m13 - matrix.m12;
		far[0] = matrix.m03 - matrix.m02;
	}
	
}
