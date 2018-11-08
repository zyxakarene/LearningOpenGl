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
		// Left clipping plane
		left[0] = matrix.m30 + matrix.m00;
		left[1] = matrix.m31 + matrix.m01;
		left[2] = matrix.m32 + matrix.m02;
		left[3] = matrix.m33 + matrix.m03;
		// Right clipping plane
		right[0] = matrix.m30 - matrix.m00;
		right[1] = matrix.m31 - matrix.m01;
		right[2] = matrix.m32 - matrix.m02;
		right[3] = matrix.m33 - matrix.m03;
		// Top clipping plane
		top[0] = matrix.m30 - matrix.m10;
		top[1] = matrix.m31 - matrix.m11;
		top[2] = matrix.m32 - matrix.m12;
		top[3] = matrix.m33 - matrix.m13;
		// Bottom clipping plane
		bottom[0] = matrix.m30 + matrix.m10;
		bottom[1] = matrix.m31 + matrix.m11;
		bottom[2] = matrix.m32 + matrix.m12;
		bottom[3] = matrix.m33 + matrix.m13;
		// Near clipping plane
		near[0] = matrix.m30 + matrix.m20;
		near[1] = matrix.m31 + matrix.m21;
		near[2] = matrix.m32 + matrix.m22;
		near[3] = matrix.m33 + matrix.m23;
		// Far clipping plane
		far[0] = matrix.m30 - matrix.m20;
		far[1] = matrix.m31 - matrix.m21;
		far[2] = matrix.m32 - matrix.m22;
		far[3] = matrix.m33 - matrix.m23;
	}
	
	public static void extractPlanesOLD(Matrix4f matrix, float left[], float right[], float top[], float bottom[], float near[], float far[])
	{
		float mat[][] = new float[4][4];
		mat[0] = new float[4];
		mat[1] = new float[4];
		mat[2] = new float[4];
		mat[3] = new float[4];
		
		mat[0][0] = matrix.m00;
		mat[0][1] = matrix.m01;
		mat[0][2] = matrix.m02;
		mat[0][3] = matrix.m03;
		
		mat[1][0] = matrix.m10;
		mat[1][1] = matrix.m11;
		mat[1][2] = matrix.m12;
		mat[1][3] = matrix.m13;
		
		mat[2][0] = matrix.m20;
		mat[2][1] = matrix.m21;
		mat[2][2] = matrix.m22;
		mat[2][3] = matrix.m23;
		
		mat[3][0] = matrix.m30;
		mat[3][1] = matrix.m31;
		mat[3][2] = matrix.m32;
		mat[3][3] = matrix.m33;
		
		for (int i = 3; i >= 0;i--)
		{
			left[i] = mat[i][3] + mat[i][0];
		}
		for (int i = 3; i >= 0;i--)
		{
			right[i] = mat[i][3] - mat[i][0];
		}
		for (int i = 3; i >= 0;i--)
		{
			bottom[i] = mat[i][3] + mat[i][1];
		}
		for (int i = 3; i >= 0;i--)
		{
			top[i] = mat[i][3] - mat[i][1];
		}
		for (int i = 3; i >= 0;i--)
		{
			near[i] = mat[i][3] + mat[i][2];
		}
		for (int i = 3; i >= 0;i--)
		{
			far[i] = mat[i][3] - mat[i][2];
		}
	}
	
}
