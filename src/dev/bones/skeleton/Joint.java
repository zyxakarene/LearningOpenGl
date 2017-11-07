package dev.bones.skeleton;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.util.vector.Matrix4f;

public class Joint
{

	private final List<Joint> children;
	private final Matrix4f animatedTransform;
	private final Matrix4f inverseBindTransform;
	
	private final Matrix4f localBindTransform;
	
	private final Matrix4f finalTransform;

	public Joint(Matrix4f bindTransform)
	{
		children = new ArrayList<>();
		animatedTransform = new Matrix4f();
		inverseBindTransform = new Matrix4f();
		localBindTransform = new Matrix4f(bindTransform);
		
		finalTransform = new Matrix4f();
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
		Matrix4f currentLocalTransform = new Matrix4f(animatedTransform);
		Matrix4f currentTransform = Matrix4f.mul(parentTransform, currentLocalTransform, null);
		for (Joint childJoint : children)
		{
			childJoint.calcAnimationTransform(currentTransform);
		}
		
		Matrix4f.mul(currentTransform, inverseBindTransform, currentTransform);
		finalTransform.load(currentTransform);
	}

	public Matrix4f getInverse()
	{
		return inverseBindTransform;
	}
	
	public Matrix4f getAnimation()
	{
		return finalTransform;
	}
	
}
