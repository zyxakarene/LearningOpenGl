package zyx.opengl.models.loading.skeletons.fallback;

import org.lwjgl.util.vector.Matrix4f;
import zyx.game.controls.SharedPools;
import zyx.opengl.models.implementations.bones.skeleton.Joint;
import zyx.opengl.models.implementations.bones.skeleton.Skeleton;

public class FakeSkeleton
{

	public static Skeleton makeFakeSkeleton()
	{
		Joint rootJoint = getJointFrom();
		Joint meshJoint = getMeshJoint();

		return new Skeleton(rootJoint, meshJoint);
	}
	
	private static Joint getJointFrom()
	{
		Matrix4f restPose = SharedPools.MATRIX_POOL.getInstance();

		String boneName = "root";
		byte id = 1;
		Joint joint = new Joint(id, boneName, restPose);

		return joint;
	}
	
	private static Joint getMeshJoint()
	{
		String name = "dummy";
		Matrix4f matrix = SharedPools.MATRIX_POOL.getInstance();
		byte id = 0;

		return new Joint(id, name, matrix);
	}
}
