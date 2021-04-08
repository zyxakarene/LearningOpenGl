package zyx.opengl.models.implementations.bones.skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import zyx.game.controls.SharedPools;
import zyx.utils.interfaces.IDisposeable;

public class Joint implements IDisposeable
{
	public final byte id;
	public final String name;

	private List<Joint> children;

	private Matrix4f animatedTransform;
	private Matrix4f inverseBindTransform;

	private Matrix4f localBindTransform;

	private Matrix4f outTransformInverseTranspose;
	private Matrix4f outTransform;
	
	private Matrix4f attachmentTransform;
	private Matrix4f physTransform;

	public Joint(byte id, String name, Matrix4f localTransform)
	{
		this.id = id;
		this.name = name;

		outTransform = BoneProvider.BONES[id];
		outTransformInverseTranspose = BoneProvider.INVERT_BONES[id];

		children = new ArrayList<>();
		animatedTransform = SharedPools.MATRIX_POOL.getInstance();
		inverseBindTransform = SharedPools.MATRIX_POOL.getInstance();
		localBindTransform = localTransform;
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

		if(attachmentTransform != null)
		{
			attachmentTransform.load(outTransform);
		}

		Matrix4f.mul(outTransform, inverseBindTransform, outTransform);

		if(physTransform != null)
		{
			physTransform.load(outTransform);
		}
		
		outTransformInverseTranspose.load(outTransform);
		outTransformInverseTranspose.invert().transpose();
		
		BoneProvider.BoneChanged(id);
	}

	public Matrix4f getInverse()
	{
		return inverseBindTransform;
	}

	public void addToMap(HashMap<String, Joint> nameMap, HashMap<Byte, Joint> idMap)
	{
		nameMap.put(name, this);
		idMap.put(id, this);

		for (Joint child : children)
		{
			child.addToMap(nameMap, idMap);
		}
	}

	public void setTPose()
	{
		animatedTransform.load(localBindTransform);
	}

	public Matrix4f getPhysTransform()
	{
		if(physTransform == null)
		{
			physTransform = SharedPools.MATRIX_POOL.getInstance();
		}
		
		return physTransform;
	}
	
	public Matrix4f getAttachmentTransform()
	{
		if(attachmentTransform == null)
		{
			attachmentTransform = SharedPools.MATRIX_POOL.getInstance();
		}
		
		return attachmentTransform;
	}

	@Override
	public void dispose()
	{
		for (Joint child : children)
		{
			child.dispose();
		}

		children.clear();

		if(physTransform != null)
		{
			SharedPools.MATRIX_POOL.releaseInstance(physTransform);
		}
		
		if(attachmentTransform != null)
		{
			SharedPools.MATRIX_POOL.releaseInstance(attachmentTransform);
		}
		
		SharedPools.MATRIX_POOL.releaseInstance(animatedTransform);
		SharedPools.MATRIX_POOL.releaseInstance(inverseBindTransform);
		SharedPools.MATRIX_POOL.releaseInstance(localBindTransform);

		physTransform = null;
		attachmentTransform = null;
		outTransform = null;
		animatedTransform = null;
		inverseBindTransform = null;
		localBindTransform = null;
		children = null;
	}
}
