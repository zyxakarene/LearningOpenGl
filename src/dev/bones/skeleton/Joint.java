package dev.bones.skeleton;

import dev.bones.transform.JointTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.util.vector.Matrix4f;
import zyx.utils.interfaces.IDisposeable;

public class Joint implements IDisposeable
{

	private static Matrix4f[] shaderBones;
	
	public final int id;
	public final String name;
	
	private List<Joint> children;
	
	private Matrix4f animatedTransform;
	private Matrix4f inverseBindTransform;
	
	private Matrix4f localBindTransform;
	
	private final Matrix4f outTransform;

	public Joint(int id, String name, JointTransform transform)
	{
		this.id = id;
		this.name = name;
		
		children = new ArrayList<>();
		animatedTransform = new Matrix4f();
		inverseBindTransform = new Matrix4f();
		localBindTransform = transform.getMatrix();
		
		outTransform = shaderBones[id];
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
		Matrix4f.mul(parentTransform, currentLocalTransform, outTransform);
		for (Joint childJoint : children)
		{
			childJoint.calcAnimationTransform(outTransform);
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
	
	@Override
	public void dispose()
	{
		for (Joint child : children)
		{
			child.dispose();
		}
		
		children.clear();
		
		children = null;
	}
	
}
