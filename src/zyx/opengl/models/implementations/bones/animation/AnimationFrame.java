package zyx.opengl.models.implementations.bones.animation;

import zyx.opengl.models.implementations.bones.transform.JointTransform;
import java.util.HashMap;
import java.util.LinkedList;
import zyx.utils.interfaces.IDisposeable;

public class AnimationFrame implements IDisposeable
{
	public int frame;
	public HashMap<String, JointTransform> transforms;
	
	private LinkedList<JointTransform> transformList;

	public AnimationFrame()
	{
		this.transforms = new HashMap<>();
		transformList = new LinkedList<>();
	}
	
	public void addTransform(String boneName, JointTransform transform)
	{
		transforms.put(boneName, transform);
		transformList.add(transform);
	}

	@Override
	public void dispose()
	{
		transforms.clear();
		
		while (transformList.isEmpty() == false)
		{			
			transformList.remove().dispose();
		}
		
		transformList = null;
		transforms = null;
	}
}
