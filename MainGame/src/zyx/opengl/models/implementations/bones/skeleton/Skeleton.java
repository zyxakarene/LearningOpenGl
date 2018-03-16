package zyx.opengl.models.implementations.bones.skeleton;

import zyx.opengl.models.implementations.bones.animation.Animation;
import zyx.opengl.models.implementations.bones.animation.Animator;
import java.util.HashMap;
import java.util.LinkedList;
import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.utils.interfaces.IDisposeable;
import zyx.utils.interfaces.IUpdateable;

public class Skeleton implements IUpdateable, IDisposeable
{	
	private static final Matrix4f DUMMY_MATRIX = new Matrix4f();
	
	private Joint rootJoint;
	private Joint dummyJoint;
	private HashMap<Integer, Joint> jointIdMap;
	private HashMap<String, Joint> jointNameMap;
	private HashMap<String, Animation> animations;
	private Animator animator;
	
	private LinkedList<Animation> animationList;

	public Skeleton(Joint root, Joint meshJoint)
	{
		rootJoint = root;
		jointNameMap = new HashMap<>();
		jointIdMap = new HashMap<>();
		animations = new HashMap<>();
		animationList = new LinkedList<>();
		
		
		rootJoint.calcInverseBindTransform(DUMMY_MATRIX);
		rootJoint.addToMap(jointNameMap, jointIdMap);
		
		dummyJoint = meshJoint;
		if (jointNameMap.size() > 1)
		{
			jointNameMap.put(dummyJoint.name, dummyJoint);
		}
		
		animator = new Animator(jointNameMap, animations);
	}
	
	public void addAnimation(String name, Animation animation)
	{
		animations.put(name, animation);
		animationList.add(animation);
	}

	public void setCurrentAnimation(AnimationController controller)
	{
		animator.setCurrentAnimation(controller);
	}
	
	@Override
	public void update(long timestamp, int elapsedTime)
	{
		animator.update(timestamp, elapsedTime);
		rootJoint.calcAnimationTransform(DUMMY_MATRIX);
	}
	
	public Joint getBoneByName(String name)
	{
		return jointNameMap.get(name);
	}

	public Joint getBoneById(int boneId)
	{
		return jointIdMap.get(boneId);
	}
	
	@Override
	public void dispose()
	{
		rootJoint.dispose();
		animator.dispose();
		dummyJoint.dispose();
		
		while (animationList.isEmpty() == false)
		{			
			animationList.remove().dispose();
		}
		
		jointNameMap.clear();
		animations.clear();
		animationList.clear();
		
		rootJoint = null;
		jointNameMap = null;
		animations = null;
		animator = null;
		animationList = null;
		dummyJoint = null;
	}
}
