package zyx.opengl.models.implementations.bones.skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import zyx.game.controls.SharedPools;
import zyx.utils.interfaces.IDisposeable;

public class Joint implements IDisposeable
{

	private static Matrix4f[] shaderBones;

	public final int id;
	public final String name;

	private List<Joint> children;
	private boolean isAttachmentPoint;

	private Matrix4f animatedTransform;
	private Matrix4f inverseBindTransform;

	private Matrix4f localBindTransform;

	private Matrix4f outTransform;
	private Matrix4f lastFinalTransform;

	public Joint(int id, String name, Matrix4f localTransform)
	{
		this.id = id;
		this.name = name;

		outTransform = shaderBones[id];

		children = new ArrayList<>();
		animatedTransform = SharedPools.MATRIX_POOL.getInstance();
		inverseBindTransform = SharedPools.MATRIX_POOL.getInstance();
		localBindTransform = localTransform;

		isAttachmentPoint = name.equals("Skeleton_Hand_R");
		if (isAttachmentPoint)
		{
			lastFinalTransform = SharedPools.MATRIX_POOL.getInstance();
		}
	}

	public Matrix4f getLocalBindTransform()
	{
		return localBindTransform;
	}

	public void addChild(Joint child)
	{
		children.add(child);
	}

	public void setAnimationTransform(Matrix4f transform)
	{
		animatedTransform.load(transform);
	}

	public void calcInverseBindTransform(Matrix4f parentBindTransform)
	{
		Matrix4f bindTransform = Matrix4f.mul(parentBindTransform, localBindTransform, null);
		Matrix4f.invert(bindTransform, inverseBindTransform);
		for (Joint child : children)
		{
			child.calcInverseBindTransform(bindTransform);
		}
	}

	public void calcAnimationTransform(Matrix4f parentTransform)
	{
		Matrix4f.mul(parentTransform, animatedTransform, outTransform);
		for (Joint childJoint : children)
		{
			childJoint.calcAnimationTransform(outTransform);
		}

		if (isAttachmentPoint)
		{
			lastFinalTransform.load(outTransform);
		}

		Matrix4f.mul(outTransform, inverseBindTransform, outTransform);

	}

	public Matrix4f getInverse()
	{
		return inverseBindTransform;
	}

	public void addToMap(HashMap<String, Joint> map)
	{
		map.put(name, this);

		for (Joint child : children)
		{
			child.addToMap(map);
		}
	}

	public static void setBones(Matrix4f[] bones)
	{
		shaderBones = bones;
	}

	public void setTPose()
	{
		animatedTransform.load(localBindTransform);
	}

	public Matrix4f getFinalTransform()
	{
		return lastFinalTransform;
	}

	@Override
	public void dispose()
	{
		for (Joint child : children)
		{
			child.dispose();
		}

		children.clear();

		if (isAttachmentPoint)
		{
			SharedPools.MATRIX_POOL.releaseInstance(lastFinalTransform);
		}
		SharedPools.MATRIX_POOL.releaseInstance(animatedTransform);
		SharedPools.MATRIX_POOL.releaseInstance(inverseBindTransform);
		SharedPools.MATRIX_POOL.releaseInstance(localBindTransform);

		lastFinalTransform = null;
		outTransform = null;
		animatedTransform = null;
		inverseBindTransform = null;
		localBindTransform = null;
		children = null;
	}
}
