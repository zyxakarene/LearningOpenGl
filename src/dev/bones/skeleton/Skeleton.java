package dev.bones.skeleton;

import dev.bones.animation.Animation;
import dev.bones.animation.Animator;
import java.util.HashMap;
import org.lwjgl.util.vector.Matrix4f;
import zyx.utils.interfaces.IUpdateable;

public class Skeleton implements IUpdateable
{	
	private static final Matrix4f DUMMY_MATRIX = new Matrix4f();
	
	private final Joint rootJoint;
	private final HashMap<String, Joint> jointMap;
	private final HashMap<String, Animation> animations;
	
	private final Animator animator;

	public Skeleton(Joint root)
	{
		rootJoint = root;
		jointMap = new HashMap<>();
		animations = new HashMap<>();
		
		rootJoint.calcInverseBindTransform(DUMMY_MATRIX);
		rootJoint.addToMap(jointMap);
		
		animator = new Animator(jointMap, animations);
	}
	
	public void addAnimation(String name, Animation animation)
	{
		animations.put(name, animation);
	}

	public void setCurrentAnimation(String name)
	{
		animator.setCurrentAnimation(name);
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		animator.update(timestamp, elapsedTime);
		rootJoint.calcAnimationTransform(DUMMY_MATRIX);
	}
}
