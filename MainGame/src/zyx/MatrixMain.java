/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zyx;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import zyx.opengl.shaders.implementations.WorldShader;
import zyx.utils.FloatMath;
import zyx.utils.GeometryUtils;
import zyx.utils.math.MatrixUtils;

/**
 *
 * @author Rene
 */
public class MatrixMain
{

	private static final Quaternion SHARED_QUAT = new Quaternion(0, 0, 0, 0);
	private static final Vector3f SHARED_ROTATION = new Vector3f(0, 0, 0);
	private static final Vector3f SHARED_POSITION = new Vector3f(0, 0, 0);
	private static final Vector3f SHARED_SCALE = new Vector3f(1, 1, 1);

	public static void transform(Matrix4f mat)
	{
		mat.translate(SHARED_POSITION);

		mat.rotate(SHARED_ROTATION.z, GeometryUtils.ROTATION_Z);
		mat.rotate(SHARED_ROTATION.y, GeometryUtils.ROTATION_Y);
		mat.rotate(SHARED_ROTATION.x, GeometryUtils.ROTATION_X);

		mat.scale(SHARED_SCALE);
	}
	
	/**
	 * Must be order ZYX and Radians!
	 */
	public static Quaternion transform(Vector3f rot, Quaternion out)
	{
		float c = (float) Math.cos(rot.x / 2f);
		float d = (float) Math.cos(rot.y / 2f);
		float e = (float) Math.cos(rot.z / 2f);
		float f = (float) Math.sin(rot.x / 2f);
		float g = (float) Math.sin(rot.y / 2f);
		float h = (float) Math.sin(rot.z / 2f);

		out.x = f * d * e - c * g * h;
		out.y = c * g * e + f * d * h;
		out.z = c * d * h - f * g * e;
		out.w = c * d * e + f * g * h;

		return out;
	}
	
	public static void main(String[] args)
	{
		Matrix4f mat = new Matrix4f();
		
		SHARED_POSITION.set(25, 30, 90);
		SHARED_ROTATION.set(FloatMath.toRadians(25), FloatMath.toRadians(20), FloatMath.toRadians(1));
		SHARED_SCALE.set(1, 2, 3);
		transform(SHARED_ROTATION, SHARED_QUAT);
		System.out.println("input pos: " + SHARED_POSITION);
		System.out.println("input rot:      " + SHARED_QUAT);
		System.out.println("input scale: " + SHARED_SCALE);
		
		System.out.println("--");
		transform(mat);
		
		MatrixUtils.DecomposeResult result = MatrixUtils.decompose(mat);
		System.out.println("Decomposed pos: " + result.pos);
		System.out.println("Decomposed rot: " + result.rot);
		System.out.println("Decomposed scale: " + result.scale);
	}
	
}
