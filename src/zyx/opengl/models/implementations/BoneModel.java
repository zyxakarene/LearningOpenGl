package zyx.opengl.models.implementations;

import zyx.opengl.models.implementations.bones.skeleton.Skeleton;

public class BoneModel extends WorldModel
{

	private Skeleton skeleton;

	public BoneModel(float[] vertexData, int[] elementData, Skeleton skeleton)
	{
		super(vertexData, elementData);
		this.skeleton = skeleton;
		
		skeleton.setCurrentAnimation("walk");
	}

	public void setAnimation(String animation)
	{
		skeleton.setCurrentAnimation(animation);
	}

	@Override
	public void update(long timestamp, int elapsedTime)
	{
		skeleton.update(timestamp, elapsedTime);
	}
}
