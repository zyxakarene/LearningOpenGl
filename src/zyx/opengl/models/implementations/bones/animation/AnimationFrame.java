package zyx.opengl.models.implementations.bones.animation;

import zyx.opengl.models.implementations.bones.transform.JointTransform;
import java.util.HashMap;

public class AnimationFrame
{
	public int frame;
	public HashMap<String, JointTransform> transforms;

	public AnimationFrame()
	{
		this.transforms = new HashMap<>();
	}
	
	public void addTransform(String boneName, JointTransform transform)
	{
		transforms.put(boneName, transform);
	}
}