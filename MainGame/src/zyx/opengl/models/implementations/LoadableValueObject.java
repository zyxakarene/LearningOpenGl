package zyx.opengl.models.implementations;

import zyx.opengl.models.implementations.bones.skeleton.Skeleton;

public class LoadableValueObject
{

	float vertexData[];
	int elementData[];
	Skeleton skeleton;
	String texture;

	public LoadableValueObject(float[] vertexData, int[] elementData, Skeleton skeleton, String texture)
	{
		this.vertexData = vertexData;
		this.elementData = elementData;
		this.skeleton = skeleton;
		this.texture = texture;
	}
}
