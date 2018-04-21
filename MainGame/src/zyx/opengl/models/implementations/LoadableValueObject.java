package zyx.opengl.models.implementations;

import zyx.opengl.models.implementations.bones.skeleton.Skeleton;
import zyx.opengl.models.implementations.physics.PhysBox;
import zyx.opengl.textures.AbstractTexture;

public class LoadableValueObject
{

	float vertexData[];
	int elementData[];
	Skeleton skeleton;
	String texture;
	PhysBox physBox;
	
	AbstractTexture gameTexture;

	public LoadableValueObject(float[] vertexData, int[] elementData, Skeleton skeleton, PhysBox physBox, String texture)
	{
		this.vertexData = vertexData;
		this.elementData = elementData;
		this.skeleton = skeleton;
		this.texture = texture;
		this.physBox = physBox;
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
