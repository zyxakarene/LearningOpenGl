package zyx.opengl.models.implementations.bones.skeleton;

import zyx.opengl.models.implementations.bones.animation.Animation;
import zyx.opengl.models.implementations.bones.animation.Animator;
import java.util.HashMap;
import java.util.LinkedList;
import org.lwjgl.util.vector.Matrix4f;
import zyx.opengl.models.implementations.bones.animation.AnimationController;
import zyx.utils.interfaces.IDisposeable;

public class Skeleton implements IDisposeable
{	
	private static final Matrix4f DUMMY_MATRIX = new Matrix4f();
	
	private Joint rootJoint;
	private Joint dummyJoint;
	private HashMap<Byte, Joint> jointIdMap;
	private HashMap<String, Joint> jointNameMap;
	private HashMap<String, Animation> animations;
	private Animator animator;
	private boolean disposed;
	
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
		
		animator = new Animator(jointIdMap, animations);
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
	
	public void update()
	{
		animator.update();
		rootJoint.calcAnimationTransform(DUMMY_MATRIX);
	}
	
	public Joint getBoneByName(String name)
	{
		return jointNameMap.get(name);
	}

	public Joint getBoneById(byte boneId)
	{
		return jointIdMap.get(boneId);
	}
	
	@Override
	public void dispose()
	{
		if (!disposed)
		{
			disposed = true;
			
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
}
