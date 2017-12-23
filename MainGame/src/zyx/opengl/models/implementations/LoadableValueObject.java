package zyx.opengl.models.implementations;

import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.textures.AbstractTexture;

public class LoadableValueObject
{

	float vertexData[];
	int elementData[];
	Skeleton skeleton;
	String texture;
	
	AbstractTexture gameTexture;

	public LoadableValueObject(float[] vertexData, int[] elementData, Skeleton skeleton, String texture)
	{
		this.vertexData = vertexData;
		this.elementData = elementData;
		this.skeleton = skeleton;
		this.texture = texture;
	}

	public String getTexture()
	{
		return texture;
	}

	public void setGameTexture(AbstractTexture gameTexture)
	{
		this.gameTexture = gameTexture;
	}
	
	
}
